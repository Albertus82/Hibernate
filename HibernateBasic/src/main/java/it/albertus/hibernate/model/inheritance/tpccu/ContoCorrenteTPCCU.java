package it.albertus.hibernate.model.inheritance.tpccu;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TPCCU_CONTI_CORRENTI")
public class ContoCorrenteTPCCU extends MetodoPagamentoTPCCU {

	private String iban;

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

}
