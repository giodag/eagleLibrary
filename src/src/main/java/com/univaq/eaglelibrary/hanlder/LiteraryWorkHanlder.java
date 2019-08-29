package com.univaq.eaglelibrary.hanlder;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.univaq.eaglelibrary.converter.ConvertLiteraryWork;
import com.univaq.eaglelibrary.dto.LiteraryWorkDTO;
import com.univaq.eaglelibrary.dto.LiteraryWorkListDTO;
import com.univaq.eaglelibrary.dto.LiteraryWorkListFilterDTO;
import com.univaq.eaglelibrary.dto.PageDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.model.LiteraryWork;
import com.univaq.eaglelibrary.model.Page;
import com.univaq.eaglelibrary.repository.LiteraryWorkRepository;

@Component
public class LiteraryWorkHanlder {

	@Autowired
	private LiteraryWorkRepository literaryWorkRepository;

	@Autowired
	private ConvertLiteraryWork convertLiteraryWork;

	private final Logger logger = LoggerFactory.getLogger(LiteraryWorkHanlder.class);

	public LiteraryWorkDTO getLiteraryWork(LiteraryWorkDTO literaryWorkDTO) {
		LiteraryWork literaryWork = literaryWorkRepository.findLiteraryWorkByFilter(literaryWorkDTO.getId(),
				literaryWorkDTO.getCategory(), literaryWorkDTO.getTitle(), literaryWorkDTO.getYear(),
				literaryWorkDTO.getAuthor());
		return convertLiteraryWork.convert(literaryWork);
	}

	public LiteraryWorkDTO getLiteraryWorkTranscribed(LiteraryWorkDTO literaryWorkDTO) {
		return null;
	}

	public LiteraryWorkListDTO getLiteraryWork(LiteraryWorkListFilterDTO literaryWorkListFilterDTO) {
		return null;
	}

	public ResultDTO createUpdateLiteraryWork(LiteraryWorkDTO literaryWorkDTO) {
		//TODO INSERIRE CONVERTER E REFATTORIZZARE IL CODICE
		//LE ISTRUZIONI DI CUI SOTTO SERVIVANO SOLO PER UN TEST
//		LiteraryWork literaryWork = new LiteraryWork();
//		List<PageDTO> dtos = literaryWorkDTO.getPageList();
//		List<Page> pages = new ArrayList<Page>();
//		Page page = new Page();
//		page.setChapter(dtos.get(0).getChapter());
//		page.setPageNumber(dtos.get(0).getPageNumber());
//		pages.add(page);
//		page.setLiteraryWorkPage(literaryWork);
//		literaryWork.setAuthor(literaryWorkDTO.getAuthor());
//		literaryWork.setCategory(literaryWorkDTO.getCategory());
//		literaryWork.setPageList(pages);
//		literaryWork.setTitle(literaryWorkDTO.getTitle());
//		literaryWork.setYear(literaryWorkDTO.getYear());
//		LiteraryWork result = literaryWorkRepository.save(literaryWork);
		return null;
	}

}
