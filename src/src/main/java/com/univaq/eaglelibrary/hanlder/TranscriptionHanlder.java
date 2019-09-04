package com.univaq.eaglelibrary.hanlder;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.univaq.eaglelibrary.converter.ConvertPages;
import com.univaq.eaglelibrary.converter.ConvertTranscription;
import com.univaq.eaglelibrary.converter.ConvertUser;
import com.univaq.eaglelibrary.dto.TranscriptionDTO;
import com.univaq.eaglelibrary.model.Transcription;
import com.univaq.eaglelibrary.repository.TranscriptionRepository;

public class TranscriptionHanlder {

	@Autowired
	private TranscriptionRepository transcriptionRepository;

	@Autowired
	private ConvertTranscription convertTranscription;
	
	@Autowired
	private ConvertPages convertPages;
	
	@Autowired
	private ConvertUser convertUser;

	private final Logger logger = LoggerFactory.getLogger(TranscriptionHanlder.class);
	
	public TranscriptionDTO createUpdateTranscription(TranscriptionDTO transcriptionDTO) {
		if(transcriptionDTO != null) {
			Transcription transcription = getTranscription(transcriptionDTO);
			if(transcription == null) {
				transcription = new Transcription();
			}
			transcription.setId(transcriptionDTO.getId() != null ? transcriptionDTO.getId() : transcription.getId());
			transcription.setPage(transcriptionDTO.getPage() != null ? convertPages.convertToModel(transcriptionDTO.getPage()) : transcription.getPage());
			transcription.setStatus(StringUtils.isNotEmpty(transcriptionDTO.getStatus()) ? transcriptionDTO.getStatus() : transcription.getStatus());
			transcription.setUsersWorkTranscription(transcriptionDTO.getUserList() != null && !transcriptionDTO.getUserList().isEmpty() ? convertUser.convertToModel(transcriptionDTO.getUserList()) : transcription.getUsersWorkTranscription());
			transcription.setTranscription(StringUtils.isNotEmpty(transcriptionDTO.getTranscription()) ? transcriptionDTO.getTranscription() : transcription.getTranscription());
			transcription.setLockByUser(transcriptionDTO.getLockedByuser() != null ? transcriptionDTO.getLockedByuser() : transcription.getLockByUser());
			transcription = transcriptionRepository.save(transcription);
			transcriptionDTO = convertTranscription.convert(transcription);
		}
		return transcriptionDTO;
	}

	public TranscriptionDTO getTranscriptionDTO(TranscriptionDTO transcriptionDTO) {
		TranscriptionDTO transcriptionDTORead = null;
		if(transcriptionDTO != null) {
			Transcription transcription = getTranscription(transcriptionDTO);
			transcriptionDTORead = convertTranscription.convert(transcription);
		}
		return transcriptionDTORead;
	}
	
	private Transcription getTranscription(TranscriptionDTO transcriptionDTO) {
		Transcription transcription = null;
		if (transcriptionDTO != null) {
			if (transcriptionDTO.getId() != null) {
				transcription = transcriptionRepository.findOne(transcriptionDTO.getId());
			} else {
				transcription = transcriptionRepository.findByFilter(transcriptionDTO.getTranscription(), transcriptionDTO.getStatus(),
						transcriptionDTO.getPage() != null ? transcriptionDTO.getPage().getId() : null,
								transcriptionDTO.getLockedByuser() != null ? transcriptionDTO.getLockedByuser() : null);
			}
		}
		return transcription;
	}
}
