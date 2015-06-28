package it.albertus.hibernate;

import it.albertus.hibernate.model.inheritance.tpch.CartaDiCreditoTPCH;
import it.albertus.hibernate.model.inheritance.tpch.ContoCorrenteTPCH;
import it.albertus.hibernate.model.inheritance.tpch.MetodoPagamentoTPCH;
import it.albertus.hibernate.model.inheritance.tpch.UtenteTPCH;

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

public class TablePerClassHierarchyTest {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private static final String SPACER = LINE_SEPARATOR + LINE_SEPARATOR;

	private final EntityManager em;

	public static final void main(String... args) {
		new TablePerClassHierarchyTest().run();
	}

	public TablePerClassHierarchyTest() {
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
		UtenteTPCH utente = insert();
		System.out.println(">>> Fine esempio INSERT." + SPACER);

		System.out.println(">>> Inizio esempio UPDATE...");
		utente = update(utente);
		System.out.println(">>> Fine esempio UPDATE." + SPACER);

		// Detach di tutti gli oggetti, per forzare le successive select
		em.clear();

		System.out.println(">>> Inizio esempio SELECT con JPA...");
		selectJpa();
		System.out.println(">>> Fine esempio SELECT con JPA." + SPACER);

		// Detach di tutti gli oggetti, per forzare le successive select
		em.clear();

		System.out.println(">>> Inizio esempio SELECT con Hibernate...");
		List<UtenteTPCH> utenti = selectHibernate();
		System.out.println(">>> Fine esempio SELECT con Hibernate." + SPACER);

		System.out.println(">>> Inizio esempio DELETE con JPA...");
		deleteJpa(utenti);
		System.out.println(">>> Fine esempio DELETE con JPA." + SPACER);

		System.out.println(">>> Inizio secondo esempio SELECT con JPA...");
		selectJpa();
		System.out.println(">>> Fine secondo esempio SELECT con JPA." + SPACER);

		em.close();

		System.out.println(">>> Fine programma " + this.getClass().getSimpleName() + '.');
	}

	private void cleanup() {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.createQuery("DELETE FROM " + ContoCorrenteTPCH.class.getName()).executeUpdate();
		em.createQuery("DELETE FROM " + CartaDiCreditoTPCH.class.getName()).executeUpdate();
		em.createQuery("DELETE FROM " + MetodoPagamentoTPCH.class.getName()).executeUpdate();
		em.createQuery("DELETE FROM " + UtenteTPCH.class.getName()).executeUpdate();
		tx.commit();
	}

	private UtenteTPCH insert() {
		UtenteTPCH utente1 = new UtenteTPCH();
		utente1.setUsername("b");
		utente1.setPassword("b");
		utente1.setCognome("Bianchi");
		utente1.setNome("Mario");
		Calendar dataNascita = Calendar.getInstance();
		dataNascita.add(Calendar.YEAR, -30);
		utente1.setDataNascita(dataNascita.getTime());

		CartaDiCreditoTPCH carta = new CartaDiCreditoTPCH();
		carta.setNumero("9999555588883333");
		carta.setTitolare("Mario Rossi");
		Calendar dataScadenza = Calendar.getInstance();
		dataScadenza.add(Calendar.YEAR, 2);
		carta.setScadenza(dataScadenza.getTime());
		utente1.setMetodoPagamento(carta);

		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(utente1);
		tx.commit();
		
		UtenteTPCH utente2 = new UtenteTPCH();
		utente2.setUsername("a");
		utente2.setPassword("a");
		utente2.setCognome("Verdi");
		utente2.setNome("Giorgio");
		utente2.setDataNascita(dataNascita.getTime());

		ContoCorrenteTPCH conto = new ContoCorrenteTPCH();
		conto.setIban("IT35123451234500001234567890");
		conto.setTitolare("Giorgio Verdi");
		utente2.setMetodoPagamento(conto);
		
		tx.begin();
		em.persist(utente2);
		tx.commit();
		
		return utente1;
	}

	private UtenteTPCH update(UtenteTPCH utente) {
		utente.setCognome("Rossi");
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		utente = em.merge(utente);
		tx.commit();
		return utente;
	}

	private void selectJpa() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<UtenteTPCH> criteria = builder.createQuery(UtenteTPCH.class);
		Root<UtenteTPCH> root = criteria.from(UtenteTPCH.class);
		criteria.select(root).where(builder.equal(root.get("cognome"), "Rossi"));
		List<UtenteTPCH> results = em.createQuery(criteria).getResultList();
		System.out.println(results);
	}

	private List<UtenteTPCH> selectHibernate() {
		Session session = em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(UtenteTPCH.class);
		criteria.createAlias("metodoPagamento", "cdc");
		criteria.add(Restrictions.like("cdc.numero", "%"));
		List<UtenteTPCH> results = criteria.list();
		System.out.println(results);
		return results;
	}

	private void deleteJpa(Collection<UtenteTPCH> utenti) {
		if (utenti != null && !utenti.isEmpty()) {
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.remove(utenti.iterator().next());
			tx.commit();
		}
	}

}
