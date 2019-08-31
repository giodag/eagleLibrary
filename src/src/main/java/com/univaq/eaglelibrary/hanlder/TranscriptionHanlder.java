package com.univaq.eaglelibrary.hanlder;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.univaq.eaglelibrary.converter.ConvertTranscription;
import com.univaq.eaglelibrary.dto.AssignTranscriptionRequestDTO;
import com.univaq.eaglelibrary.dto.AssignTranscriptionResponseDTO;
import com.univaq.eaglelibrary.dto.LockTranscriptionRequestDTO;
import com.univaq.eaglelibrary.dto.LockTranscriptionResponseDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.dto.TranscriptionDTO;
import com.univaq.eaglelibrary.model.Transcription;
import com.univaq.eaglelibrary.persistence.exceptions.MandatoryFieldException;
import com.univaq.eaglelibrary.repository.TranscriptionRepository;

public class TranscriptionHanlder {

	@Autowired
	private TranscriptionRepository transcriptionRepository;

	@Autowired
	private ConvertTranscription convertTranscription;

	private final Logger logger = LoggerFactory.getLogger(TranscriptionHanlder.class);

	public TranscriptionDTO submitTranscription(TranscriptionDTO transcriptionDTO) throws MandatoryFieldException {
		checkMandatory(transcriptionDTO);
		transcriptionDTO.setStatus("OPEN");
		Transcription transcription = convertTranscription.convert(transcriptionDTO);
		return null;
	}

	public ResultDTO validateTranscription(TranscriptionDTO transcriptionDTO) throws MandatoryFieldException {
		checkMandatory(transcriptionDTO);
		return null;
	}

	public AssignTranscriptionResponseDTO assignTrascription(
			AssignTranscriptionRequestDTO assignTranscriptionRequestDTO) {
		// TODO Auto-generated method stub
		return null;
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
