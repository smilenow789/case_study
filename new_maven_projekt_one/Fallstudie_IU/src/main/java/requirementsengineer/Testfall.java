package requirementsengineer;

import java.io.Serializable;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class Testfall implements Serializable {
	private int id = 1;
	private String testfallTitel;
	private String beschreibung;
	private String ergebnis = "noch nicht ausgeführt";
	private int anforderungsId = 0;

	public Testfall() {
	}

	public Testfall(String testfallTitel, String beschreibung, int id) {
		this.id = id;
		this.testfallTitel = testfallTitel;
		this.beschreibung = beschreibung;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTestfallTitel() {
		return testfallTitel;
	}

	public void setTestfallTitel(String testfallTitel) {
		this.testfallTitel = testfallTitel;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public String getErgebnis() {
		return ergebnis;
	}

	public void setErgebnis(String ergebnis) {
		this.ergebnis = ergebnis;
	}

	public int getAnforderungsId() {
		return anforderungsId;
	}

	public void setAnforderungsId(int anforderungsId) {
		this.anforderungsId = anforderungsId;
	}

	public void testfallErstellen() {
		System.out.println("Testfall erstellt:");
		System.out.println("Ausgewählte Anforderungs-ID: " + this.anforderungsId);
		System.out.println("Testfall-Titel: " + this.testfallTitel);
		System.out.println("Beschreibung: " + this.beschreibung);
		System.out.println("Ergebnis: " + this.ergebnis);

		// Felder leeren nach erstellung oder navigation
		this.testfallTitel = null;
		this.beschreibung = null;
		this.anforderungsId = 0;

		Testfall.info();
	}

	public static void info() {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Erfolg!", "Testfall erfolgreich erfasst."));
	}

}
