package com.univaq.eaglelibrary.controller;

import com.univaq.eaglelibrary.dto.AssignTranscriptionRequestDTO;
import com.univaq.eaglelibrary.dto.AssignTranscriptionResponseDTO;
import com.univaq.eaglelibrary.dto.LockTranscriptionRequestDTO;
import com.univaq.eaglelibrary.dto.LockTranscriptionResponseDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.dto.TranscriptionDTO;
import com.univaq.eaglelibrary.persistence.exceptions.MandatoryFieldException;

public interface TranscriptionController {
	
	public TranscriptionDTO submitTranscription(TranscriptionDTO transcriptionDTO) throws MandatoryFieldException;
	public ResultDTO validateTranscription(TranscriptionDTO transcriptionDTO);
	public AssignTranscriptionResponseDTO assignTrascription(AssignTranscriptionRequestDTO assignTranscriptionRequestDTO);
	public TranscriptionDTO getTranscription(TranscriptionDTO transcriptionDTO);
	public LockTranscriptionResponseDTO lockTranscription(LockTranscriptionRequestDTO lockTranscriptionRequestDTO);
	public ResultDTO publishTranscription(TranscriptionDTO transcriptionDTO) throws MandatoryFieldException;
	
}
