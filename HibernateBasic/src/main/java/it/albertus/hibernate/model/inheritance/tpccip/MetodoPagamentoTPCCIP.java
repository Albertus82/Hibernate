package it.albertus.hibernate.model.inheritance.tpccip;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class MetodoPagamentoTPCCIP {

	@Column(nullable = false)
	protected String titolare;

	public String getTitolare() {
		return titolare;
	}

	public void setTitolare(String titolare) {
		this.titolare = titolare;
	}

}
