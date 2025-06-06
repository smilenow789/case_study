package testmanager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class Testlauf implements Serializable {

	private int id;
	private String testlaufTitel;
	private String beschreibung;
	private int zugehoerigerTestfall;
	private int zugehoerigerTester;
	private List<String> ausgewaehlteTestfaelle;

	public List<String> getAusgewaehlteTestfaelle() {
		return ausgewaehlteTestfaelle;
	}

	public void setAusgewaehlteTestfaelle(List<String> ausgewaehlteTestfaelle) {
		this.ausgewaehlteTestfaelle = ausgewaehlteTestfaelle;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTestlaufTitel() {
		return testlaufTitel;
	}

	public void setTestlaufTitel(String testlaufTitel) {
		this.testlaufTitel = testlaufTitel;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public int getZugehoerigerTestfall() {
		return zugehoerigerTestfall;
	}

	public void setZugehoerigerTestfall(int zugehoerigerTestfall) {
		this.zugehoerigerTestfall = zugehoerigerTestfall;
	}

	public int getZugehoerigerTester() {
		return zugehoerigerTester;
	}

	public void setZugehoerigerTester(int zugehoerigerTester) {
		this.zugehoerigerTester = zugehoerigerTester;
	}

	public Testlauf() {
		this.ausgewaehlteTestfaelle = new ArrayList<>();
	}

	public Testlauf(int id, String testlaufTitel, String beschreibung, int zugehoerigerTestfall,
			int zugehoerigerTester) {
		this.id = id;
		this.testlaufTitel = testlaufTitel;
		this.beschreibung = beschreibung;
		this.zugehoerigerTestfall = zugehoerigerTestfall;
		this.zugehoerigerTester = zugehoerigerTester;
		this.ausgewaehlteTestfaelle = new ArrayList<>();
	}

	public void testlaufErstellen() {
		System.out.println("Testlauf erstellt:");
		System.out.println("Titel: " + this.testlaufTitel);
		System.out.println("Beschreibung: " + this.beschreibung);

		if (ausgewaehlteTestfaelle != null && !ausgewaehlteTestfaelle.isEmpty()) {
			System.out.println("Ausgewählte Testfälle:");
			for (String title : ausgewaehlteTestfaelle) {
				System.out.println("- " + title);
			}
		} else {
			System.out.println("Keine Testfälle ausgewählt.");
		}

		// Felder leeren nach erstellung oder navigation
		this.testlaufTitel = null;
		this.beschreibung = null;
		this.ausgewaehlteTestfaelle.clear();

		Testlauf.info();
	}

	public static void info() {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Erfolg!", "Testlauf erfolgreich erfasst."));
	}

}
