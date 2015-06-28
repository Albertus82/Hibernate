package it.albertus.hibernate.model.inheritance.tpch;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("1")
public class ContoCorrenteTPCH extends MetodoPagamentoTPCH {

	@Column(name = "CONTO_IBAN")
	private String iban;

	@Column(name = "CONTO_NOME_BANCA")
	private String nomeBanca;

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getNomeBanca() {
		return nomeBanca;
	}

	public void setNomeBanca(String nomeBanca) {
		this.nomeBanca = nomeBanca;
	}

	@Override
	public String toString() {
		return "iban=" + iban + ", nomeBanca=" + nomeBanca;
	}

}
