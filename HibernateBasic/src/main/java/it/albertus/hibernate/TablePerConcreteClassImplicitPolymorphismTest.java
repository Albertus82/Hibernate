package it.albertus.hibernate;

import it.albertus.hibernate.model.inheritance.tpccip.CartaDiCreditoTPCCIP;
import it.albertus.hibernate.model.inheritance.tpccip.ContoCorrenteTPCCIP;
import it.albertus.hibernate.model.inheritance.tpccip.MetodoPagamentoTPCCIP;
import it.albertus.hibernate.model.inheritance.tpccip.UtenteTPCCIP;

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
	private static final String SPACER = LINE_SEPARATOR + LINE_SEPARATOR;

	private final EntityManager em;

	public static final void main(String... args) {
		new TablePerConcreteClassImplicitPolymorphismTest().run();
	}

	public TablePerConcreteClassImplicitPolymorphismTest() {
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
		UtenteTPCCIP utente = insert();
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
		List<UtenteTPCCIP> utenti = selectHibernate();
		System.out.println(">>> Fine esempio SELECT con Hibernate." + SPACER);

		System.out.println(">>> Inizio esempio DELETE con JPA...");
		deleteJpa(utenti);
		System.out.println(">>> Fine esempio DELETE con JPA." + SPACER);

		System.out.println(">>> Inizio secondo esempio SELECT con JPA...");
		selectJpa();
		System.out.println(">>> Fine secondo esempio SELECT con JPA." + SPACER);

		System.out.println(">>> Inizio esempio SELECT POLIMORFICA con Hibernate...");
		selectPolimorficaHibernate();
		System.out.println(">>> Fine esempio SELECT POLIMORFICA con Hibernate." + SPACER);

		em.close();

		System.out.println(">>> Fine programma " + this.getClass().getSimpleName() + '.');
	}

	private void cleanup() {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.createNativeQuery("DELETE FROM tpccip_carte_di_credito").executeUpdate();
		em.createQuery("DELETE FROM " + ContoCorrenteTPCCIP.class.getName()).executeUpdate();
		em.createQuery("DELETE FROM " + UtenteTPCCIP.class.getName()).executeUpdate();

		tx.commit();
	}

	private UtenteTPCCIP insert() {
		UtenteTPCCIP utente = new UtenteTPCCIP();
		utente.setUsername("a");
		utente.setPassword("a");
		utente.setCognome("Bianchi");
		utente.setNome("Mario");
		Calendar dataNascita = Calendar.getInstance();
		dataNascita.add(Calendar.YEAR, -30);
		utente.setDataNascita(dataNascita.getTime());

		CartaDiCreditoTPCCIP carta = new CartaDiCreditoTPCCIP();
		carta.setNumero("1234567890123456");
		carta.setTitolare("Mario Rossi");
		Calendar dataScadenza = Calendar.getInstance();
		dataScadenza.add(Calendar.YEAR, 2);
		carta.setScadenza(dataScadenza.getTime());
		utente.setCartaDiCredito(carta);

		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(utente);
		tx.commit();
		return utente;
	}

	private UtenteTPCCIP update(UtenteTPCCIP utente) {
		utente.setCognome("Rossi");
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		utente = em.merge(utente);
		tx.commit();
		return utente;
	}

	private void selectJpa() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<UtenteTPCCIP> criteria = builder.createQuery(UtenteTPCCIP.class);
		Root<UtenteTPCCIP> root = criteria.from(UtenteTPCCIP.class);
		criteria.select(root).where(builder.equal(root.get("cognome"), "Rossi"));
		List<UtenteTPCCIP> results = em.createQuery(criteria).getResultList();
		System.out.println(results);
	}

	private List<UtenteTPCCIP> selectHibernate() {
		Session session = em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(UtenteTPCCIP.class);
		criteria.createAlias("cartaDiCredito", "cdc");
		criteria.add(Restrictions.like("cdc.numero", "12345678%"));
		List<UtenteTPCCIP> results = criteria.list();
		System.out.println(results);
		return results;
	}

	private List<MetodoPagamentoTPCCIP> selectPolimorficaHibernate() {
		Session session = em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(MetodoPagamentoTPCCIP.class);
		criteria.add(Restrictions.like("titolare", "Mario%"));
		List<MetodoPagamentoTPCCIP> results = criteria.list();
		System.out.println(results);
		return results;
	}

	private void deleteJpa(Collection<UtenteTPCCIP> utenti) {
		if (utenti != null && !utenti.isEmpty()) {
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.remove(utenti.iterator().next());
			tx.commit();
		}
	}

}
