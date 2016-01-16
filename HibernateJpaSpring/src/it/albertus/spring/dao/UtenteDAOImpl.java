package it.albertus.spring.dao;

import it.albertus.spring.model.Utente;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
// Non occorre specificare il nome del bean PlatformTransactionManager ("transactionManager") se ne esiste solo uno!
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class UtenteDAOImpl extends BaseDAO implements UtenteDAO {

	private static final Log log = LogFactory.getLog(UtenteDAOImpl.class);

	@Override
	@Transactional(propagation = Propagation.MANDATORY)	// Bisogna arrivare qui con una transazione gia' attiva!
	public void save(Utente model) {
		entityManager.persist(model);
		System.out.println(">>> Commit non ancora effettuato!");
	}

	@Override
	public Utente auth(final String username, final String password) {
		Utente utente;

		try {
			Criteria crit = entityManager.unwrap(Session.class).createCriteria(Utente.class);
			crit.add(Restrictions.eq("username", username));
			crit.add(Restrictions.eq("password", password));
			utente = (Utente) crit.uniqueResult();
		}
		catch (EmptyResultDataAccessException e) {
			log.info("Autenticazione fallita perche' l'utente non esiste oppure la password e' errata.");
			utente = null;
		}
		catch (Exception e) {
			log.error("Autenticazione fallita per altri motivi (database non disponibile, ecc.).\r\n" + ExceptionUtils.getStackTrace(e));
			utente = null;
		}

		return utente;
	}

}
