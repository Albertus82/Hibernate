package it.albertus.hibernate;

import it.albertus.hibernate.model.relationship.mto.Offerta;
import it.albertus.hibernate.model.relationship.mto.Oggetto;

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

public class ManyToOneTest {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private static final String SPACER = LINE_SEPARATOR + LINE_SEPARATOR;

	private final EntityManager em;

	public static final void main(String... args) {
		new ManyToOneTest().run();
	}

	public ManyToOneTest() {
		System.out.println(">>> Inizio programma " + this.getClass().getSimpleName() + '.');
		em = Persistence.createEntityManagerFactory("jpa_test").createEntityManager();
		System.out.println(">>> Inizializzazione completata." + SPACER);
	}

	private void run() {
		System.out.println(">>> Inizio pulizia database...");
		cleanup();
		System.out.println(">>> Database ripulito." + SPACER);

		// Esempio...
		System.out.println(">>> Inizio esempio INSERT...");
		insert();
		System.out.println(">>> Fine esempio INSERT." + SPACER);

		// Detach di tutti gli oggetti, per forzare le successive select
		em.clear();

		System.out.println(">>> Inizio esempio SELECT con JPA...");
		selectJpa();
		System.out.println(">>> Fine esempio SELECT con JPA." + SPACER);

		// Detach di tutti gli oggetti, per forzare le successive select
		em.clear();

		System.out.println(">>> Inizio esempio SELECT con Hibernate...");
		selectHibernate();
		System.out.println(">>> Fine esempio SELECT con Hibernate." + SPACER);

		em.close();

		System.out.println(">>> Fine programma " + this.getClass().getSimpleName() + '.');
	}

	private void cleanup() {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.createNativeQuery("DELETE FROM offerte").executeUpdate();
		em.createQuery("DELETE FROM " + Oggetto.class.getName()).executeUpdate();
		tx.commit();
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

		// Inizio una seconda transazione...
		tx.begin();
		Oggetto oggetto2 = new Oggetto();
		oggetto2.setDescrizione("Obiettivo Nikon 50mm F1.4");
		oggetto2.setDataInserimento(new Date());

		Offerta offerta3 = new Offerta();
		offerta3.setImporto(new BigDecimal(500));

		Offerta offerta4 = new Offerta();
		offerta4.setImporto(new BigDecimal("550.01"));

		oggetto2.addOfferta(offerta3);
		oggetto2.addOfferta(offerta4);

		em.persist(oggetto2);
		tx.commit();
	}

	private void selectJpa() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Oggetto> criteria = builder.createQuery(Oggetto.class);
		Root<Oggetto> root = criteria.from(Oggetto.class);
		criteria.select(root).where(builder.equal(root.get("descrizione"), "Macchina fotografica reflex Nikon F100"));
		List<Oggetto> results = em.createQuery(criteria).getResultList();
		System.out.println(results);
	}

	private void selectHibernate() {
		Session session = em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Oggetto.class);
		criteria.add(Restrictions.like("descrizione", "Obiettivo%"));
		List<?> results = criteria.list();
		System.out.println(results);
	}

}
