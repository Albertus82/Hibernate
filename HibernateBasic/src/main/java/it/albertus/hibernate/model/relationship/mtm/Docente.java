package it.albertus.hibernate.model.relationship.mtm;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "DOCENTI")
public class Docente {

	@Id
	@SequenceGenerator(name = "seq_docenti", sequenceName = "SEQ_DOCENTI")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_docenti")
	@Column(name = "ID_DOCENTE")
	private Long id;
	private String cognome;
	private String nome;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
		name = "DOCENTI_MATERIE",
		joinColumns = { @JoinColumn(name = "ID_DOCENTE") },
		inverseJoinColumns = { @JoinColumn(name = "ID_MATERIA") }
	)
	private Set<Materia> materie = new HashSet<Materia>();

	public void addMateria(Materia materia) {
		materie.add(materia);
		materia.getDocenti().add(this);
	}

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

	public Set<Materia> getMaterie() {
		return materie;
	}

	public void setMaterie(Set<Materia> materie) {
		this.materie = materie;
	}

	@Override
	public String toString() {
		return "id=" + id + ", cognome=" + cognome + ", nome=" + nome + ", materie={" + (materie != null ? materie : "null") + "}";
	}

}
