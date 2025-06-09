package requirementsengineer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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

	private List<Testfall> liste;
	private List<Testfall> zugewieseneTestfaelleListe;
	private String neuerTestfallTitel;
	private String neueTestfallBeschreibung;
	private Integer neueZuErfuellendeAnforderungId;

	public Testfallliste() {
	}

	@PostConstruct
	public void erstelleTestfallliste() {
		try {
			// Nur initiale Daten erstellen, wenn keine Testfälle vorhanden sind
			if (em.createQuery("SELECT COUNT(t) FROM Testfall t", Long.class).getSingleResult() == 0) {
				em.getTransaction().begin();
				// Stellen Sie sicher, dass Anforderung-Objekte existieren oder neu erstellt
				// werden
				// Query for existing Anforderung entities by title
				Anforderung anforderung1 = findOrCreateAnforderung("EinsAnforderungsTitel",
						"BeschreibungAnforderungEins");
				Anforderung anforderung2 = findOrCreateAnforderung("ZweisAnforderungsTitel",
						"BeschreibungAnforderungZwei");
				Anforderung anforderung3 = findOrCreateAnforderung("DreiAnforderungsTitel",
						"BeschreibungAnforderungDrei");
				em.persist(new Testfall(anforderung1, "Testfall Titel 1", "Beschreibung für Testfall 1"));
				em.persist(new Testfall(anforderung2, "Testfall Titel 2", "Beschreibung für Testfall 2"));
				em.persist(new Testfall(anforderung2, "Testfall Titel 3", "Beschreibung für Testfall 3"));
				em.persist(new Testfall(anforderung3, "Testfall Titel 4", "Beschreibung für Testfall 4"));
				em.persist(new Testfall(anforderung3, "Testfall Titel 5", "Beschreibung für Testfall 5"));
				em.getTransaction().commit();
			}
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler!",
					"Unerwarteter Fehler beim Initialisieren der Testfälle: " + e.getMessage()));
		}
		liste = em.createQuery("SELECT t FROM Testfall t", Testfall.class).getResultList();
		ladenZugewieseneTestfaelle();
	}

	// Hilfsmethode, um eine Anforderung zu finden oder neu zu erstellen
	private Anforderung findOrCreateAnforderung(String titel, String beschreibung) {
		try {
			return em.createQuery("SELECT a FROM Anforderung a WHERE a.anforderungstitel = :titel", Anforderung.class)
					.setParameter("titel", titel).getSingleResult();
		} catch (NoResultException e) {
			Anforderung newAnf = new Anforderung(titel, beschreibung);
			em.persist(newAnf); // Persist the new Anforderung
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
				ladenZugewieseneTestfaelle();

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Erfolg!", "Testfall erfolgreich erfasst."));

				// Felder zurücksetzen
				this.neuerTestfallTitel = null;
				this.neueTestfallBeschreibung = null;
				this.neueZuErfuellendeAnforderungId = null;

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
					em.merge(t);
				}
			}
			em.getTransaction().commit();
			ladenZugewieseneTestfaelle();
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
			for (Testfall testfall : liste) {
				// Hier wird testfall.getID() als Wert und testfall.getTestfallTitel() als Label
				// verwendet
				selectItems.add(new SelectItem(testfall.getID(), testfall.getTestfallTitel()));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler!",
					"Unerwarteter Fehler beim Laden der Testfallliste: " + e.getMessage()));
		} finally {
			return selectItems;
		}
	}

	// Lädt die Testfälle, die dem aktuell eingeloggten Tester zugewiesen sind.
	// Diese Methode wird vom @PostConstruct und nach dem Speichern aufgerufen.
	public void ladenZugewieseneTestfaelle() {
		System.out.println("Start Methode ladenZugewoeseneTestfaelle");
		String currentTesterName = userSessionBean.getAuthenticatedUsername();
		System.out.println("CurrentTesterName: " + currentTesterName);
		if (currentTesterName != null && userSessionBean.getAuthenticatedUserRole().equals("tester")) {
			try {
				// Query, um Testfälle zu finden, die mit Testläufen verknüpft sind,
				// welche dem aktuellen Tester zugewiesen sind.
				System.out.println("Start SQL");
				TypedQuery<Testfall> query = em.createQuery(
						"SELECT DISTINCT tf FROM Testfall tf JOIN tf.zugehoerigeTestlaeufe tl WHERE tl.zugehoerigerTester = :testerName",
						Testfall.class);
				query.setParameter("testerName", currentTesterName);
				zugewieseneTestfaelleListe = query.getResultList();
				System.out.println(zugewieseneTestfaelleListe);
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
		if (userSessionBean != null && userSessionBean.getAuthenticatedUsername() != null) {
			ladenZugewieseneTestfaelle();
		} else if (userSessionBean == null || userSessionBean.getAuthenticatedUsername() == null) {
			zugewieseneTestfaelleListe = new ArrayList<>();
		}
		return zugewieseneTestfaelleListe;
	}

	public void setZugewieseneTestfaelleListe(List<Testfall> zugewieseneTestfaelleListe) {
		this.zugewieseneTestfaelleListe = zugewieseneTestfaelleListe;
	}

}