package it.albertus.hibernate.model.inheritance.tpsc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="TPSC_CONTI_CORRENTI")
@PrimaryKeyJoinColumn(name = "ID_CONTO_CORRENTE")
public class ContoCorrenteTPSC extends MetodoPagamentoTPSC {

	@Column(nullable = false)
	private String iban;

	@Column(name = "NOME_BANCA")
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
