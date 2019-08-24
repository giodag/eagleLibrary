package com.univaq.eaglelibrary.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.univaq.eaglelibrary.model.LiteraryWork;

public class LiteraryWorkRepository {
	
	private EntityManager getEntityManager(){
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EL-UP");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		return entityManager;
		
	}
	
	public void save(LiteraryWork literaryWork) {
		EntityManager entityManager = getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.persist(literaryWork);
		tx.commit();
		entityManager.close();
	}

	public LiteraryWork get(Long id) {
		EntityManager entityManager = getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		LiteraryWork literaryWork = this.getEntityManager().find(LiteraryWork.class, id);
		tx.commit();
		entityManager.close();
		return literaryWork;
	}

	public void delete(LiteraryWork literaryWork) {
		EntityManager entityManager = getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		this.getEntityManager().remove(literaryWork);
		tx.commit();
		entityManager.close();
	}

	public LiteraryWork update(LiteraryWork literaryWork) {
		EntityManager entityManager = getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		LiteraryWork userUpdated = this.getEntityManager().merge(literaryWork);
		tx.commit();
		return userUpdated;
	}
	
}
