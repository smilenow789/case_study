package requirementsengineer;

import java.io.Serializable;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Anforderung implements Serializable {

	private int id;
	private String anforderungstitel;
	private String beschreibung;

	private static int nextId = 11;

	@Inject
	private Anforderungsliste anforderungsliste;

	public Anforderung() {
	}

	public Anforderung(String anforderungstitel, String beschreibung) {
		this();
		this.anforderungstitel = anforderungstitel;
		this.beschreibung = beschreibung;

	}

	public Anforderung(int id, String anforderungstitel, String beschreibung) {
		this.id = id;
		this.anforderungstitel = anforderungstitel;
		this.beschreibung = beschreibung;

		if (id >= nextId) {
			nextId = id + 1;
		}

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

		if (this.anforderungstitel != null && !this.anforderungstitel.trim().isEmpty() && this.beschreibung != null
				&& !this.beschreibung.trim().isEmpty()) {
			Anforderung neueAnforderung = new Anforderung(nextId++, this.anforderungstitel, this.beschreibung);
			anforderungsliste.getListe().add(neueAnforderung);
			System.out.println("Anforderung erstellt:");
			System.out.println("ID: " + this.id);
			System.out.println("Titel: " + this.anforderungstitel);
			System.out.println("Beschreibung: " + this.beschreibung);

			// Felder leeren nach erstellung
			this.anforderungstitel = null;
			this.beschreibung = null;
			this.id = 0;

			Anforderung.info();

		} else {

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler!", "Bitte Titel und Beschreibung eingeben."));
		}

	}

	public static void info() {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Erfolg!", "Anforderung erfolgreich erfasst."));
	}

}