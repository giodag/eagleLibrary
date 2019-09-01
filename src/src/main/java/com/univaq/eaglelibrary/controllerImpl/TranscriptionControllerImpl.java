package com.univaq.eaglelibrary.controllerImpl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.univaq.eaglelibrary.controller.TranscriptionController;
import com.univaq.eaglelibrary.dto.AssignTranscriptionRequestDTO;
import com.univaq.eaglelibrary.dto.AssignTranscriptionResponseDTO;
import com.univaq.eaglelibrary.dto.LockTranscriptionRequestDTO;
import com.univaq.eaglelibrary.dto.LockTranscriptionResponseDTO;
import com.univaq.eaglelibrary.dto.PageDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.dto.TranscriptionDTO;
import com.univaq.eaglelibrary.dto.UserDTO;
import com.univaq.eaglelibrary.hanlder.PageHandler;
import com.univaq.eaglelibrary.hanlder.TranscriptionHanlder;
import com.univaq.eaglelibrary.hanlder.UserHanlder;
import com.univaq.eaglelibrary.model.Transcription;
import com.univaq.eaglelibrary.persistence.exceptions.MandatoryFieldException;

public class TranscriptionControllerImpl implements TranscriptionController {
	
	private final Logger logger = LoggerFactory.getLogger(TranscriptionControllerImpl.class);
	
	@Autowired
	private TranscriptionHanlder transcriptionHanlder;
	
	@Autowired
	private UserHanlder userHandler; 
	
	@Autowired
	private PageHandler pageHandler;
	
	public TranscriptionDTO submitTranscription(TranscriptionDTO transcriptionDTO) throws MandatoryFieldException {
		logger.debug("start submitTranscription");
		
		checkMandatory(transcriptionDTO);
		transcriptionDTO.setStatus("IN APPROVE");
		TranscriptionDTO transcriptionDTOUpdated = transcriptionHanlder.createUpdateTranscription(transcriptionDTO);
		
		logger.debug("finish submitTranscription");
		return transcriptionDTOUpdated;
	}

	public ResultDTO validateTranscription(TranscriptionDTO transcriptionDTO) {
		logger.debug("start validateTranscription");
		ResultDTO resultDTO = null;
		
		logger.debug("finish validateTranscription");
		return resultDTO;
	}

	public AssignTranscriptionResponseDTO assignTrascription(
			AssignTranscriptionRequestDTO assignTranscriptionRequestDTO) {
		logger.debug("start getTranscription");
		
		if(assignTranscriptionRequestDTO != null 
				&& assignTranscriptionRequestDTO.getPageList() != null
				&& !assignTranscriptionRequestDTO.getPageList().isEmpty()
				&& StringUtils.isNotEmpty(assignTranscriptionRequestDTO.getUsername())) {
			
			for (PageDTO pageDTO : assignTranscriptionRequestDTO.getPageList()) {
				
				UserDTO userFilter = new UserDTO();
				userFilter.setUsername(assignTranscriptionRequestDTO.getUsername());
				PageDTO pageRead = pageHandler.getPageDTO(pageDTO);
				UserDTO userRead = userHandler.getUserDTO(userFilter);
				
				if(userRead != null && pageRead != null) {
					TranscriptionDTO transcriptionFilter = new TranscriptionDTO();
					transcriptionFilter.setPage(new PageDTO());
					transcriptionFilter.getPage().setId(pageRead.getId());
					transcriptionFilter = transcriptionHanlder.getTranscriptionDTO(transcriptionFilter);
					
					if(transcriptionFilter != null) {
						//-- La transcrizione esiste già qundi è stata creata precedentemente e adesso vogliamo 
						//-- solo settare il nuovo user
						//-- N.B. : qui anche lo status della trascrizione sto lasciando invariato
						transcriptionFilter.getUserList().add(userRead);
					}else {
						//--Scenario in cui la trascrizione la stiamo creando in questo momento
						transcriptionFilter = new TranscriptionDTO();
						List<UserDTO> usersWorkOnTranscription = Arrays.asList(userRead);
						transcriptionFilter.setPage(pageRead);
						transcriptionFilter.setUserList(usersWorkOnTranscription);
						transcriptionFilter.setStatus("OPEN");
					}
					transcriptionHanlder.createUpdateTranscription(transcriptionFilter);
										
				}
			}
		}
		AssignTranscriptionResponseDTO assignTranscriptionResponseDTO = new AssignTranscriptionResponseDTO();
		assignTranscriptionResponseDTO.setAssigned(Boolean.TRUE);

		logger.debug("finish getTranscription");
		return assignTranscriptionResponseDTO;
	}

	public TranscriptionDTO getTranscription(TranscriptionDTO transcriptionDTO) {
		return transcriptionHanlder.getTranscriptionDTO(transcriptionDTO);
	}

	public LockTranscriptionResponseDTO lockTranscription(LockTranscriptionRequestDTO lockTranscriptionRequestDTO) {
		logger.debug("start lockTranscription");
		LockTranscriptionResponseDTO lockTranscriptionResponseDTO = null;
		if(lockTranscriptionRequestDTO != null 
				&& lockTranscriptionRequestDTO.getTranscription() != null
				&& StringUtils.isNotEmpty(lockTranscriptionRequestDTO.getUsername())) {
			lockTranscriptionResponseDTO = new LockTranscriptionResponseDTO();
			UserDTO userRead  = new UserDTO();
			userRead.setUsername(lockTranscriptionRequestDTO.getUsername());
			userRead  = userHandler.getUserDTO(userRead);
			TranscriptionDTO transcriptionToLock = getTranscription(lockTranscriptionRequestDTO.getTranscription());
			
			if(userRead != null && transcriptionToLock != null 
					&& ("OPEN".equals(transcriptionToLock.getStatus())
							|| "REJECTED".equals(transcriptionToLock.getStatus()))) {
				transcriptionToLock.setStatus("LOCK");
				transcriptionHanlder.createUpdateTranscription(transcriptionToLock);
				lockTranscriptionResponseDTO.setAssigned(Boolean.TRUE);
				lockTranscriptionResponseDTO.setMessage("LOCK SUCCESSFULLY");
			}else {
				lockTranscriptionResponseDTO.setAssigned(Boolean.FALSE);
				lockTranscriptionResponseDTO.setMessage("LOCK UNSUCCESSFULLY");
			}
		}
		logger.debug("finish lockTranscription");
		return lockTranscriptionResponseDTO;
	}

	public ResultDTO publishTranscription(TranscriptionDTO transcriptionDTO) {
		logger.debug("start publishTranscription");
		logger.debug("finish publishTranscription");
		return null;
	}
	
	private void checkMandatory(TranscriptionDTO transcriptionDTO) throws MandatoryFieldException {
		if (transcriptionDTO == null || StringUtils.isEmpty(transcriptionDTO.getTranscription())
				|| transcriptionDTO.getUserList() == null || transcriptionDTO.getUserList().isEmpty()) {
			throw new MandatoryFieldException();
		}
	}

}
