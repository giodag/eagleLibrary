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

	public Transcription convert(TranscriptionDTO transcriptionDTO) {
		return null;
		
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
			transcriptionDTO.setUserList(convertUser.convertNoTranscription(transcription.getUsersWorkTranscription()));
		}
		return transcriptionDTO;
	}
}
