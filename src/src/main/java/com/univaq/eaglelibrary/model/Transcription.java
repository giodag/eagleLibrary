package com.univaq.eaglelibrary.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "trascription")
public class Transcription {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "transcription")
	private String transcription;

	@Column(name = "status")
	private String status;

	@ManyToMany(mappedBy = "listTranscription")
	private List<User> usersWorkTranscription;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "page_id")
	private Page page;
	
	@Column(name = "lockByUser")
	private Long lockByUser;

	// -- Getter and Setter --//
	
	public Long getLockByUser() {
		return lockByUser;
	}

	public void setLockByUser(Long lockByUser) {
		this.lockByUser = lockByUser;
	}

	public List<User> getUsersWorkTranscription() {
		return usersWorkTranscription;
	}

	public void setUsersWorkTranscription(List<User> usersWorkTranscription) {
		this.usersWorkTranscription = usersWorkTranscription;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTranscription() {
		return transcription;
	}

	public void setTranscription(String transcription) {
		this.transcription = transcription;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
