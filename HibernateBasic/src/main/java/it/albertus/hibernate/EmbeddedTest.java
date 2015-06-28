package it.albertus.hibernate;

import it.albertus.hibernate.model.embedded.Indirizzo;
import it.albertus.hibernate.model.embedded.Persona;

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

public class EmbeddedTest {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private static final String SPACER = LINE_SEPARATOR + LINE_SEPARATOR;

	private final EntityManager em;

	public static final void main(String... args) {
		new EmbeddedTest().run();
	}

	public EmbeddedTest() {
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
		em.createQuery("DELETE FROM " + Persona.class.getName()).executeUpdate();
		tx.commit();
	}

	private void insert() {
		Persona persona = new Persona();
		persona.setCognome("Rossi");
		persona.setNome("Mario");

		Indirizzo indirizzo = new Indirizzo();
		indirizzo.setVia("Via del Corso 210");
		indirizzo.setCap("00100");
		indirizzo.setCitta("Roma");

		persona.setIndirizzo(indirizzo);

		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(persona);
		tx.commit();
	}

	private void selectJpa() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Persona> criteria = builder.createQuery(Persona.class);
		Root<Persona> root = criteria.from(Persona.class);
		criteria.select(root).where(builder.equal(root.get("cognome"), "Rossi"));
		List<Persona> results = em.createQuery(criteria).getResultList();
		System.out.println(results);
	}

	private void selectHibernate() {
		Session session = em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Persona.class);
		criteria.add(Restrictions.like("indirizzo.via", "%Corso%"));
		List<?> results = criteria.list();
		System.out.println(results);
	}

}
