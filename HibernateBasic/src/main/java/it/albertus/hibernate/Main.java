package it.albertus.hibernate;

import it.albertus.hibernate.model.Offerta;
import it.albertus.hibernate.model.Oggetto;

import java.math.BigDecimal;
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

		Oggetto oggetto = new Oggetto();
		oggetto.setDescrizione("Oggetto di prova n. 1");
		oggetto.setDataInserimento(new Date());

		Offerta offerta = new Offerta();
		offerta.setImporto(new BigDecimal(1000));
		offerta.setOggetto(oggetto);

		em.persist(offerta);

		tx.commit();

		em.close();

		System.out.println("Fine!");
	}
}
