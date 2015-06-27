package it.albertus.hibernate;

import it.albertus.hibernate.model.inheritance.Utente;
import it.albertus.hibernate.model.inheritance.tpccip.CartaDiCredito;
import it.albertus.hibernate.model.inheritance.tpccip.ContoCorrente;

import java.util.Calendar;
import java.util.Collection;
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

public class TablePerConcreteClassImplicitPolymorphismTest {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	private final EntityManager em;

	public static final void main(String... args) {
		new TablePerConcreteClassImplicitPolymorphismTest().run();
	}

	public TablePerConcreteClassImplicitPolymorphismTest() {
		System.out.println(">>> Inizio programma " + this.getClass().getSimpleName() + '.');
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
		List<Utente> utenti = selectHibernate();
		System.out.println(">>> Fine esempio SELECT con Hibernate." + LINE_SEPARATOR);

		System.out.println(">>> Inizio esempio DELETE con JPA...");
		deleteJpa(utenti);
		System.out.println(">>> Fine esempio DELETE con JPA." + LINE_SEPARATOR);
		
		System.out.println(">>> Inizio secondo esempio SELECT con JPA...");
		selectJpa();
		System.out.println(">>> Fine secondo esempio SELECT con JPA." + LINE_SEPARATOR);
		
		em.close();

		System.out.println(">>> Fine programma " + this.getClass().getSimpleName() + '.');
	}

	private void cleanup() {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.createNativeQuery("DELETE FROM tpccip_carte_di_credito").executeUpdate();
		em.createQuery("DELETE FROM " + ContoCorrente.class.getName()).executeUpdate();
		em.createQuery("DELETE FROM " + Utente.class.getName()).executeUpdate();

		tx.commit();
	}

	private void insert() {
		Utente utente = new Utente();
		utente.setUsername("a");
		utente.setPassword("a");
		utente.setCognome("Rossi");
		utente.setNome("Mario");
		Calendar dataNascita = Calendar.getInstance();
		dataNascita.add(Calendar.YEAR, -30);
		utente.setDataNascita(dataNascita.getTime());

		CartaDiCredito carta = new CartaDiCredito();
		carta.setNumero("1234567890123456");
		carta.setProprietario("Mario Rossi");
		Calendar dataScadenza = Calendar.getInstance();
		dataScadenza.add(Calendar.YEAR, 2);
		carta.setScadenza(dataScadenza.getTime());
		utente.setCartaDiCredito(carta);

		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(utente);
		tx.commit();
	}

	private void selectJpa() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Utente> criteria = builder.createQuery(Utente.class);
		Root<Utente> root = criteria.from(Utente.class);
		criteria.select(root).where(builder.equal(root.get("cognome"), "Rossi"));
		List<Utente> results = em.createQuery(criteria).getResultList();
		System.out.println(results);
	}

	private List<Utente> selectHibernate() {
		Session session = em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Utente.class);
		criteria.createAlias("cartaDiCredito", "cdc");
		criteria.add(Restrictions.like("cdc.numero", "12345678%"));
		List<Utente> results = criteria.list();
		System.out.println(results);
		return results;
	}
	
	private void deleteJpa(Collection<Utente> utenti) {
		if (utenti != null && !utenti.isEmpty()) {
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.remove(utenti.iterator().next());
			tx.commit();
		}
	}

}
