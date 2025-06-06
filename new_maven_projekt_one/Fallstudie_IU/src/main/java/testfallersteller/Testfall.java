package testfallersteller;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

public class Testfall {
	private int id;
	private String testfallTitel;
	private String beschreibung;
	private int gehoertZurAnforderung;
	private String ergebnis;

	public Testfall() {
	}

	public Testfall(String testfallTitel, String beschreibung, int gehoertZurAnforderung, String ergebnis) {
		this.id = 1;
		this.testfallTitel = testfallTitel;
		this.beschreibung = beschreibung;
		this.gehoertZurAnforderung = gehoertZurAnforderung;
		this.ergebnis = ergebnis;
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

	public int getGehoertZurAnforderung() {
		return gehoertZurAnforderung;
	}

	public void setGehoertZurAnforderung(int gehoertZurAnforderung) {
		this.gehoertZurAnforderung = gehoertZurAnforderung;
	}

	public String getErgebnis() {
		return ergebnis;
	}

	public void setErgebnis(String ergebnis) {
		this.ergebnis = ergebnis;
	}

	public void testfallErstellen() {
		System.out.println("Testfall erstellt:");
		System.out.println("Titel: " + this.testfallTitel);
		System.out.println("Beschreibung: " + this.beschreibung);

		// Felder leeren nach erstellung oder navigation
		this.testfallTitel = null;
		this.beschreibung = null;

		Testfall.info();
	}

	public static void info() {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Erfolg!", "Testfall erfolgreich erfasst."));
	}

}
