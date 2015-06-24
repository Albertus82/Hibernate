package it.albertus.hibernate;

import it.albertus.hibernate.model.Offerta;
import it.albertus.hibernate.model.Oggetto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class HibernateBasicTest {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	private final EntityManager em;

	public static final void main(String... args) {
		new HibernateBasicTest().run();
	}

	public HibernateBasicTest() {
		System.out.println(">>> Inizio programma.");
		em = Persistence.createEntityManagerFactory("jpa_test").createEntityManager();
		System.out.println(">>> Inizializzazione completata." + LINE_SEPARATOR);
	}

	private void run() {
		System.out.println(">>> Inizio pulizia database...");
		cleanup();
		System.out.println(">>> Database ripulito." + LINE_SEPARATOR);

		// Esempio...
		System.out.println(">>> Inizio esempio INSERT...");
		insert();
		System.out.println(">>> Fine esempio INSERT." + LINE_SEPARATOR);

		// Detach di tutti gli oggetti, per forzare le successive select
		em.clear();

		System.out.println(">>> Inizio esempio SELECT con JPA...");
		selectJpa();
		System.out.println(">>> Fine esempio SELECT con JPA." + LINE_SEPARATOR);

		// Detach di tutti gli oggetti, per forzare le successive select
		em.clear();

		System.out.println(">>> Inizio esempio SELECT con Hibernate...");
		selectHibernate();
		System.out.println(">>> Fine esempio SELECT con Hibernate." + LINE_SEPARATOR);

		em.close();

		System.out.println(">>> Fine programma.");
	}

	private void selectHibernate() {
		Session session = em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Oggetto.class);
		criteria.add(Restrictions.eq("descrizione", "Macchina fotografica reflex Nikon F100"));
		List<Oggetto> results = criteria.list();
		System.out.println(results);
	}

	private void selectJpa() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Oggetto> cq = cb.createQuery(Oggetto.class);
		Root<Oggetto> c = cq.from(Oggetto.class);
		cq.select(cq.from(Oggetto.class)).where(cb.equal(c.get("descrizione"), "Macchina fotografica reflex Nikon F100"));
		List<Oggetto> results = em.createQuery(cq).getResultList();
		System.out.println(results);
	}

	private void insert() {
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		Oggetto oggetto = new Oggetto();
		oggetto.setDescrizione("Macchina fotografica reflex Nikon F100");
		oggetto.setDataInserimento(new Date());

		Offerta offerta1 = new Offerta();
		offerta1.setImporto(new BigDecimal(100));
		offerta1.setOggetto(oggetto);

		Offerta offerta2 = new Offerta();
		offerta2.setImporto(new BigDecimal("145.50"));
		offerta2.setOggetto(oggetto);

		em.persist(offerta1);
		em.persist(offerta2);

		tx.commit();
	}

	private void cleanup() {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.createNativeQuery("DELETE FROM offerte").executeUpdate();
		em.createQuery("DELETE FROM it.albertus.hibernate.model.Oggetto").executeUpdate();
		tx.commit();
	}

}
