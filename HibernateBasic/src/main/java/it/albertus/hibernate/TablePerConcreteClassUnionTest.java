package it.albertus.hibernate;

import it.albertus.hibernate.model.inheritance.tpccu.CartaDiCreditoTPCCU;
import it.albertus.hibernate.model.inheritance.tpccu.ContoCorrenteTPCCU;
import it.albertus.hibernate.model.inheritance.tpccu.UtenteTPCCU;

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

public class TablePerConcreteClassUnionTest {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private static final String SPACER = LINE_SEPARATOR + LINE_SEPARATOR;

	private final EntityManager em;

	public static final void main(String... args) {
		new TablePerConcreteClassUnionTest().run();
	}

	public TablePerConcreteClassUnionTest() {
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
		UtenteTPCCU utente = insert();
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
		List<UtenteTPCCU> utenti = selectHibernate();
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
		em.createNativeQuery("DELETE FROM tpccu_carte_di_credito").executeUpdate();
		em.createQuery("DELETE FROM " + ContoCorrenteTPCCU.class.getName()).executeUpdate();
		em.createQuery("DELETE FROM " + UtenteTPCCU.class.getName()).executeUpdate();

		tx.commit();
	}

	private UtenteTPCCU insert() {
		UtenteTPCCU utente1 = new UtenteTPCCU();
		utente1.setUsername("b");
		utente1.setPassword("b");
		utente1.setCognome("Bianchi");
		utente1.setNome("Mario");
		Calendar dataNascita = Calendar.getInstance();
		dataNascita.add(Calendar.YEAR, -30);
		utente1.setDataNascita(dataNascita.getTime());

		CartaDiCreditoTPCCU carta = new CartaDiCreditoTPCCU();
		carta.setNumero("9999555588883333");
		carta.setProprietario("Mario Rossi");
		Calendar dataScadenza = Calendar.getInstance();
		dataScadenza.add(Calendar.YEAR, 2);
		carta.setScadenza(dataScadenza.getTime());
		utente1.setMetodoPagamento(carta);

		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(utente1);
		tx.commit();
		
		UtenteTPCCU utente2 = new UtenteTPCCU();
		utente2.setUsername("a");
		utente2.setPassword("a");
		utente2.setCognome("Verdi");
		utente2.setNome("Giorgio");
		utente2.setDataNascita(dataNascita.getTime());

		ContoCorrenteTPCCU conto = new ContoCorrenteTPCCU();
		conto.setIban("IT35123451234500001234567890");
		conto.setProprietario("Giorgio Verdi");
		utente2.setMetodoPagamento(carta);
		
		tx.begin();
		em.persist(utente2);
		tx.commit();
		
		return utente1;
	}

	private UtenteTPCCU update(UtenteTPCCU utente) {
		utente.setCognome("Rossi");
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		utente = em.merge(utente);
		tx.commit();
		return utente;
	}

	private void selectJpa() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<UtenteTPCCU> criteria = builder.createQuery(UtenteTPCCU.class);
		Root<UtenteTPCCU> root = criteria.from(UtenteTPCCU.class);
		criteria.select(root).where(builder.equal(root.get("cognome"), "Rossi"));
		List<UtenteTPCCU> results = em.createQuery(criteria).getResultList();
		System.out.println(results);
	}

	private List<UtenteTPCCU> selectHibernate() {
		Session session = em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(UtenteTPCCU.class);
		criteria.createAlias("metodoPagamento", "cdc");
		criteria.add(Restrictions.like("cdc.numero", "%"));
		List<UtenteTPCCU> results = criteria.list();
		System.out.println(results);
		return results;
	}

	private void deleteJpa(Collection<UtenteTPCCU> utenti) {
		if (utenti != null && !utenti.isEmpty()) {
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.remove(utenti.iterator().next());
			tx.commit();
		}
	}

}
