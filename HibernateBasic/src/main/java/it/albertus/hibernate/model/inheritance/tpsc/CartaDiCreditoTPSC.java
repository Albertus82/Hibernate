package it.albertus.hibernate.model.inheritance.tpsc;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="TPSC_CARTE_DI_CREDITO")
@PrimaryKeyJoinColumn(name = "ID_CARTA_DI_CREDITO")
public class CartaDiCreditoTPSC extends MetodoPagamentoTPSC {

	@Column(nullable = false)
	private String numero;

	@Column(name = "DATA_SCADENZA", nullable = false)
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
		return "id=" + id + ", numero=" + numero + ", scadenza=" + (scadenza != null ? scadenza : "null") + ", titolare=" + titolare;
	}

}
