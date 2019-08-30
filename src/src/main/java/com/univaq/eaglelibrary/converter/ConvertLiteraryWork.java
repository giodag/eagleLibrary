package com.univaq.eaglelibrary.converter;

import java.util.ArrayList;
import java.util.List;

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
	
	public LiteraryWork convert(LiteraryWorkDTO literaryWorkDTO) {
		LiteraryWork literaryWork = null;
		if(literaryWorkDTO != null) {
			literaryWork = new LiteraryWork();
			literaryWork.setAuthor(literaryWorkDTO.getAuthor());
			literaryWork.setCategory(literaryWorkDTO.getCategory());
			literaryWork.setId(literaryWorkDTO.getId());
			literaryWork.setTitle(literaryWorkDTO.getTitle());
			literaryWork.setYear(literaryWorkDTO.getYear());
			literaryWork.setPageList(convertPages.convertToModel(literaryWorkDTO.getPageList()));
		}
		return literaryWork;
	}
	
	public List<LiteraryWorkDTO> convert(List<LiteraryWork> lieratyWorksModel){
		List<LiteraryWorkDTO> literaryWorkDTOs = null;
		if(lieratyWorksModel != null && !lieratyWorksModel.isEmpty()) {
			literaryWorkDTOs = new ArrayList<LiteraryWorkDTO>();
			for (LiteraryWork literaryWork : lieratyWorksModel) {
				LiteraryWorkDTO literaryWorkDTO = convert(literaryWork);
				literaryWorkDTOs.add(literaryWorkDTO);
			}
		}
		return literaryWorkDTOs;
	}
	
}
