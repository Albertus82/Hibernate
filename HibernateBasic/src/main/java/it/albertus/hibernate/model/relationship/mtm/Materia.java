package it.albertus.hibernate.model.relationship.mtm;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "MATERIE")
public class Materia {

	@Id
	@SequenceGenerator(name = "seq_materie", sequenceName = "SEQ_MATERIE")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_materie")
	@Column(name = "ID_MATERIA")
	private Integer id;
	private String nome;

	@ManyToMany(mappedBy = "materie", cascade = CascadeType.ALL)
	private List<Docente> docenti = new ArrayList<Docente>();

	public void addDocente(Docente docente) {
		docenti.add(docente);
		docente.getMaterie().add(this);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Materia() {}

	public Materia(String nome) {
		super();
		this.nome = nome;
	}

	public List<Docente> getDocenti() {
		return docenti;
	}

	public void setDocenti(List<Docente> docenti) {
		this.docenti = docenti;
	}

	@Override
	public String toString() {
		return "id=" + id + ", nome=" + nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Materia)) {
			return false;
		}
		Materia other = (Materia) obj;
		if (nome == null) {
			if (other.nome != null) {
				return false;
			}
		}
		else if (!nome.equals(other.nome)) {
			return false;
		}
		return true;
	}

}
