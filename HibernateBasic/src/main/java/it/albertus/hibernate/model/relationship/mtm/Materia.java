package it.albertus.hibernate.model.relationship.mtm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

	@Override
	public String toString() {
		return "id=" + id + ", nome=" + nome;
	}

}
