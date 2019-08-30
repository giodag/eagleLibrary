package com.univaq.eaglelibrary.hanlder;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.univaq.eaglelibrary.dto.AssignTranscriptionRequestDTO;
import com.univaq.eaglelibrary.dto.AssignTranscriptionResponseDTO;
import com.univaq.eaglelibrary.dto.LockTranscriptionRequestDTO;
import com.univaq.eaglelibrary.dto.LockTranscriptionResponseDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.dto.TranscriptionDTO;
import com.univaq.eaglelibrary.persistence.exceptions.MandatoryFieldException;

public class TranscriptionHanlder {
	
	private final Logger logger = LoggerFactory.getLogger(TranscriptionHanlder.class);

	public TranscriptionDTO submitTranscription(TranscriptionDTO transcriptionDTO) throws MandatoryFieldException {
		checkMandatory(transcriptionDTO);
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
		// TODO Auto-generated method stub
		return null;
	}

	public LockTranscriptionResponseDTO lockTranscription(LockTranscriptionRequestDTO lockTranscriptionRequestDTO) {
		return null;
	}

	public ResultDTO publishTranscription(TranscriptionDTO transcriptionDTO) throws MandatoryFieldException {
		checkMandatory(transcriptionDTO);
		return null;
	}

	private void checkMandatory(TranscriptionDTO transcriptionDTO) throws MandatoryFieldException {
		if(transcriptionDTO == null 
				|| StringUtils.isEmpty(transcriptionDTO.getTranscription())
				|| transcriptionDTO.getUserList() == null 
				|| transcriptionDTO.getUserList().isEmpty()) {
			throw new MandatoryFieldException();
		}
		
	}
	
}
