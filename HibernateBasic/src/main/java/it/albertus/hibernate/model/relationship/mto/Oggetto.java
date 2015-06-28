package it.albertus.hibernate.model.relationship.mto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "OGGETTI")
public class Oggetto {

	private Long id;
	private String descrizione;
	private Date dataInserimento;
	private Set<Offerta> offerte = new HashSet<Offerta>();

	@Id
	@SequenceGenerator(name = "seq_oggetti", sequenceName = "SEQ_OGGETTI")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_oggetti")
	@Column(name = "ID_OGGETTO")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "DESCRIZIONE")
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Column(name = "DATA_INSERIMENTO")
	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	// La relazione e' gestita dall'altro lato!
	@OneToMany(mappedBy = "oggetto", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
	public Set<Offerta> getOfferte() {
		return offerte;
	}

	public void setOfferte(Set<Offerta> offerte) {
		this.offerte = offerte;
	}

	public void addOfferta(Offerta offerta) {
		offerta.setOggetto(this); // Serve a permettere il salvataggio dell'offerta!
		offerte.add(offerta);
	}

	@Override
	public String toString() {
		return "id=" + id + ", descrizione=" + descrizione + ", dataInserimento=" + dataInserimento + ", #offerte=" + offerte.size();
	}

}
