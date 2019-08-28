package com.univaq.eaglelibrary.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.univaq.eaglelibrary.dto.PageDTO;
import com.univaq.eaglelibrary.model.Page;

@Component
public class ConvertPages {

	public List<PageDTO> convert(List<Page> pageModel) {
		List<PageDTO> pagesDTO = null;
		if (pageModel != null && !pageModel.isEmpty()) {
			pagesDTO = pageModel.stream().map(x -> {
				PageDTO pageDTO = new PageDTO();
				pageDTO.setChapter(x.getChapter());
				pageDTO.setId(x.getId());
				pageDTO.setPageNumber(x.getPageNumber());
				return pageDTO;
			}).collect(Collectors.toList());

		}
		return pagesDTO;
	}

}
