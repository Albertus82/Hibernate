package it.albertus.hibernate.model.inheritance.tpccu;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TPCCU_CARTE_DI_CREDITO")
public class CartaDiCreditoTPCCU extends MetodoPagamentoTPCCU {

	private String numero;

	@Column(name = "DATA_SCADENZA")
	private Date scadenza;

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

	public String toString() {
		return "id=" + id + ", numero=" + numero + ", scadenza=" + scadenza + ", proprietario=" + proprietario;
	}

}
