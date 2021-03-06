package it.albertus.spring.config;

import javax.persistence.EntityManagerFactory;

import org.apache.commons.configuration.ConfigurationConverter;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.DatabaseConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

@Configuration
@ComponentScan(basePackages = { "it.albertus.spring" }, excludeFilters = { @Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class) })
@EnableTransactionManagement // Abilita l'AOP per la gestione delle transazioni.
public class RootConfig {
	
	private static final Log log = LogFactory.getLog(RootConfig.class);

	/**
	 * Gestisce la transazionalita' dei metodi che accedono al database. In
	 * presenza di piu' transaction manager, bisogna specificare in
	 * @Transactional quale si vuole usare, pena "NoUniqueBeanDefinitionException".
	 */
	@Bean
	protected JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

	/** Integrazione con Hibernate */
	@Bean
	public LocalEntityManagerFactoryBean entityManagerFactory() {
		LocalEntityManagerFactoryBean emfb = new LocalEntityManagerFactoryBean();
		emfb.setPersistenceUnitName("jpaPersistenceUnit");
		return emfb;
	}

	/**
	 * Una follia che carica le properties di configurazione da una tabella del
	 * database invece che da file come di norma
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(DatabaseConfiguration dbc) {
		final PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
		try {
			pspc.setProperties(ConfigurationConverter.getProperties(dbc));
		}
		catch (Exception e) {
			log.fatal("Impossibile caricare la configurazione dal database: " + ExceptionUtils.getStackTrace(e));
		}
		return pspc;
	}

	@Bean
	public static DatabaseConfiguration configuration() throws ConfigurationException {
		return loadConfigurationFromDatabase();
	}

	private static DatabaseConfiguration loadConfigurationFromDatabase() throws ConfigurationException {
		/* Estrazione parametri di connessione dal database dal persistence.xml di JPA */
		final XMLConfiguration databaseConfig = new XMLConfiguration();
		databaseConfig.load(WebConfig.class.getResourceAsStream("/META-INF/persistence.xml"));
		final NodeList nodes = databaseConfig.getDocument().getElementsByTagName("property");
		final DriverManagerDataSource jpaDataSource = new DriverManagerDataSource();

		for (int i = 0; i < nodes.getLength(); i++) {
			final NamedNodeMap map = nodes.item(i).getAttributes();
			final String key = map.getNamedItem("name").getNodeValue();
			final String value = map.getNamedItem("value").getNodeValue();
			if ("hibernate.connection.driver_class".equals(key)) {
				jpaDataSource.setDriverClassName(value);
			}
			else if ("hibernate.connection.url".equals(key)) {
				jpaDataSource.setUrl(value);
			}
			else if ("hibernate.connection.username".equals(key)) {
				jpaDataSource.setUsername(value);
			}
			else if ("hibernate.connection.password".equals(key)) {
				jpaDataSource.setPassword(value);
			}
		}

		/* Caricamento configurazione */
		DatabaseConfiguration dbc = new DatabaseConfiguration(jpaDataSource, "configurazione", "chiave", "valore");
		return dbc;
	}

}
