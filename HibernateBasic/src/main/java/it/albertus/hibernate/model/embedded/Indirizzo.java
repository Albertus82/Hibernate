package it.albertus.hibernate.model.embedded;

import javax.persistence.Embeddable;

@Embeddable
public class Indirizzo {

	private String via;
	private String citta;
	private String cap;

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String toString() {
		return "via=" + via + ", cap=" + cap + ", citt\u00E0=" + citta;
	}
}
