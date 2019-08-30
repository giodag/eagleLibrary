package com.univaq.eaglelibrary.hanlder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.univaq.eaglelibrary.converter.ConvertLiteraryWork;
import com.univaq.eaglelibrary.converter.ConvertTranscription;
import com.univaq.eaglelibrary.dto.LiteraryWorkDTO;
import com.univaq.eaglelibrary.dto.LiteraryWorkListDTO;
import com.univaq.eaglelibrary.dto.LiteraryWorkListFilterDTO;
import com.univaq.eaglelibrary.dto.PageDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.model.LiteraryWork;
import com.univaq.eaglelibrary.model.Page;
import com.univaq.eaglelibrary.model.Transcription;
import com.univaq.eaglelibrary.persistence.exceptions.MandatoryFieldException;
import com.univaq.eaglelibrary.repository.LiteraryWorkRepository;
import com.univaq.eaglelibrary.repository.TranscriptionRepository;

@Component
public class LiteraryWorkHanlder {

	@Autowired
	private LiteraryWorkRepository literaryWorkRepository;

	@Autowired
	private TranscriptionRepository transcriptionRepository;

	@Autowired
	private ConvertLiteraryWork convertLiteraryWork;

	@Autowired
	private ConvertTranscription convertTranscription;

	private final Logger logger = LoggerFactory.getLogger(LiteraryWorkHanlder.class);

	public LiteraryWorkDTO getLiteraryWork(LiteraryWorkDTO literaryWorkDTO) {
		LiteraryWork literaryWork = literaryWorkRepository.findLiteraryWorkByFilter(literaryWorkDTO.getId(),
				literaryWorkDTO.getCategory(), literaryWorkDTO.getTitle(), literaryWorkDTO.getYear(),
				literaryWorkDTO.getAuthor());
		return convertLiteraryWork.convert(literaryWork);
	}

	public LiteraryWorkDTO getLiteraryWorkTranscribed(LiteraryWorkDTO literaryWorkDTO) {
		LiteraryWorkDTO literaryWorkDTORead = getLiteraryWork(literaryWorkDTO);
		if (literaryWorkDTORead != null && literaryWorkDTORead.getPageList() != null
				&& !literaryWorkDTORead.getPageList().isEmpty()) {
			for (PageDTO pageDTO : literaryWorkDTORead.getPageList()) {
				Transcription transcription = transcriptionRepository.findTranscriptionByPage(pageDTO.getId());
				pageDTO.setTranscriptionDTO(convertTranscription.convert(transcription));
			}
		}
		return literaryWorkDTORead;
	}

	public LiteraryWorkListDTO getLiteraryWork(LiteraryWorkListFilterDTO literaryWorkListFilterDTO) {
		LiteraryWorkListDTO literaryWorkListDTO = null;
		List<LiteraryWork> literaryWorksFiltered = null;
		if (literaryWorkListFilterDTO != null) {
			if (StringUtils.isNotEmpty(literaryWorkListFilterDTO.getPartOfText())) {
				List<Transcription> transcriptionList = transcriptionRepository
						.findByTranscriptionLike(literaryWorkListFilterDTO.getPartOfText());
				if (transcriptionList != null && !transcriptionList.isEmpty()) {
					literaryWorksFiltered = new ArrayList<LiteraryWork>();
					for (Transcription transcription : transcriptionList) {
						if (transcription.getPage() != null && transcription.getPage().getLiteraryWorkPage() != null
								&& !literaryWorksFiltered.contains(transcription.getPage().getLiteraryWorkPage())) {
							literaryWorksFiltered.add(transcription.getPage().getLiteraryWorkPage());
						}
					}
				}
			} else {
				literaryWorksFiltered = literaryWorkRepository.findListLiteraryWorkByFilter(
						literaryWorkListFilterDTO.getIdList(), literaryWorkListFilterDTO.getCategory(),
						literaryWorkListFilterDTO.getTitle(), literaryWorkListFilterDTO.getYear(),
						literaryWorkListFilterDTO.getAuthor());
			}
			if (literaryWorksFiltered != null && !literaryWorksFiltered.isEmpty()) {
				literaryWorkListDTO = new LiteraryWorkListDTO();
				literaryWorkListDTO.setLiteraryWorkList(convertLiteraryWork.convert(literaryWorksFiltered));
			}
		}
		return literaryWorkListDTO;
	}

	public ResultDTO createUpdateLiteraryWork(LiteraryWorkDTO literaryWorkDTO) throws MandatoryFieldException {
		ResultDTO resultDTO = null;
		checkMandatory(literaryWorkDTO);
		LiteraryWork literaryWork = convertLiteraryWork.convert(literaryWorkDTO);

		if (literaryWork != null && literaryWork.getPageList() != null) {
			for (Page page : literaryWork.getPageList()) {
				page.setLiteraryWorkPage(literaryWork);
			}
		}
		LiteraryWork result = literaryWorkRepository.save(literaryWork);
		if (result != null) {
			resultDTO = new ResultDTO();
			resultDTO.setSuccessfullyOperation(Boolean.TRUE);
		}
		return resultDTO;
	}

	private void checkMandatory(LiteraryWorkDTO literaryWorkDTO) throws MandatoryFieldException {
		if (literaryWorkDTO == null || literaryWorkDTO.getAuthor() == null || literaryWorkDTO.getCategory() == null
				|| literaryWorkDTO.getTitle() == null) {
			throw new MandatoryFieldException();
		}
	}

}
