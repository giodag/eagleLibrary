package com.univaq.eaglelibrary.hanlder;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mysql.cj.util.StringUtils;
import com.univaq.eaglelibrary.converter.ConvertModule;
import com.univaq.eaglelibrary.dto.ModuleDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.exceptions.CannotUpdateModuleException;
import com.univaq.eaglelibrary.exceptions.MandatoryFieldException;
import com.univaq.eaglelibrary.model.Module;
import com.univaq.eaglelibrary.model.User;
import com.univaq.eaglelibrary.repository.ModuleRepository;
import com.univaq.eaglelibrary.repository.UserRepository;

@Component
public class ModuleHandler {
	
	private static final String UTENTE = "Utente";
	private static final String ANNO_DI_STUDIO = "Anno di studio";
	private static final String COMMENT = "Comment";
	private static final String ALL = "All";
	private static final String MISSED_PARAMETER = "Missed parameter : ";
	private static final String ERROR = "It is impossible to submit the request at the moment. \r\n" + 
			"Please try again in days: {}.";
	
	@Autowired
	private ConvertModule convertModule;
	
	@Autowired
	private ModuleRepository moduleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ModuleHandler.class);

	public ResultDTO createUpdateModule(ModuleDTO moduleDTO) throws MandatoryFieldException, CannotUpdateModuleException {
		
		ModuleDTO moduleRead = readModule(moduleDTO);
		Date oneYearAgo = getOneYearAgo();
		
		if(moduleRead != null 
				&& moduleRead.getCreationDate().compareTo(oneYearAgo)>0 
				&& moduleRead.getStatus().equalsIgnoreCase("Rejected")) {
			
			long diffInMillies = Math.abs(moduleRead.getCreationDate().getTime() - oneYearAgo.getTime());
		    Long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		    LOGGER.error("Ancora non sono trascorsi i 365 giorni per poter inviare nuovamente il modulo. Mancano {}", String.valueOf(diff));
			throw new CannotUpdateModuleException(ERROR+diff.toString());
		}
		
		Module module = null;
		
		if(moduleRead != null) {
			moduleRead.setComment(!StringUtils.isNullOrEmpty(moduleDTO.getComment()) ? moduleDTO.getComment() : moduleRead.getComment());
			moduleRead.setCreationDate(moduleDTO.getCreationDate() != null ? moduleDTO.getCreationDate() : moduleRead.getCreationDate());
			moduleRead.setId(moduleDTO.getId() != null ? moduleDTO.getId() : moduleRead.getId());
			moduleRead.setStatus(!StringUtils.isNullOrEmpty(moduleDTO.getStatus()) ? moduleDTO.getStatus() : moduleRead.getStatus());
			moduleRead.setYearOfTheStudy(moduleDTO.getYearOfTheStudy() != null ? moduleDTO.getYearOfTheStudy() : moduleRead.getYearOfTheStudy());
			module = convertModule.convert(moduleRead);
			
		} else {
			//--Nello scenario di creazione andiamo a verificare le mandatorietà
			checkMandatory(moduleDTO);
			module = convertModule.convert(moduleDTO);
		}
		Module result = moduleRepository.save(module);
		ResultDTO resultDTO = null;
		if (result != null) {
			resultDTO = new ResultDTO();
			resultDTO.setSuccessfullyOperation(Boolean.TRUE);
		}
		return resultDTO;
	}

	private Date getOneYearAgo() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -1); 
		return cal.getTime();
	}

	public ModuleDTO readModule(ModuleDTO moduleDTO) {
		Module module = null;
		if (moduleDTO.getId() != null) {
			module = moduleRepository.findOne(moduleDTO.getId());
		} else {
			if(moduleDTO.getUser() != null) {
				User user = userRepository.findByUsername(moduleDTO.getUser().getUsername());
				module = moduleRepository.findModuleByUser(user.getId());
			}
		}
		return convertModule.convert(module);
	}

	private void checkMandatory(ModuleDTO moduleDTO) throws MandatoryFieldException {
		if(moduleDTO == null) {
			throw new MandatoryFieldException(MISSED_PARAMETER, ALL);
		}else if(StringUtils.isNullOrEmpty(moduleDTO.getComment())) {
			throw new MandatoryFieldException(MISSED_PARAMETER, COMMENT);
		}else if(moduleDTO.getYearOfTheStudy() == null) {
			throw new MandatoryFieldException(MISSED_PARAMETER, ANNO_DI_STUDIO);
		}else if(moduleDTO.getUser() == null) {
			throw new MandatoryFieldException(MISSED_PARAMETER, UTENTE);
		}
	}
	
	public ResultDTO validateModule(ModuleDTO moduleDTO) throws CannotUpdateModuleException {
		ResultDTO resultDTO = new ResultDTO(Boolean.FALSE);
		if(moduleDTO != null) {
			Module module = moduleRepository.findModuleByUsername(moduleDTO.getUsername());
			Date oneYearAgo = getOneYearAgo();
			if(module != null 
					&& module.getCreationDate().compareTo(oneYearAgo)>0 
					&& module.getStatus().equalsIgnoreCase("Rejected")) {
				
				long diffInMillies = Math.abs(module.getCreationDate().getTime() - oneYearAgo.getTime());
			    Long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			    LOGGER.error("Ancora non sono trascorsi i 365 giorni per poter inviare nuovamente il modulo. Mancano {}", String.valueOf(diff));
				throw new CannotUpdateModuleException(ERROR+diff.toString());
			}else if(module != null) {
				module.setStatus(moduleDTO.getStatus());
				moduleDTO = convertModule.convert(moduleRepository.save(module));
				resultDTO.setSuccessfullyOperation(Boolean.TRUE);
			}
		}
		return resultDTO;
	}
	
	public List<ModuleDTO> getAllModules(){
		List<Module> modules = moduleRepository.findAll();
		List<ModuleDTO> modulesRead = convertModule.convert(modules);
		return modulesRead;
	}
}
