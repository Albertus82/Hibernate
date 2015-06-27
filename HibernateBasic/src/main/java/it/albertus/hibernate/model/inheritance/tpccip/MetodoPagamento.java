package it.albertus.hibernate.model.inheritance.tpccip;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class MetodoPagamento {

	@Column(nullable = false)
	protected String proprietario;

	public String getProprietario() {
		return proprietario;
	}

	public void setProprietario(String proprietario) {
		this.proprietario = proprietario;
	}

}
