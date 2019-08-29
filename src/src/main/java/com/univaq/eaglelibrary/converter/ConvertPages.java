package com.univaq.eaglelibrary.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.univaq.eaglelibrary.dto.PageDTO;
import com.univaq.eaglelibrary.model.Page;

@Component
public class ConvertPages {

	public List<PageDTO> convert(List<Page> pageModel) {
		List<PageDTO> pagesDTO = null;
		if (pageModel != null && !pageModel.isEmpty()) {
			pagesDTO = new ArrayList<PageDTO>();
			for (Page page : pageModel) {
				PageDTO pageDTO = new PageDTO();
				pageDTO.setChapter(page.getChapter());
				pageDTO.setId(page.getId());
				pageDTO.setPageNumber(page.getPageNumber());
				pagesDTO.add(pageDTO);
			}
		}
		return pagesDTO;
	}

}
