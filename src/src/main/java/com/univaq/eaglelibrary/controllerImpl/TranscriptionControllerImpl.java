package com.univaq.eaglelibrary.controllerImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.univaq.eaglelibrary.controller.TranscriptionController;
import com.univaq.eaglelibrary.dto.AssignTranscriptionRequestDTO;
import com.univaq.eaglelibrary.dto.AssignTranscriptionResponseDTO;
import com.univaq.eaglelibrary.dto.LockTranscriptionRequestDTO;
import com.univaq.eaglelibrary.dto.LockTranscriptionResponseDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.dto.TranscriptionDTO;
import com.univaq.eaglelibrary.hanlder.TranscriptionHanlder;
import com.univaq.eaglelibrary.persistence.exceptions.MandatoryFieldException;

public class TranscriptionControllerImpl implements TranscriptionController {
	
	private final Logger logger = LoggerFactory.getLogger(TranscriptionControllerImpl.class);
	private TranscriptionHanlder transcriptionHanlder;

	public TranscriptionControllerImpl() {
		this.transcriptionHanlder = new TranscriptionHanlder();
	}
	
	public TranscriptionDTO submitTranscription(TranscriptionDTO transcriptionDTO) {
		logger.debug("start submitTranscription");
		TranscriptionDTO transcriptionDTOUpdated = null;
		try {
			transcriptionDTOUpdated = this.transcriptionHanlder.submitTranscription(transcriptionDTO);
		} catch (MandatoryFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("finish submitTranscription");
		return transcriptionDTOUpdated;
	}

	public ResultDTO validateTranscription(TranscriptionDTO transcriptionDTO) {
		logger.debug("start validateTranscription");
		ResultDTO resultDTO = null;
		try {
			resultDTO = this.transcriptionHanlder.validateTranscription(transcriptionDTO);
		} catch (MandatoryFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("finish validateTranscription");
		return resultDTO;
	}

	public AssignTranscriptionResponseDTO assignTrascription(
			AssignTranscriptionRequestDTO assignTranscriptionRequestDTO) {
		logger.debug("start getTranscription");
		AssignTranscriptionResponseDTO responseDTO = this.transcriptionHanlder.assignTrascription(assignTranscriptionRequestDTO);
		logger.debug("finish getTranscription");
		return responseDTO;
	}

	public TranscriptionDTO getTranscription(TranscriptionDTO transcriptionDTO) {
		logger.debug("start getTranscription");
		TranscriptionDTO transcriptionDTORead = this.transcriptionHanlder.getTranscription(transcriptionDTO);
		logger.debug("finish getTranscription");
		return transcriptionDTORead;
	}

	public LockTranscriptionResponseDTO lockTranscription(LockTranscriptionRequestDTO lockTranscriptionRequestDTO) {
		logger.debug("start lockTranscription");
		logger.debug("finish lockTranscription");
		return null;
	}

	public ResultDTO publishTranscription(TranscriptionDTO transcriptionDTO) {
		logger.debug("start publishTranscription");
		logger.debug("finish publishTranscription");
		return null;
	}

}
