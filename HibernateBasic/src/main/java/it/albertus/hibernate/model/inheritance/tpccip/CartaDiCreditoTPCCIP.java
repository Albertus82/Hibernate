package it.albertus.hibernate.model.inheritance.tpccip;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TPCCIP_CARTE_DI_CREDITO")
public class CartaDiCreditoTPCCIP extends MetodoPagamentoTPCCIP {

	@Id
	@SequenceGenerator(name = "seq_carte_di_credito", sequenceName = "SEQ_CARTE_DI_CREDITO")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_carte_di_credito")
	@Column(name = "ID_CARTA_DI_CREDITO")
	private Long id;
	private String numero;

	@Column(name = "DATA_SCADENZA")
	private Date scadenza;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Date getScadenza() {
		return scadenza;
	}

	public void setScadenza(Date scadenza) {
		this.scadenza = scadenza;
	}

	@Override
	public String toString() {
		return "id=" + id + ", numero=" + numero + ", scadenza=" + scadenza + ", titolare=" + titolare;
	}

}
