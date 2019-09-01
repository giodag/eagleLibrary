package com.univaq.eaglelibrary.hanlder;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.univaq.eaglelibrary.converter.ConvertTranscription;
import com.univaq.eaglelibrary.dto.AssignTranscriptionRequestDTO;
import com.univaq.eaglelibrary.dto.AssignTranscriptionResponseDTO;
import com.univaq.eaglelibrary.dto.LockTranscriptionRequestDTO;
import com.univaq.eaglelibrary.dto.LockTranscriptionResponseDTO;
import com.univaq.eaglelibrary.dto.PageDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.dto.TranscriptionDTO;
import com.univaq.eaglelibrary.model.Page;
import com.univaq.eaglelibrary.model.Transcription;
import com.univaq.eaglelibrary.model.User;
import com.univaq.eaglelibrary.persistence.exceptions.MandatoryFieldException;
import com.univaq.eaglelibrary.repository.PageRepository;
import com.univaq.eaglelibrary.repository.TranscriptionRepository;
import com.univaq.eaglelibrary.repository.UserRepository;

public class TranscriptionHanlder {

	@Autowired
	private TranscriptionRepository transcriptionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PageRepository pageRepository;

	@Autowired
	private ConvertTranscription convertTranscription;

	private final Logger logger = LoggerFactory.getLogger(TranscriptionHanlder.class);

	public TranscriptionDTO submitTranscription(TranscriptionDTO transcriptionDTO) throws MandatoryFieldException {
		checkMandatory(transcriptionDTO);
		transcriptionDTO.setStatus("OPEN");
		Transcription transcription = convertTranscription.convert(transcriptionDTO);
		transcriptionRepository.save(transcription);
		return null;
	}

	public ResultDTO validateTranscription(TranscriptionDTO transcriptionDTO) throws MandatoryFieldException {
		checkMandatory(transcriptionDTO);
		return null;
	}

	public AssignTranscriptionResponseDTO assignTrascription(
			AssignTranscriptionRequestDTO assignTranscriptionRequestDTO) {
		
		if(assignTranscriptionRequestDTO != null 
				&& assignTranscriptionRequestDTO.getPageList() != null
				&& !assignTranscriptionRequestDTO.getPageList().isEmpty()
				&& StringUtils.isNotEmpty(assignTranscriptionRequestDTO.getUsername())) {
			
			for (PageDTO pageDTO : assignTranscriptionRequestDTO.getPageList()) {
				
				Page page = null;
				if(pageDTO.getId() != null) {
					page = pageRepository.findOne(pageDTO.getId());
				}else {
					page = pageRepository.finaPageByFilter(pageDTO.getPageNumber(), pageDTO.getChapter(), pageDTO.getId());
				}
				User user = userRepository.findByUsername(assignTranscriptionRequestDTO.getUsername());
				if(user != null && page != null) {
					Transcription transcription = transcriptionRepository.findTranscriptionByPage(page.getId());
					if(transcription != null) {
						//-- La transcrizione esiste già qundi è stata creata precedentemente e adesso vogliamo 
						//-- solo settare il nuovo user
						//-- N.B. : qui anche lo status della trascrizione sto lasciando invariato
						transcription.getUsersWorkTranscription().add(user);
					}else {
						//--Scenario in cui la trascrizione la stiamo creando in questo momento
						transcription = new Transcription();
						List<User> usersWorkOnTranscription = new ArrayList<User>();
						usersWorkOnTranscription.add(user);
 						transcription.setPage(page);
						transcription.setUsersWorkTranscription(usersWorkOnTranscription);
						transcription.setStatus("OPEN");
					}
					transcriptionRepository.save(transcription);
										
				}
			}
		}
		AssignTranscriptionResponseDTO assignTranscriptionResponseDTO = new AssignTranscriptionResponseDTO();
		assignTranscriptionResponseDTO.setAssigned(Boolean.TRUE);
		return assignTranscriptionResponseDTO;
	}

	public TranscriptionDTO getTranscription(TranscriptionDTO transcriptionDTO) {
		TranscriptionDTO transcriptionDTORead = null;
		Transcription transcription = null;
		if (transcriptionDTO != null) {
			if (transcriptionDTO.getId() != null) {
				transcription = transcriptionRepository.findOne(transcriptionDTO.getId());
			} else {
				transcription = transcriptionRepository.findByFilter(transcriptionDTO.getTranscription(), transcriptionDTO.getStatus(),
						transcriptionDTO.getPage() != null ? transcriptionDTO.getPage().getId() : null);
			}
			transcriptionDTORead = convertTranscription.convert(transcription);
		}
		return transcriptionDTORead;
	}

	public LockTranscriptionResponseDTO lockTranscription(LockTranscriptionRequestDTO lockTranscriptionRequestDTO) {
		return null;
	}

	public ResultDTO publishTranscription(TranscriptionDTO transcriptionDTO) throws MandatoryFieldException {
		checkMandatory(transcriptionDTO);
		return null;
	}

	private void checkMandatory(TranscriptionDTO transcriptionDTO) throws MandatoryFieldException {
		if (transcriptionDTO == null || StringUtils.isEmpty(transcriptionDTO.getTranscription())
				|| transcriptionDTO.getUserList() == null || transcriptionDTO.getUserList().isEmpty()) {
			throw new MandatoryFieldException();
		}

	}

}
