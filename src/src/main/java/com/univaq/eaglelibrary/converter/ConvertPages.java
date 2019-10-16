package com.univaq.eaglelibrary.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.univaq.eaglelibrary.dto.PageDTO;
import com.univaq.eaglelibrary.model.Page;

@Component
public class ConvertPages {

	@Autowired
	private ConvertTranscription convertTranscription;

	public List<PageDTO> convert(List<Page> pageModel) {
		List<PageDTO> pagesDTO = null;
		if (pageModel != null && !pageModel.isEmpty()) {
			pagesDTO = new ArrayList<PageDTO>();
			for (Page page : pageModel) {
				pagesDTO.add(convert(page));
			}
		}
		return pagesDTO;
	}

	public List<Page> convertToModel(List<PageDTO> pages) {
		List<Page> pagesModel = null;
		if (pages != null && !pages.isEmpty()) {
			pagesModel = new ArrayList<Page>();
			for (PageDTO pageDTO : pages) {
				pagesModel.add(convertToModel(pageDTO));
			}
		}
		return pagesModel;
	}

	public PageDTO convert(Page page) {
		PageDTO pageDTO = null;
		if (page != null) {
			pageDTO = new PageDTO();
			pageDTO.setChapter(page.getChapter());
			pageDTO.setId(page.getId());
			pageDTO.setPageNumber(page.getPageNumber());
			pageDTO.setTranscriptionDTO(convertTranscription.convert(page.getTranscription()));
		}
		return pageDTO;
	}

	public Page convertToModel(PageDTO pageDTO) {
		Page page = null;
		if (pageDTO != null) {
			page = new Page();
			page.setChapter(pageDTO.getChapter());
			page.setId(pageDTO.getId());
			page.setPageNumber(pageDTO.getPageNumber());
			//page.setTranscription(convertTranscription.convert(pageDTO.getTranscriptionDTO()));
			page.setImage(pageDTO.getImage());
		}
		return page;
	}
}
