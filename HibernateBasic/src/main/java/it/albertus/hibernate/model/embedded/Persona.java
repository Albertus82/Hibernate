package it.albertus.hibernate.model.embedded;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PERSONE")
public class Persona {

	@Id
	@SequenceGenerator(name = "seq_persone", sequenceName = "SEQ_PERSONE")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_persone")
	@Column(name = "ID_PERSONA")
	private Long id;

	private String cognome;

	private String nome;

	@Embedded
	private Indirizzo indirizzo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Indirizzo getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(Indirizzo indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String toString() {
		return "id=" + id + ", cognome=" + cognome + ", nome=" + nome + ", indirizzo={" + indirizzo + '}';
	}

}
