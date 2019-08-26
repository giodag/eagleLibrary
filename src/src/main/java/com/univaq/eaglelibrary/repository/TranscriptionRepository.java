package com.univaq.eaglelibrary.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.univaq.eaglelibrary.model.Transcription;

public class TranscriptionRepository {

	private EntityManager getEntityManager(){
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EL-UP");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		return entityManager;
		
	}
	
	public void save(Transcription transcription) {
		EntityManager entityManager = getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.persist(transcription);
		tx.commit();
		entityManager.close();
	}

	public Transcription get(Long id) {
		EntityManager entityManager = getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		Transcription transcription = this.getEntityManager().find(Transcription.class, id);
		tx.commit();
		entityManager.close();
		return transcription;
	}

	public void delete(Transcription transcription) {
		EntityManager entityManager = getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		this.getEntityManager().remove(transcription);
		tx.commit();
		entityManager.close();
	}

	public Transcription update(Transcription transcription) {
		EntityManager entityManager = getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		Transcription userUpdated = this.getEntityManager().merge(transcription);
		tx.commit();
		return userUpdated;
	}
}
