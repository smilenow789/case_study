package requirementsengineer;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import login.Benutzer;

@Entity
public class Testfall implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ID;
	private String testfallTitel;
	private String beschreibung;
	private String ergebnis = "noch nicht ausgeführt";

	// ADD THE NEW ManyToMany MAPPING
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "testfall_benutzer_assignment", // Name for the join table
			joinColumns = @JoinColumn(name = "testfall_id"), inverseJoinColumns = @JoinColumn(name = "benutzer_id"))
	private Set<Benutzer> zugewieseneBenutzer = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "anforderung_id", nullable = true) // Fremdschlüsselspalte in Testfall-Tabelle
	private Anforderung zuErfuellendeAnforderung; // Direkte Referenz zum Anforderung-Objekt

	@ManyToMany(mappedBy = "ausgewaehlteTestfaelle") // Testlauf owns the relationship
	private Set<Testlauf> zugehoerigeTestlaeufe = new HashSet<>(); // Initialize to prevent NullPointerException

	/*
	 * private Set<Testlauf> zugehoerigeTestlaeufe = new HashSet<>();: It's good
	 * practice to use Set for many-to-many relationships to avoid duplicate entries
	 * and to initialize the collection to prevent NullPointerExceptions.
	 */

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

	public Set<Benutzer> getZugewieseneBenutzer() {
		return zugewieseneBenutzer;
	}

	public void setZugewieseneBenutzer(Set<Benutzer> zugewieseneBenutzer) {
		this.zugewieseneBenutzer = zugewieseneBenutzer;
	}

	public Anforderung getZuErfuellendeAnforderung() {
		return zuErfuellendeAnforderung;
	}

	public void setZuErfuellendeAnforderung(Anforderung zuErfuellendeAnforderung) {
		this.zuErfuellendeAnforderung = zuErfuellendeAnforderung;
	}

	public Set<Testlauf> getZugehoerigeTestlaeufe() {
		return zugehoerigeTestlaeufe;
	}

	public void setZugehoerigeTestlaeufe(Set<Testlauf> zugehoerigeTestlaeufe) {
		this.zugehoerigeTestlaeufe = zugehoerigeTestlaeufe;
	}

	public Testfall() {
	}

	public Testfall(Anforderung zuErfuellendeAnforderung, String testfallTitel, String beschreibung) {
		this.zuErfuellendeAnforderung = zuErfuellendeAnforderung;
		this.testfallTitel = testfallTitel;
		this.beschreibung = beschreibung;
	}

	// Add helper methods to manage the ManyToMany relationship
	public void addZugewiesenerBenutzer(Benutzer benutzer) {
		this.zugewieseneBenutzer.add(benutzer);
		benutzer.getZugewieseneTestfaelle().add(this); // Assuming Benutzer has a mappedBy relationship
	}

}
