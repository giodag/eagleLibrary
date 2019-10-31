package com.univaq.eaglelibrary.controllerImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.univaq.eaglelibrary.controller.TranscriptionController;
import com.univaq.eaglelibrary.dto.AssignTranscriptionRequestDTO;
import com.univaq.eaglelibrary.dto.AssignTranscriptionResponseDTO;
import com.univaq.eaglelibrary.dto.LockTranscriptionRequestDTO;
import com.univaq.eaglelibrary.dto.LockTranscriptionResponseDTO;
import com.univaq.eaglelibrary.dto.PageDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.dto.TranscriptionDTO;
import com.univaq.eaglelibrary.dto.UnassignTranscriptionRequestDTO;
import com.univaq.eaglelibrary.dto.UnassignTranscriptionResponseDTO;
import com.univaq.eaglelibrary.dto.UserDTO;
import com.univaq.eaglelibrary.exceptions.MandatoryFieldException;
import com.univaq.eaglelibrary.hanlder.PageHandler;
import com.univaq.eaglelibrary.hanlder.TranscriptionHanlder;
import com.univaq.eaglelibrary.hanlder.UserHanlder;
/**
 * L'implementazione dell'interfaccia controller, orchestra le chiamate verso il core computazionale 
 * del sistema minimizzando così gli impatti tra la parte view e la parte logica nel caso di change requests.
 */
