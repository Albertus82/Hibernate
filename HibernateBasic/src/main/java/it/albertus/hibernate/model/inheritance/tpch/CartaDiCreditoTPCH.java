package it.albertus.hibernate.model.inheritance.tpch;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("2")
public class CartaDiCreditoTPCH extends MetodoPagamentoTPCH {

	@Column(name = "CARTA_NUMERO")
	private String numero;

	@Column(name = "CARTA_DATA_SCADENZA")
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

	@Override
	public String toString() {
		return "id=" + id + ", numero=" + numero + ", scadenza=" + scadenza + ", titolare=" + titolare;
	}

}
