package com.univaq.eaglelibrary.dto;

import java.util.List;

public class UserListDTO {
	
	public List<UserDTO> listUserDTO;

	//-- Getter and Setter --//
	
	public UserListDTO(List<UserDTO> listUser) {
		this.listUserDTO = listUser;
	}
	
	public List<UserDTO> getListUserDTO() {
		return listUserDTO;
	}

	public void setListUserDTO(List<UserDTO> listUserDTO) {
		this.listUserDTO = listUserDTO;
	}

}
