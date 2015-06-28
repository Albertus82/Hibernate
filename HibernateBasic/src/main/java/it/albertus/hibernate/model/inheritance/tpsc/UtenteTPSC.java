package it.albertus.hibernate.model.inheritance.tpsc;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TPSC_UTENTI")
public class UtenteTPSC {

	@Id
	private String username;

	private String password;
	private String cognome;
	private String nome;

	@Column(name = "DATA_INSERIMENTO", insertable = false, updatable = false)
	private Date dataInserimento;

	@Column(name = "DATA_NASCITA")
	private Date dataNascita;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_METODO_PAGAMENTO")
	private MetodoPagamentoTPSC metodoPagamento;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public MetodoPagamentoTPSC getMetodoPagamento() {
		return metodoPagamento;
	}

	public void setMetodoPagamento(MetodoPagamentoTPSC metodoPagamento) {
		this.metodoPagamento = metodoPagamento;
	}

	@Override
	public String toString() {
		return "username=" + username + ", cognome=" + cognome + ", nome=" + nome + ", dataNascita=" + (dataNascita != null ? dataNascita : "null") + ", metodoPagamento="
				+ (metodoPagamento != null ? "{" + metodoPagamento + "}" : "null") + ", dataInserimento=" + (dataInserimento != null ? dataInserimento : "null");
	}

}
