package requirementsengineer;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Anforderung implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ID;
	private String anforderungstitel;
	private String beschreibung;

	// Definition der One-to-Many-Beziehung zu Testfall
	// 'mappedBy' zeigt an, dass die 'testfall' Seite die Beziehung verwaltet
	// CascadeType.ALL sorgt dafür, dass abhängige Operationen (z.B. Löschen)
	// weitergegeben werden. Optional, je nach Geschäftslogik.
	@OneToMany(mappedBy = "zuErfuellendeAnforderung", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Testfall> zugehoerigeTestfaelle = new HashSet<>(); // Initialisierung des Sets

	public Anforderung() {
	}

	public Anforderung(String anforderungstitel, String beschreibung) {
		this.anforderungstitel = anforderungstitel;
		this.beschreibung = beschreibung;

	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
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

	public Set<Testfall> getZugehoerigeTestfaelle() {
		return zugehoerigeTestfaelle;
	}

	public void setZugehoerigeTestfaelle(Set<Testfall> zugehoerigeTestfaelle) {
		this.zugehoerigeTestfaelle = zugehoerigeTestfaelle;
	}

	// Hilfsmethoden zum Hinzufügen/Entfernen von Testfällen, um die Beziehung
	// beidseitig zu verwalten
	public void addTestfall(Testfall testfall) {
		this.zugehoerigeTestfaelle.add(testfall);
		testfall.setZuErfuellendeAnforderung(this);
	}

	public void removeTestfall(Testfall testfall) {
		this.zugehoerigeTestfaelle.remove(testfall);
		testfall.setZuErfuellendeAnforderung(null);
	}

}