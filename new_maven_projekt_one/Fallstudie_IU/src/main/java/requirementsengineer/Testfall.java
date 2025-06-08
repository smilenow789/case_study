package requirementsengineer;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Testfall implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ID;
	private String testfallTitel;
	private String beschreibung;
	private String ergebnis = "noch nicht ausgeführt";
	private int zuErfuellendeAnforderung = 0;

//	@Inject // Inject the ApplicationScoped Testfallliste
//	private Testfallliste testfallliste;

	public Testfall() {
	}

	public Testfall(int neueZuErfuellendeAnforderung, String testfallTitel, String beschreibung) {
		this.zuErfuellendeAnforderung = neueZuErfuellendeAnforderung;
		this.testfallTitel = testfallTitel;
		this.beschreibung = beschreibung;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
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

	public int getZuErfuellendeAnforderung() {
		return zuErfuellendeAnforderung;
	}

	public void setZuErfuellendeAnforderung(int zuErfuellendeAnforderung) {
		this.zuErfuellendeAnforderung = zuErfuellendeAnforderung;
	}

}
