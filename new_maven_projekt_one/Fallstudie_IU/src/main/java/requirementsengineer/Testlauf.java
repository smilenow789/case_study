package requirementsengineer;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;

@Entity
public class Testlauf implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ID;
	private String testlaufTitel;
	private String beschreibung;
	private String zugehoerigerTester;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }) // Cascade operations for Testfall
	@JoinTable(name = "testlauf_testfall", // Name of the join table
			joinColumns = @JoinColumn(name = "testlauf_id"), // Column in join table referring to Testlauf ID
			inverseJoinColumns = @JoinColumn(name = "testfall_id") // Column in join table referring to Testfall ID
	)
	private Set<Testfall> ausgewaehlteTestfaelle = new HashSet<>(); // Initialize set

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

	public Set<Testfall> getAusgewaehlteTestfaelle() {
		return ausgewaehlteTestfaelle;
	}

	public void setAusgewaehlteTestfaelle(Set<Testfall> ausgewaehlteTestfaelle) {
		this.ausgewaehlteTestfaelle = ausgewaehlteTestfaelle;
	}

	public String getZugehoerigerTester() {
		return zugehoerigerTester;
	}

	public void setZugehoerigerTester(String zugehoerigerTester) {
		this.zugehoerigerTester = zugehoerigerTester;
	}

	public Testlauf() {
	}

	public Testlauf(String testlaufTitel, String beschreibung, Set<Testfall> ausgewaehlteTestfaelle,
			String zugehoerigerTester) {
		this.testlaufTitel = testlaufTitel;
		this.beschreibung = beschreibung;
		this.ausgewaehlteTestfaelle = ausgewaehlteTestfaelle;
		this.zugehoerigerTester = zugehoerigerTester;
	}

}