@Service
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

		// --Come prima cosa vado a leggere la trascrizione senza filtrare per
		// lockByUser, dopodichè
		// --verifico che lo status della trascrizione sia lock e che l'utente che
		// l'abbia lockato
		// --sia lo stesso che ha sovrascritto la trascrizione. Se si salviamo le
		// modifiche, lo status
		// --diventerà IN APPROVE e rimuoveremo il lock dell'utente
		TranscriptionDTO transcriptionDTORead = transcriptionHanlder.readTranscription(transcriptionDTO);
		if (transcriptionDTORead != null && "LOCK".equals(transcriptionDTORead.getStatus())) {
			UserDTO userFiter = new UserDTO();
			userFiter.setUsername(transcriptionDTO.getUsername());
			userFiter = userHandler.readUser(userFiter);
			if (userFiter != null && userFiter.getId().equals(transcriptionDTORead.getLockedByuser())) {
				transcriptionDTO.setStatus("IN APPROVE");
				transcriptionDTO.setLockedByuser(null);
				transcriptionDTO = transcriptionHanlder.createUpdateTranscription(transcriptionDTO);
			}
		}
		logger.debug("finish submitTranscription");
		return transcriptionDTO;
	}

	public TranscriptionDTO saveTranscription(TranscriptionDTO transcriptionDTO) throws MandatoryFieldException {
		logger.debug("start submitTranscription");

		checkMandatory(transcriptionDTO);
		TranscriptionDTO transcriptionDTORead = transcriptionHanlder.readTranscription(transcriptionDTO);
		if (transcriptionDTORead != null && "LOCK".equals(transcriptionDTORead.getStatus())) {
			UserDTO userFiter = new UserDTO();
			userFiter.setUsername(transcriptionDTO.getUsername());
			userFiter = userHandler.readUser(userFiter);
			if (userFiter != null && userFiter.getId().equals(transcriptionDTORead.getLockedByuser())) {

				// --Setto questa info a null per permettere al prossimo utente di lockare, sia
				// esso lo stesso di prima
				// --oppure un altro
				transcriptionDTO.setLockedByuser(null);
				transcriptionDTO = transcriptionHanlder.createUpdateTranscription(transcriptionDTO);
				LockTranscriptionRequestDTO lockTranscriptionRequestDTO = new LockTranscriptionRequestDTO();
				lockTranscriptionRequestDTO.setTranscription(transcriptionDTO);
				unlockTranscription(lockTranscriptionRequestDTO);
			}
		}
		logger.debug("finish submitTranscription");
		return transcriptionDTO;
	}

	public ResultDTO validateTranscription(TranscriptionDTO transcriptionDTO) {
		logger.debug("start validateTranscription");
		ResultDTO resultDTO = null;
		TranscriptionDTO transcriptionRead = transcriptionHanlder.readTranscription(transcriptionDTO);
//		if ("CLOSED".equals(transcriptionRead.getStatus()) || "REJECT".equals(transcriptionRead.getStatus())) {
		if(transcriptionRead != null) {
			// --Ho commentato di proposito la riga sotto perchè è un errore mettere
			// completed direttamente,
			// --lo status in questo caso deve essere passato dalla GUI perchè potrebbe
			// anche essere REJECT
			// --quindi a DB andiamo a scrivere quello che ci arriva.
			// transcriptionDTO.setStatus("COMPLETED");

			TranscriptionDTO transcriptionDTOUpdated = transcriptionHanlder.createUpdateTranscription(transcriptionDTO);
			// --Per la validate della trascrizione assumiamo che, l'utente che revisionerà
			// la trascrizione all'atto della validazione
			// --decide se accettare le modifiche o evidenziare le cose che non vanno nella
			// trascrizione e rigettare la modifica
			if (transcriptionDTOUpdated != null) {
				resultDTO = new ResultDTO();
				resultDTO.setSuccessfullyOperation(Boolean.TRUE);
			}
		}
		logger.debug("finish validateTranscription");
		return resultDTO;
	}
	
	public UnassignTranscriptionResponseDTO unassignTranscription(
			UnassignTranscriptionRequestDTO unassignTranscriptionRequestDTO) throws MandatoryFieldException {
		UnassignTranscriptionResponseDTO unassignTranscriptionResponseDTO = null;
		if (unassignTranscriptionRequestDTO != null && unassignTranscriptionRequestDTO.getPageList() != null
				&& !unassignTranscriptionRequestDTO.getPageList().isEmpty()
				&& StringUtils.isNotEmpty(unassignTranscriptionRequestDTO.getUsername())) {
			
			UserDTO userFilter = new UserDTO();
			userFilter.setUsername(unassignTranscriptionRequestDTO.getUsername());
			UserDTO userRead = userHandler.readUser(userFilter);
			
			if(userRead != null && !CollectionUtils.isEmpty(unassignTranscriptionRequestDTO.getPageList())) {
				for (PageDTO pageDTO : unassignTranscriptionRequestDTO.getPageList()) {
					PageDTO pageRead = pageHandler.readPage(pageDTO);
					TranscriptionDTO transcriptionFilter = new TranscriptionDTO();
					transcriptionFilter.setPage(new PageDTO());
					transcriptionFilter.getPage().setId(pageRead.getId());
					transcriptionFilter = transcriptionHanlder.readTranscription(transcriptionFilter);
					
					if(transcriptionFilter != null) {
						for (Iterator<UserDTO> iter = transcriptionFilter.getUserList().listIterator(); iter.hasNext(); ) {
							UserDTO userDTO = iter.next();
						    if (userDTO.getUsername().equalsIgnoreCase(userRead.getUsername())) {
						        iter.remove();
						    }
						}
						for (Iterator<TranscriptionDTO> iter = userRead.getTranscriptionList().listIterator(); iter.hasNext(); ) {
							TranscriptionDTO transcrToRemove = iter.next();
						    if (pageRead.getId().equals(transcrToRemove.getPage().getId())) {
						        iter.remove();
						    }
						}
//						transcriptionHanlder.createUpdateTranscription(transcriptionFilter);
						userHandler.updateUser(userRead);
					}
				}
				unassignTranscriptionResponseDTO = new UnassignTranscriptionResponseDTO();
				unassignTranscriptionResponseDTO.setAssigned(Boolean.TRUE);
			}
		}
				
		return unassignTranscriptionResponseDTO;
	}

	public AssignTranscriptionResponseDTO assignTrascription(
			AssignTranscriptionRequestDTO assignTranscriptionRequestDTO) throws MandatoryFieldException {
		logger.debug("start getTranscription");
		AssignTranscriptionResponseDTO assignTranscriptionResponseDTO = new AssignTranscriptionResponseDTO();
		if (assignTranscriptionRequestDTO != null && assignTranscriptionRequestDTO.getPageList() != null
				&& !assignTranscriptionRequestDTO.getPageList().isEmpty()
				&& StringUtils.isNotEmpty(assignTranscriptionRequestDTO.getUsername())) {

			for (PageDTO pageDTO : assignTranscriptionRequestDTO.getPageList()) {

				UserDTO userFilter = new UserDTO();
				userFilter.setUsername(assignTranscriptionRequestDTO.getUsername());
				PageDTO pageRead = pageHandler.readPage(pageDTO);
				UserDTO userRead = userHandler.readUser(userFilter);
				
				if(userRead != null && pageRead != null) {
					TranscriptionDTO transcriptionFilter = new TranscriptionDTO();
//					transcriptionFilter.setPage(new PageDTO());
					transcriptionFilter.setPage(pageRead);
					transcriptionFilter.getPage().setId(pageRead.getId());
					transcriptionFilter = transcriptionHanlder.readTranscription(transcriptionFilter);

					if (transcriptionFilter != null) {
						// -- La transcrizione esiste già qundi è stata creata precedentemente e adesso
						// vogliamo
						// -- solo settare il nuovo user
						// -- N.B. : qui anche lo status della trascrizione sto lasciando invariato
						// -- da controllare il caso in cui la lista degli assegnati sia nulla
						if(transcriptionFilter.getUserList() != null) {
							transcriptionFilter.getUserList().add(userRead);
						} else {
							List<UserDTO> usersWorkOnTranscription = Arrays.asList(userRead);
							transcriptionFilter.setUserList(usersWorkOnTranscription);
						}
					} else {
						// --Scenario in cui la trascrizione la stiamo creando in questo momento
						transcriptionFilter = new TranscriptionDTO();
						List<UserDTO> usersWorkOnTranscription = Arrays.asList(userRead);
						transcriptionFilter.setUserList(usersWorkOnTranscription);
						transcriptionFilter.setStatus("OPEN");
						transcriptionFilter.setPage(pageRead);
					}
					if(userRead.getTranscriptionList() != null) {
						userRead.getTranscriptionList().add(transcriptionFilter);
					} else {
						List<TranscriptionDTO> transcriptionList = new ArrayList<TranscriptionDTO>();
						transcriptionList.add(transcriptionFilter);
						userRead.setTranscriptionList(transcriptionList);
					}
					
//					transcriptionHanlder.createUpdateTranscription(transcriptionFilter);
					userHandler.updateUser(userRead);
//					pageRead.setTranscriptionDTO(transcriptionFilter);
//					pageHandler.createUpdatePage(pageRead);
					assignTranscriptionResponseDTO.setAssigned(Boolean.TRUE);
				}
			}
		}
		logger.debug("finish getTranscription");
		return assignTranscriptionResponseDTO;
	}

	public TranscriptionDTO getTranscription(TranscriptionDTO transcriptionDTO) {
		return transcriptionHanlder.readTranscription(transcriptionDTO);
	}
	
	public List<TranscriptionDTO> getTrascriptionList(TranscriptionDTO transcriptionDTO){
		return transcriptionHanlder.readTranscriptionList(transcriptionDTO);
	}

	public LockTranscriptionResponseDTO lockTranscription(LockTranscriptionRequestDTO lockTranscriptionRequestDTO) {
		logger.debug("start lockTranscription");
		LockTranscriptionResponseDTO lockTranscriptionResponseDTO = null;
		if (lockTranscriptionRequestDTO != null && lockTranscriptionRequestDTO.getTranscription() != null
				&& StringUtils.isNotEmpty(lockTranscriptionRequestDTO.getUsername())) {

			lockTranscriptionResponseDTO = new LockTranscriptionResponseDTO();
			UserDTO userRead = new UserDTO();
			userRead.setUsername(lockTranscriptionRequestDTO.getUsername());
			userRead  = userHandler.readUser(userRead);
			TranscriptionDTO transcriptionToLock = getTranscription(lockTranscriptionRequestDTO.getTranscription());

			if (userRead != null && transcriptionToLock != null && ("OPEN".equals(transcriptionToLock.getStatus())
					|| "REJECTED".equals(transcriptionToLock.getStatus()))) {
				transcriptionToLock.setStatus("LOCK");
				transcriptionToLock.setLockedByuser(userRead.getId());
				transcriptionHanlder.createUpdateTranscription(transcriptionToLock);
				lockTranscriptionResponseDTO.setAssigned(Boolean.TRUE);
				lockTranscriptionResponseDTO.setMessage("LOCK SUCCESSFULLY");
			} else {
				lockTranscriptionResponseDTO.setAssigned(Boolean.FALSE);
				lockTranscriptionResponseDTO.setMessage("LOCK UNSUCCESSFULLY");
			}
		}
		logger.debug("finish lockTranscription");
		return lockTranscriptionResponseDTO;
	}

	public LockTranscriptionResponseDTO unlockTranscription(LockTranscriptionRequestDTO lockTranscriptionRequestDTO) {
		logger.debug("start lockTranscription");
		LockTranscriptionResponseDTO lockTranscriptionResponseDTO = null;
		if (lockTranscriptionRequestDTO != null && lockTranscriptionRequestDTO.getTranscription() != null
				&& StringUtils.isNotEmpty(lockTranscriptionRequestDTO.getUsername())) {

			lockTranscriptionResponseDTO = new LockTranscriptionResponseDTO();
			UserDTO userRead = new UserDTO();
			userRead.setUsername(lockTranscriptionRequestDTO.getUsername());
			userRead = userHandler.readUser(userRead);
			TranscriptionDTO transcriptionToUnlock = getTranscription(lockTranscriptionRequestDTO.getTranscription());

			if (userRead != null && transcriptionToUnlock != null && "LOCK".equals(transcriptionToUnlock.getStatus())
					&& userRead.getId().equals(transcriptionToUnlock.getLockedByuser())) {
				transcriptionToUnlock.setStatus("OPEN");
				transcriptionToUnlock.setLockedByuser(null);
				transcriptionHanlder.createUpdateTranscription(transcriptionToUnlock);
				lockTranscriptionResponseDTO.setAssigned(Boolean.TRUE);
				lockTranscriptionResponseDTO.setMessage("UNLOCK SUCCESSFULLY");
			} else {
				lockTranscriptionResponseDTO.setAssigned(Boolean.FALSE);
				lockTranscriptionResponseDTO.setMessage("UNLOCK UNSUCCESSFULLY");
			}
		}
		logger.debug("finish lockTranscription");
		return lockTranscriptionResponseDTO;
	}

	public ResultDTO publishTranscription(TranscriptionDTO transcriptionDTO) throws MandatoryFieldException {
		logger.debug("start publishTranscription");
		ResultDTO resultDTO = null;
		checkMandatory(transcriptionDTO);
		TranscriptionDTO transcriptionread = transcriptionHanlder.readTranscription(transcriptionDTO);
		if (transcriptionread != null) {
			resultDTO = new ResultDTO();
			if ("COMPLETED".equals(transcriptionread.getStatus())) {
				transcriptionDTO.setStatus("PUBLISHED");
				transcriptionHanlder.createUpdateTranscription(transcriptionDTO);
				resultDTO.setSuccessfullyOperation(Boolean.TRUE);
			} else {
				resultDTO.setSuccessfullyOperation(Boolean.FALSE);
				resultDTO.setMessage("Cannot pusblish transcription until is not completed");
			}
		}
		logger.debug("finish publishTranscription");
		return resultDTO;
	}

	private void checkMandatory(TranscriptionDTO transcriptionDTO) throws MandatoryFieldException {
		if (transcriptionDTO == null || StringUtils.isEmpty(transcriptionDTO.getTranscription())
				|| transcriptionDTO.getUserList() == null || transcriptionDTO.getUserList().isEmpty()) {
			throw new MandatoryFieldException();
		}
	}
}
