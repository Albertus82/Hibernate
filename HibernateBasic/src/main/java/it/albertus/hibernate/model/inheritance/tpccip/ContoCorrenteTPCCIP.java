package it.albertus.hibernate.model.inheritance.tpccip;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TPCCIP_CONTI_CORRENTI")
public class ContoCorrenteTPCCIP extends MetodoPagamentoTPCCIP {

	@Id
	@SequenceGenerator(name = "seq_conti_correnti", sequenceName = "SEQ_CONTI_CORRENTI")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_conti_correnti")
	@Column(name = "ID_CONTO_CORRENTE")
	private Long id;
	private String iban;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

}
