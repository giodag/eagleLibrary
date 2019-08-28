package com.univaq.eaglelibrary.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.univaq.eaglelibrary.dto.LiteraryWorkDTO;
import com.univaq.eaglelibrary.model.LiteraryWork;

@Component
public class ConvertLiteraryWork {
	
	@Autowired
	private ConvertPages convertPages;

	public LiteraryWorkDTO convert(LiteraryWork literaryWork) {
		LiteraryWorkDTO literaryWorkDTO = null;
		if(literaryWork != null) {
			literaryWorkDTO = new LiteraryWorkDTO();
			literaryWorkDTO.setAuthor(literaryWork.getAuthor());
			literaryWorkDTO.setCategory(literaryWork.getCategory());
			literaryWorkDTO.setId(literaryWork.getId());
			literaryWorkDTO.setTitle(literaryWork.getTitle());
			literaryWorkDTO.setYear(literaryWork.getYear());
			literaryWorkDTO.setPageList(convertPages.convert(literaryWork.getPageList()));
		}
		return literaryWorkDTO;
	}
	
}