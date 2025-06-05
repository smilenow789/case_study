package login;

import java.util.Date;

public class Aufgabe {
	private String text;
	private String person;
	Date faelligAm;
	Boolean erledigt;
	float aufwand;

	public Aufgabe(String text, String person) {
		this.text = text;
		this.person = person;
	}
	
	public Aufgabe(String text, String person, Date faelligAm, Boolean erledigt, float aufwand) {
		super();
		this.text = text;
		this.person = person;
		this.faelligAm = faelligAm;
		this.erledigt = erledigt;
		this.aufwand = aufwand;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public Date getFaelligAm() {
		return faelligAm;
	}

	public void setFaelligAm(Date faelligAm) {
		this.faelligAm = faelligAm;
	}

	public Boolean getErledigt() {
		return erledigt;
	}

	public void setErledigt(Boolean erledigt) {
		this.erledigt = erledigt;
	}

	public float getAufwand() {
		return aufwand;
	}

	public void setAufwand(float aufwand) {
		this.aufwand = aufwand;
	}
}
