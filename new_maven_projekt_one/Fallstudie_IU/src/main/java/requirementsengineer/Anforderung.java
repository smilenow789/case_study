package requirementsengineer;

import java.io.Serializable;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class Anforderung implements Serializable {

	private int id;
	private String anforderungstitel;
	private String beschreibung;

	public Anforderung() {
	}

	public Anforderung(String anforderungstitel, String beschreibung) {
		this.id = 1;
		this.anforderungstitel = anforderungstitel;
		this.beschreibung = beschreibung;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAnforderungstitel() {
		return anforderungstitel;
	}

	public void setAnforderungstitel(String anforderungstitel) {
		this.anforderungstitel = anforderungstitel;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public void anforderungErstellen() {
		System.out.println("Anforderung erstellt:");
		System.out.println("Titel: " + this.anforderungstitel);
		System.out.println("Beschreibung: " + this.beschreibung);

		// Felder leeren nach erstellung oder navigation
		this.anforderungstitel = null;
		this.beschreibung = null;

		Anforderung.info();
	}

	public static void info() {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Erfolg!", "Anforderung erfolgreich erfasst."));
	}

}