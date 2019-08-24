package com.univaq.eaglelibrary.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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

	// -- Getter and Setter --//

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
