package requirementsengineer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct; // Wichtig: Import für @PostConstruct
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;

@Named
@ApplicationScoped
public class Testfallliste implements Serializable {
	private List<Testfall> liste;

	public Testfallliste() {
		// Der Konstruktor wird vom CDI-Container aufgerufen.
		// Die eigentliche Initialisierung der Liste mit Daten findet am besten in
		// @PostConstruct statt.
		System.out.println("Testfallliste() Konstruktor aufgerufen (erste Initialisierungsphase).");
		liste = new ArrayList<>(); // Liste hier initialisieren, falls keine @PostConstruct Methode verwendet wird
	}

	@PostConstruct // Diese Methode wird nach der Bean-Konstruktion und Injektion aufgerufen
	public void init() {
		System.out.println("@PostConstruct in Testfallliste aufgerufen. Fülle Liste mit Testfällen.");
		liste.add(new Testfall("Testfall Titel 1", "Beschreibung für Testfall 1", 2001));
		liste.add(new Testfall("Testfall Titel 2", "Beschreibung für Testfall 2", 2002));
		liste.add(new Testfall("Testfall Titel 3", "Beschreibung für Testfall 3", 2003));
		liste.add(new Testfall("Testfall Titel 4", "Beschreibung für Testfall 4", 2004));
		liste.add(new Testfall("Testfall Titel 5", "Beschreibung für Testfall 5", 2005));
		System.out.println("Testfallliste initialisiert mit " + liste.size() + " Testfällen.");
	}

	public List<Testfall> getListe() {
		// System.out.println("getListe() aufgerufen. Aktuelle Größe: " + liste.size());
		// // Kann sehr gesprächig sein
		return liste;
	}

	public void setListe(List<Testfall> liste) {
		this.liste = liste;
	}

	public void speichern() {
		System.out.println("--- Zustand der Testfallliste VOR dem Speichern (im speichern() Methode) ---");
		for (Testfall tf : liste) {
			System.out.println("  ID: " + tf.getId() + ", Titel: " + tf.getTestfallTitel() + ", Aktuelles Ergebnis: '"
					+ tf.getErgebnis() + "'");
		}

		System.out.println("--- Zustand der Testfallliste NACH dem Speichern (im speichern() Methode) ---");
		for (Testfall tf : liste) {
			System.out.println("  ID: " + tf.getId() + ", Titel: " + tf.getTestfallTitel()
					+ ", Gespeichertes Ergebnis: '" + tf.getErgebnis() + "'");
		}

		info();
	}

	public static void info() {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Erfolg!", "Testergebnisse erfolgreich gespeichert."));
	}

	public List<SelectItem> getTestfaelleAsSelectItems() {
		List<SelectItem> selectItems = new ArrayList<>();
		for (Testfall testfall : liste) {
			selectItems.add(new SelectItem(testfall.getId(), testfall.getTestfallTitel()));
		}
		return selectItems;
	}
}
