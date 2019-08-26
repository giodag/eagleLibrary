package com.univaq.eaglelibrary.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.univaq.eaglelibrary.model.Page;

public class PageRepository {
	
	private EntityManager getEntityManager(){
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EL-UP");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		return entityManager;
		
	}
	
	public void save(Page page) {
		EntityManager entityManager = getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.persist(page);
		tx.commit();
		entityManager.close();
	}

	public Page get(Long id) {
		EntityManager entityManager = getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		Page page = this.getEntityManager().find(Page.class, id);
		tx.commit();
		entityManager.close();
		return page;
	}

	public void delete(Page page) {
		EntityManager entityManager = getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		this.getEntityManager().remove(page);
		tx.commit();
		entityManager.close();
	}

	public Page update(Page page) {
		EntityManager entityManager = getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		Page userUpdated = this.getEntityManager().merge(page);
		tx.commit();
		return userUpdated;
	}

}
