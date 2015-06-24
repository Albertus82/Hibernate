package it.albertus.hibernate.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "OFFERTE")
public class Offerta {

	private Long id;
	private Oggetto oggetto;
	private BigDecimal importo;

	@Id
	@SequenceGenerator(name = "seq_offerte", sequenceName = "SEQ_OFFERTE")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_offerte")
	@Column(name = "ID_OFFERTA")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_OGGETTO")
	public Oggetto getOggetto() {
		return oggetto;
	}

	public void setOggetto(Oggetto oggetto) {
		this.oggetto = oggetto;
	}

	@Column(name = "IMPORTO")
	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

}
