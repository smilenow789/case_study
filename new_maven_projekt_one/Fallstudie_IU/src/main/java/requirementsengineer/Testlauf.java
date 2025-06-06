package requirementsengineer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.inject.Inject;

@Named
@RequestScoped
public class Testlauf implements Serializable {

	private int id;
	private String testlaufTitel;
	private String beschreibung;
	private int zugehoerigerTestfall;
	private int zugehoerigerTester;
	private List<Integer> ausgewaehlteTestfaelle;

	@Inject
	private Testfallliste testfallliste;

	public Testlauf() {
		ausgewaehlteTestfaelle = new ArrayList<>();
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

	public List<Integer> getAusgewaehlteTestfaelle() {
		return ausgewaehlteTestfaelle;
	}

	public void setAusgewaehlteTestfaelle(List<Integer> ausgewaehlteTestfaelle) {
		this.ausgewaehlteTestfaelle = ausgewaehlteTestfaelle;
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
		System.out.println("Neuer Testlauf erstellt:");
		System.out.println("Titel: " + this.testlaufTitel);
		System.out.println("Beschreibung: " + this.beschreibung);
		System.out.println("Ausgewählte Testfall-IDs: " + this.ausgewaehlteTestfaelle);

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
