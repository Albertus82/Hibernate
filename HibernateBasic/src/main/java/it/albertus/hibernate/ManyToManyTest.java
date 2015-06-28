package it.albertus.hibernate;

import it.albertus.hibernate.model.relationship.mtm.Docente;
import it.albertus.hibernate.model.relationship.mtm.Materia;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class ManyToManyTest {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private static final String SPACER = LINE_SEPARATOR + LINE_SEPARATOR;

	private final EntityManager em;

	public static final void main(String... args) {
		new ManyToManyTest().run();
	}

	public ManyToManyTest() {
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
//		selectHibernate();
		System.out.println(">>> Fine esempio SELECT con Hibernate." + SPACER);

		em.close();

		System.out.println(">>> Fine programma " + this.getClass().getSimpleName() + '.');
	}

	private void cleanup() {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.createQuery("DELETE FROM " + Docente.class.getName()).executeUpdate();
		System.out.println("> Docenti eliminati." + LINE_SEPARATOR);
		em.createQuery("DELETE FROM " + Materia.class.getName()).executeUpdate();
		System.out.println("> Materie eliminate." + LINE_SEPARATOR);
		tx.commit();
	}

	private void insert() {

		Docente docente1 = new Docente();
		docente1.setCognome("Bianchi");
		docente1.setNome("Mario");
		List<Materia> materie1 = new ArrayList<Materia>();
		materie1.add(new Materia("Italiano"));
		materie1.add(new Materia("Latino"));
		materie1.add(new Materia("Geografia"));
		docente1.setMaterie(materie1);

		EntityTransaction tx = em.getTransaction();

		tx.begin();
		em.persist(docente1);
		tx.commit();
		System.out.println("> Primo docente inserito." + LINE_SEPARATOR);
		
		// Inizio una seconda transazione...
		Docente docente2 = new Docente();
		docente2.setCognome("Neri");
		docente2.setNome("Vittorio");
		List<Materia> materie2 = new ArrayList<Materia>();
		materie2.add(new Materia("Storia"));
		materie2.add(new Materia("Filosofia"));
		docente2.setMaterie(materie2);
		
		tx.begin();
		em.persist(docente2);
		tx.commit();
	}

	private List<Docente> selectJpa() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Docente> criteria = builder.createQuery(Docente.class);
		Root<Docente> root = criteria.from(Docente.class);
		criteria.select(root).where(builder.equal(root.get("cognome"), "Bianchi"));
		List<Docente> results = em.createQuery(criteria).getResultList();
		System.out.println(results);
		return results;
	}
//
//	private void selectHibernate() {
//		Session session = em.unwrap(Session.class);
//		Criteria criteria = session.createCriteria(Oggetto.class);
//		criteria.add(Restrictions.like("descrizione", "Obiettivo%"));
//		List<?> results = criteria.list();
//		System.out.println(results);
//	}

}
