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

@Named
@ViewScoped
public class Testfallliste implements Serializable {

	@Inject
	private EntityManager em;

	private List<Testfall> liste;

	private String neuerTestfallTitel;
	private String neueTestfallBeschreibung;
	private int neueZuErfuellendeAnforderung = 0;

	public Testfallliste() {
	}

	@PostConstruct
	public void erstelleTestfallliste() {

		try {
			if (em.createQuery("SELECT COUNT(t) FROM Testfall t", Long.class).getSingleResult() == 0) {
				em.getTransaction().begin();
				em.persist(new Testfall(1, "Testfall Titel 1", "Beschreibung für Testfall 1"));
				em.persist(new Testfall(2, "Testfall Titel 2", "Beschreibung für Testfall 2"));
				em.persist(new Testfall(2, "Testfall Titel 3", "Beschreibung für Testfall 3"));
				em.persist(new Testfall(3, "Testfall Titel 4", "Beschreibung für Testfall 4"));
				em.persist(new Testfall(3, "Testfall Titel 5", "Beschreibung für Testfall 5"));
				em.getTransaction().commit();
			}
		} catch (Exception e) {
			// Fehlerbehandlung: Bei einer Ausnahme wird die Transaktion zurückgerollt,
			// um Dateninkonsistenzen zu vermeiden.
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			System.err.println("Fehler beim Erstellen der initialen Testfaelle:" + e.getMessage());
		}
		liste = em.createQuery("SELECT t FROM Testfall t", Testfall.class).getResultList();

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

	public int getNeueZuErfuellendeAnforderung() {
		return neueZuErfuellendeAnforderung;
	}

	public void setNeueZuErfuellendeAnforderung(int neueZuErfuellendeAnforderung) {
		this.neueZuErfuellendeAnforderung = neueZuErfuellendeAnforderung;
	}

	public void erstelleTestfall() {

		if (neueZuErfuellendeAnforderung != 0 && neuerTestfallTitel != null && !neuerTestfallTitel.trim().isEmpty()
				&& neueTestfallBeschreibung != null && !neueTestfallBeschreibung.trim().isEmpty()) {

			try {
				em.getTransaction().begin();
				Testfall neuerTestfall = new Testfall(neueZuErfuellendeAnforderung, neuerTestfallTitel,
						neueTestfallBeschreibung);
				em.persist(neuerTestfall);
				em.getTransaction().commit();

				liste = em.createQuery("SELECT t FROM Testfall t", Testfall.class).getResultList();

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Erfolg!", "Testfall erfolgreich erfasst."));

				this.neuerTestfallTitel = null;
				this.neueTestfallBeschreibung = null;
				this.neueZuErfuellendeAnforderung = 0;

			} catch (Exception e) {
				// Bei Fehlern wird die Transaktion zurückgerollt, um Dateninkonsistenzen zu
				// verhindern.
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Fehler!", "Unerwarteter Fehler beim Speichern des Testfalles: " + e.getMessage()));
			}

		}

	}

	public void speicherTestfallErgebnisse() {

		try {
			em.getTransaction().begin();
			if (liste != null) {
				for (Testfall t : liste) {
					em.merge(t);
				}
			}
			em.getTransaction().commit();

			liste = em.createQuery("SELECT t FROM Testfall t", Testfall.class).getResultList();

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Erfolg!", "Testfallergebnisse erfolgreich erfasst."));

		} catch (Exception e) {
			// Bei Fehlern wird die Transaktion zurückgerollt, um Dateninkonsistenzen zu
			// verhindern.
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler!",
					"Unerwarteter Fehler beim Speichern der Testfallergebnisse: " + e.getMessage()));
		}
	}

	public List<SelectItem> getTestfaelleAsSelectItems() {
		List<SelectItem> selectItems = new ArrayList<>();
		for (Testfall testfall : liste) {
			// Corrected: Use testfall.getID() as the value and testfall.getTestfallTitel()
			// as the label
			selectItems.add(new SelectItem(testfall.getID(), testfall.getTestfallTitel()));
		}
		return selectItems;
	}
}
