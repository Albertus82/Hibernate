package it.albertus.spring.service;

import it.albertus.spring.dao.UtenteDAO;
import it.albertus.spring.model.Utente;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class UtenteServiceImpl implements UtenteService, BeanNameAware {

	private static final Log log = LogFactory.getLog(UtenteServiceImpl.class);

	@Autowired
	UtenteDAO utenteDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void save(Utente model) {
		try {
			utenteDao.save(model);
		}
		catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
		}
	}

	@Override
	public Utente auth(String username, String password) {
		return utenteDao.auth(username, password);
	}

	@Override
	public void setBeanName(String name) {
		log.info("Esempio di bean BeanNameAware: nome del bean: " + name);
	}

}