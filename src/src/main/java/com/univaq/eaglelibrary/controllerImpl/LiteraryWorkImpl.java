package com.univaq.eaglelibrary.controllerImpl;

import com.univaq.eaglelibrary.controller.LiteraryWork;
import com.univaq.eaglelibrary.dto.LiteraryWorkDTO;
import com.univaq.eaglelibrary.dto.LiteraryWorkListDTO;
import com.univaq.eaglelibrary.dto.LiteraryWorkListFilterDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.hanlder.LiteraryWorkHanlder;

public class LiteraryWorkImpl implements LiteraryWork {

	private LiteraryWorkHanlder literaryWorkHanlder; 
	
	public LiteraryWorkImpl() {
		literaryWorkHanlder = new LiteraryWorkHanlder();
	}
	
	public LiteraryWorkDTO getLiteraryWork(LiteraryWorkDTO literaryWorkDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	public LiteraryWorkDTO getLiteraryWorkTranscribed(LiteraryWorkDTO literaryWorkDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	public LiteraryWorkListDTO getLiteraryWork(LiteraryWorkListFilterDTO literaryWorkListFilterDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultDTO createUpdateLiteraryWork(LiteraryWorkDTO literaryWorkDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}
