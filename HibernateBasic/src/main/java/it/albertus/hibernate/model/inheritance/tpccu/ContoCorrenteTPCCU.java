package it.albertus.hibernate.model.inheritance.tpccu;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TPCCU_CONTI_CORRENTI")
@AttributeOverride(name = "id", column = @Column(name = "ID_CONTO_CORRENTE", nullable = false))
public class ContoCorrenteTPCCU extends MetodoPagamentoTPCCU {

	private String iban;

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

}
