package com.univaq.eaglelibrary.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.univaq.eaglelibrary.dto.TranscriptionDTO;
import com.univaq.eaglelibrary.model.Transcription;

@Component
public class ConvertTranscription {
	
	@Autowired
	private ConvertUser convertUser;
	
	@Autowired
	private ConvertPages convertPages;

	public Transcription convert(TranscriptionDTO transcriptionDTO) {
		Transcription transcription = null;
		if(transcriptionDTO != null) {
			transcription = new Transcription();
			transcription.setId(transcriptionDTO.getId());
			transcription.setLockByUser(transcriptionDTO.getLockedByuser());
			transcription.setStatus(transcriptionDTO.getStatus());
			transcription.setTranscription(transcriptionDTO.getTranscription());
			transcription.setPage(convertPages.convertToModel(transcriptionDTO.getPage()));
//			transcription.setUsersWorkTranscription(convertUser.convertToModel(transcriptionDTO.getUserList()));
		}
		return transcription;
		
	}
	
	public Transcription convertNoPages(TranscriptionDTO transcriptionDTO) {
		Transcription transcription = null;
		if(transcriptionDTO != null) {
			transcription = new Transcription();
			transcription.setId(transcriptionDTO.getId());
			transcription.setLockByUser(transcriptionDTO.getLockedByuser());
			transcription.setStatus(transcriptionDTO.getStatus());
			transcription.setTranscription(transcriptionDTO.getTranscription());
		}
		return transcription;
		
	}
	
	public List<Transcription> convertToModel(List<TranscriptionDTO> transcriptionDTOs) {
		List<Transcription> transcriptionModel = null;
		if (transcriptionDTOs != null && !transcriptionDTOs.isEmpty()) {
			transcriptionModel = new ArrayList<Transcription>();
			for (TranscriptionDTO transcriptionDTO : transcriptionDTOs) {
				transcriptionModel.add(convert(transcriptionDTO));
			}
		}
		return transcriptionModel;
	}
	
	public List<TranscriptionDTO> convert(List<Transcription> transcriptions) {
		List<TranscriptionDTO> transcriptionsDTO = null;
		if (transcriptions != null && !transcriptions.isEmpty()) {
			transcriptionsDTO = new ArrayList<TranscriptionDTO>();
			for (Transcription transcription : transcriptions) {
				transcriptionsDTO.add(convert(transcription));
			}
		}
		return transcriptionsDTO;
	}
	
	public TranscriptionDTO convert(Transcription transcription) {
		TranscriptionDTO transcriptionDTO = null;
		if(transcription != null) {
			transcriptionDTO = new TranscriptionDTO();
			transcriptionDTO.setId(transcription.getId());
			transcriptionDTO.setLockedByuser(transcription.getLockByUser());
			transcriptionDTO.setStatus(transcription.getStatus());
			transcriptionDTO.setTranscription(transcription.getTranscription());
			transcriptionDTO.setPage(convertPages.convertNoTranscription(transcription.getPage()));
			transcriptionDTO.setUserList(convertUser.convertNoTranscription(transcription.getUsersWorkTranscription()));
		}
		return transcriptionDTO;
	}
}
