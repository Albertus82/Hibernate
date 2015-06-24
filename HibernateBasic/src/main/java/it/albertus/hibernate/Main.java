package it.albertus.hibernate;

import it.albertus.hibernate.model.Oggetto;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

	public static final void main(String... args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa_test");
		System.out.println("Inizializzazione completata.");

		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		Oggetto o = new Oggetto();
		o.setDescrizione("Oggetto di prova n. 1");
		o.setDataInserimento(new Date());

		em.persist(o);

		tx.commit();

		em.close();
		
		System.out.println("Fine!");
	}
}
