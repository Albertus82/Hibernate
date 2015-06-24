package it.albertus.hibernate;

import it.albertus.hibernate.model.Offerta;
import it.albertus.hibernate.model.Oggetto;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class HibernateBasicTest {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	private final EntityManager em;

	public static final void main(String... args) {
		new HibernateBasicTest().run();
	}

	public HibernateBasicTest() {
		em = Persistence.createEntityManagerFactory("jpa_test").createEntityManager();
		System.out.println(">>> Inizializzazione completata." + LINE_SEPARATOR);
	}

	private void run() {
		// Pulizia del database...
		cleanup(em);
		System.out.println(">>> Database ripulito." + LINE_SEPARATOR);

		// Esempio...
		System.out.println(">>> Inizio esempio...");
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		Oggetto oggetto = new Oggetto();
		oggetto.setDescrizione("Oggetto di prova n. 1");
		oggetto.setDataInserimento(new Date());

		Offerta offerta1 = new Offerta();
		offerta1.setImporto(new BigDecimal(1000));
		offerta1.setOggetto(oggetto);

		Offerta offerta2 = new Offerta();
		offerta2.setImporto(new BigDecimal("1450.50"));
		offerta2.setOggetto(oggetto);

		em.persist(offerta1);
		em.persist(offerta2);

		tx.commit();

		em.close();

		System.out.println(">>> Fine esempio.");
	}

	private void cleanup(EntityManager em) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.createNativeQuery("DELETE FROM offerte").executeUpdate();
		em.createQuery("DELETE FROM it.albertus.hibernate.model.Oggetto").executeUpdate();
		tx.commit();
	}

}
