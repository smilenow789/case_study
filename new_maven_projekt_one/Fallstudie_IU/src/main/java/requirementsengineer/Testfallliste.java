package requirementsengineer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import login.UserSessionBean;

@Named
@ViewScoped
public class Testfallliste implements Serializable {

	@Inject
	private EntityManager em;

	@Inject
	private UserSessionBean userSessionBean;

	private List<Testfall> liste; // Alle Testfälle
	private List<Testfall> zugewieseneTestfaelleListe; // Testfälle, die dem eingeloggten Tester zugewiesen sind

	private String neuerTestfallTitel;
	private String neueTestfallBeschreibung;
	private Integer neueZuErfuellendeAnforderungId;

	public Testfallliste() {
	}

	@PostConstruct
	public void erstelleTestfallliste() {
		try {
			em.getTransaction().begin();

			// Nur initiale Daten erstellen, wenn keine Testfälle oder Testläufe vorhanden
			// sind.
			// Dies verhindert das doppelte Erstellen von Daten bei jedem Deployment oder
			// Neustart.
			if (em.createQuery("SELECT COUNT(t) FROM Testfall t", Long.class).getSingleResult() == 0
					|| em.createQuery("SELECT COUNT(tl) FROM Testlauf tl", Long.class).getSingleResult() == 0) {

				// Stellen Sie sicher, dass Anforderung-Objekte existieren oder neu erstellt
				// werden
				Anforderung anforderung1 = findOrCreateAnforderung("EinsAnforderungsTitel",
						"BeschreibungAnforderungEins");
				Anforderung anforderung2 = findOrCreateAnforderung("ZweisAnforderungsTitel",
						"BeschreibungAnforderungZwei");
				Anforderung anforderung3 = findOrCreateAnforderung("DreiAnforderungsTitel",
						"BeschreibungAnforderungDrei");

				// Testfall-Objekte erstellen und persistieren
				Testfall testfall1 = new Testfall(anforderung1, "Testfall Titel 1", "Beschreibung für Testfall 1");
				Testfall testfall2 = new Testfall(anforderung2, "Testfall Titel 2", "Beschreibung für Testfall 2");
				Testfall testfall3 = new Testfall(anforderung2, "Testfall Titel 3", "Beschreibung für Testfall 3");
				Testfall testfall4 = new Testfall(anforderung3, "Testfall Titel 4", "Beschreibung für Testfall 4");
				Testfall testfall5 = new Testfall(anforderung3, "Testfall Titel 5", "Beschreibung für Testfall 5");
				Testfall testfall6 = new Testfall(anforderung1, "Testfall-Titel", "Beschreibung für Testfall 6"); // Aus
																													// der
																													// Aufgabenstellung

				em.persist(testfall1);
				em.persist(testfall2);
				em.persist(testfall3);
				em.persist(testfall4);
				em.persist(testfall5);
				em.persist(testfall6);

				// Testlauf-Objekte erstellen und Testfälle Testern zuweisen
				// WICHTIG: Bidirektionale Beziehung synchronisieren!

				// Beispiel: Zuweisung von Testfall 1 und 2 an "Nametester"
				Set<Testfall> zugewieseneTestfaelleFürNametester = new HashSet<>();
				zugewieseneTestfaelleFürNametester.add(testfall1);
				zugewieseneTestfaelleFürNametester.add(testfall2);

				Testlauf testlaufNametester = new Testlauf("Testlauf für Nametester",
						"Beschreibung des Testlaufes für Nametester", zugewieseneTestfaelleFürNametester, "Nametester");
				em.persist(testlaufNametester);
				// Bidirektionale Beziehung synchronisieren
				testfall1.getZugehoerigeTestlaeufe().add(testlaufNametester);
				testfall2.getZugehoerigeTestlaeufe().add(testlaufNametester);

				// Beispiel: Zuweisung von Testfall 3 und 4 an "NametesterEins"
				Set<Testfall> zugewieseneTestfaelleFürNametesterEins = new HashSet<>();
				zugewieseneTestfaelleFürNametesterEins.add(testfall3);
				zugewieseneTestfaelleFürNametesterEins.add(testfall4);

				Testlauf testlaufNametesterEins = new Testlauf("Testlauf für NametesterEins",
						"Beschreibung des Testlaufes für Nametester Eins", zugewieseneTestfaelleFürNametesterEins,
						"NametesterEins");
				em.persist(testlaufNametesterEins);
				// Bidirektionale Beziehung synchronisieren
				testfall3.getZugehoerigeTestlaeufe().add(testlaufNametesterEins);
				testfall4.getZugehoerigeTestlaeufe().add(testlaufNametesterEins);

				// Beispiel: Zuweisung von Testfall 5 und 6 an "NametesterZwei"
				Set<Testfall> zugewieseneTestfaelleFürNametesterZwei = new HashSet<>();
				zugewieseneTestfaelleFürNametesterZwei.add(testfall5);
				zugewieseneTestfaelleFürNametesterZwei.add(testfall6);

				Testlauf testlaufNametesterZwei = new Testlauf("Testlauf für NametesterZwei",
						"Beschreibung des Testlaufes für Nametester Zwei", zugewieseneTestfaelleFürNametesterZwei,
						"NametesterZwei");
				em.persist(testlaufNametesterZwei);
				// Bidirektionale Beziehung synchronisieren
				testfall5.getZugehoerigeTestlaeufe().add(testlaufNametesterZwei);
				testfall6.getZugehoerigeTestlaeufe().add(testlaufNametesterZwei);

			}
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			System.err.println("Fehler beim Erstellen der initialen Testfaelle und Testlaeufe: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler!",
					"Unerwarteter Fehler beim Initialisieren der Testfälle und Testläufe: " + e.getMessage()));
		}
		// Alle Testfälle laden (für den Fall, dass diese Liste auch benötigt wird)
		liste = em.createQuery("SELECT t FROM Testfall t", Testfall.class).getResultList();
		// Die zugewiesenen Testfälle für den aktuell eingeloggten Benutzer laden
		ladenZugewieseneTestfaelle();
	}

	// Hilfsmethode, um eine Anforderung zu finden oder neu zu erstellen
	private Anforderung findOrCreateAnforderung(String titel, String beschreibung) {
		try {
			return em.createQuery("SELECT a FROM Anforderung a WHERE a.anforderungstitel = :titel", Anforderung.class)
					.setParameter("titel", titel).getSingleResult();
		} catch (NoResultException e) {
			Anforderung newAnf = new Anforderung(titel, beschreibung);
			em.persist(newAnf); // Persistieren der neuen Anforderung
			return newAnf;
		}
	}

	public List<Testfall> getListe() {
		return liste;
	}

	public void setListe(List<Testfall> liste) {
		this.liste = liste;
	}

	public String getNeuerTestfallTitel() {
		return neuerTestfallTitel;
	}

	public void setNeuerTestfallTitel(String neuerTestfallTitel) {
		this.neuerTestfallTitel = neuerTestfallTitel;
	}

	public String getNeueTestfallBeschreibung() {
		return neueTestfallBeschreibung;
	}

	public void setNeueTestfallBeschreibung(String neueTestfallBeschreibung) {
		this.neueTestfallBeschreibung = neueTestfallBeschreibung;
	}

	public Integer getNeueZuErfuellendeAnforderungId() {
		return neueZuErfuellendeAnforderungId;
	}

	public void setNeueZuErfuellendeAnforderungId(Integer neueZuErfuellendeAnforderungId) {
		this.neueZuErfuellendeAnforderungId = neueZuErfuellendeAnforderungId;
	}

	public void erstelleTestfall() {
		if (neuerTestfallTitel != null && !neuerTestfallTitel.trim().isEmpty() && neueTestfallBeschreibung != null
				&& !neueTestfallBeschreibung.trim().isEmpty()) {

			try {
				em.getTransaction().begin();

				Anforderung zugehoerigeAnforderung = null;
				// Nur versuchen, die Anforderung zu laden, wenn eine ID ausgewählt wurde
				if (neueZuErfuellendeAnforderungId != null) {
					zugehoerigeAnforderung = em.find(Anforderung.class, neueZuErfuellendeAnforderungId);
				}

				// Erstelle den neuen Testfall mit dem geladenen Anforderung-Objekt (oder null)
				Testfall neuerTestfall = new Testfall(zugehoerigeAnforderung, neuerTestfallTitel,
						neueTestfallBeschreibung);
				em.persist(neuerTestfall);
				em.getTransaction().commit();

				// Aktualisiere die Liste nach dem Speichern
				liste = em.createQuery("SELECT t FROM Testfall t", Testfall.class).getResultList();
				ladenZugewieseneTestfaelle(); // Aktualisiere die zugewiesene Liste

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Erfolg!", "Testfall erfolgreich erfasst."));

				// Felder zurücksetzen
				this.neuerTestfallTitel = null;
				this.neueTestfallBeschreibung = null;
				this.neueZuErfuellendeAnforderungId = null; // Setze ID auf null zurück

			} catch (Exception e) {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Fehler!", "Unerwarteter Fehler beim Speichern des Testfalles: " + e.getMessage()));
			}

		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Fehler!",
					"Bitte füllen Sie alle erforderlichen Felder aus."));
		}
	}

	public void speicherTestfallErgebnisse() {
		try {
			em.getTransaction().begin();
			if (zugewieseneTestfaelleListe != null) { // Speichere nur die angezeigten Testfälle
				for (Testfall t : zugewieseneTestfaelleListe) {
					em.merge(t); // Speichert Änderungen am Ergebnis-Feld
				}
			}
			em.getTransaction().commit();

			ladenZugewieseneTestfaelle(); // Lade die Liste neu, um aktualisierte Ergebnisse anzuzeigen

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Erfolg!", "Testfallergebnisse erfolgreich erfasst."));

		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler!",
					"Unerwarteter Fehler beim Speichern der Testfallergebnisse: " + e.getMessage()));
		}
	}

	public List<SelectItem> getTestfaelleAsSelectItems() {
		List<SelectItem> selectItems = new ArrayList<>();
		try {
			// Verwenden Sie 'liste' (alle Testfälle) für die Dropdown-Auswahl,
			// da hier alle Testfälle zur Auswahl stehen sollten.
			for (Testfall testfall : liste) {
				selectItems.add(new SelectItem(testfall.getID(), testfall.getTestfallTitel()));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler!",
					"Unerwarteter Fehler beim Laden der Testfallliste: " + e.getMessage()));
		}
		return selectItems;
	}

	// Lädt die Testfälle, die dem aktuell eingeloggten Tester zugewiesen sind.
	// Diese Methode wird vom @PostConstruct und nach dem Speichern aufgerufen.
	public void ladenZugewieseneTestfaelle() {
		String currentTesterName = userSessionBean.getAuthenticatedUsername();
		String currentTesterRole = userSessionBean.getAuthenticatedUserRole();

		if (currentTesterName != null && "tester".equals(currentTesterRole)) {
			try {
				// Query, um Testfälle zu finden, die mit Testläufen verknüpft sind,
				// welche dem aktuellen Tester zugewiesen sind.
				// Durch die inverse Beziehung in Testfall (zugehoerigeTestlaeufe) funktioniert
				// der JOIN jetzt korrekt.
				TypedQuery<Testfall> query = em.createQuery(
						"SELECT DISTINCT tf FROM Testfall tf JOIN tf.zugehoerigeTestlaeufe tl WHERE tl.zugehoerigerTester = :testerName",
						Testfall.class);
				query.setParameter("testerName", currentTesterName);
				zugewieseneTestfaelleListe = query.getResultList();
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Fehler!", "Fehler beim Laden Ihrer zugewiesenen Testfälle: " + e.getMessage()));
				zugewieseneTestfaelleListe = new ArrayList<>(); // Leere Liste bei Fehler
			}
		} else {
			// Wenn kein Tester eingeloggt ist oder die Rolle nicht 'tester' ist, keine
			// Testfälle anzeigen
			zugewieseneTestfaelleListe = new ArrayList<>();
		}
	}

	public List<Testfall> getZugewieseneTestfaelleListe() {
		// Diese Methode sollte einfach die bereits geladene Liste zurückgeben.
		// Das Laden wird im @PostConstruct und nach dem Speichern von Ergebnissen
		// ausgelöst.
		if (zugewieseneTestfaelleListe == null) {
			ladenZugewieseneTestfaelle(); // Lade die Liste, falls sie noch nicht initialisiert wurde
		}
		return zugewieseneTestfaelleListe;
	}

	public void setZugewieseneTestfaelleListe(List<Testfall> zugewieseneTestfaelleListe) {
		this.zugewieseneTestfaelleListe = zugewieseneTestfaelleListe;
	}
}
