package com.univaq.eaglelibrary.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.springframework.stereotype.Repository;

import com.univaq.eaglelibrary.model.User;

@Repository
public class UserRepository {
	
	private EntityManager getEntityManager(){
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EL-UP");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		return entityManager;
		
	}

	public User save(User user) {
		EntityManager entityManager = getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.persist(user);
		user = entityManager.find(User.class, user.getId());
		tx.commit();
		entityManager.close();
		return user;
	}

	public User get(Long id) {
		EntityManager entityManager = getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		User user = this.getEntityManager().find(User.class, id);
		entityManager.close();
		return user;
	}

	public void delete(User user) {
		EntityManager entityManager = getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		this.getEntityManager().remove(user);
		tx.commit();
		entityManager.close();
	}

	public User update(User user) {
		EntityManager entityManager = getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		User userUpdated = this.getEntityManager().merge(user);
		tx.commit();
		return userUpdated;
	}

}
