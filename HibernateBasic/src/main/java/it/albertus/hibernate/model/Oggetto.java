package it.albertus.hibernate.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="OGGETTI")
public class Oggetto {

	private Long id;
	private String descrizione;
	private Date dataInserimento;

	@Id
	@SequenceGenerator(name="seq_oggetti", sequenceName="SEQ_OGGETTI")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_oggetti")
	@Column(name="ID_OGGETTO")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="DESCRIZIONE")
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Column(name="DATA_INSERIMENTO")
	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

}
