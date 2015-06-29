package it.albertus.spring.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class BaseDAO {

	@PersistenceContext
	protected EntityManager entityManager;
	
}
