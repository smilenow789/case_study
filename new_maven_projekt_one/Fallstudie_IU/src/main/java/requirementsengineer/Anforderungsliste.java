package requirementsengineer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;

@Named
@ViewScoped
public class Anforderungsliste implements Serializable {

	@Inject
	private EntityManager em;

	private List<Anforderung> liste;

	private String neueAnforderungTitel;
	private String neueAnforderungBeschreibung;

	public Anforderungsliste() {
	}

	@PostConstruct
	public void anforderungenErstellen() {
		// Diese Methode wird nach der Konstruktion des Beans und der Injektion von
		// Abhängigkeiten aufgerufen.
		// Sie initialisiert die Liste der Anforderungen und fügt Beispieldaten hinzu,
		// falls die Datenbank leer ist.

		try {
			if (em.createQuery("SELECT COUNT(a) FROM Anforderung a", Long.class).getSingleResult() == 0) {
				em.getTransaction().begin();
				em.persist(new Anforderung("EinsAnforderungsTitel", "<p>BeschreibungAnforderungEins<p>"));
				em.persist(new Anforderung("ZweisAnforderungsTitel", "<p>BeschreibungAnforderungZwei<p>"));
				em.persist(new Anforderung("DreiAnforderungsTitel", "<p>BeschreibungAnforderungDrei<p>"));
				em.persist(new Anforderung("VierAnforderungsTitel", "<p>BeschreibungAnforderungVier<p>"));
				em.persist(new Anforderung("FünfAnforderungsTitel", "<p>BeschreibungAnforderungFünf<p>"));
				em.persist(new Anforderung("SechsAnforderungsTitel", "<p>BeschreibungAnforderungSechs<p>"));
				em.getTransaction().commit();
				;
			}
		} catch (Exception e) {
			// Fehlerbehandlung: Bei einer Ausnahme wird die Transaktion zurückgerollt,
			// um Dateninkonsistenzen zu vermeiden.
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			System.err.println("Fehler beim Erstellen der initialen Anforderungen:" + e.getMessage());
		}

		liste = em.createQuery("SELECT a FROM Anforderung a", Anforderung.class).getResultList();

	}

	public List<Anforderung> getListe() {
		return liste;
	}

	public void setListe(List<Anforderung> liste) {
		this.liste = liste;
	}

	public String getNeueAnforderungTitel() {
		return neueAnforderungTitel;
	}

	public void setNeueAnforderungTitel(String neueAnforderungTitel) {
		this.neueAnforderungTitel = neueAnforderungTitel;
	}

	public String getNeueAnforderungBeschreibung() {
		return neueAnforderungBeschreibung;
	}

	public void setNeueAnforderungBeschreibung(String neueAnforderungBeschreibung) {
		this.neueAnforderungBeschreibung = neueAnforderungBeschreibung;
	}

	public void erstelleAnforderung() {
		if (neueAnforderungTitel != null && !neueAnforderungTitel.trim().isEmpty()
				&& neueAnforderungBeschreibung != null && !neueAnforderungBeschreibung.trim().isEmpty()) {

			try {
				em.getTransaction().begin();
				Anforderung neueAnforderung = new Anforderung(neueAnforderungTitel, neueAnforderungBeschreibung);
				em.persist(neueAnforderung);
				em.getTransaction().commit();

				liste = em.createQuery("SELECT a FROM Anforderung a", Anforderung.class).getResultList();

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Erfolg!", "Anforderung erfolgreich erfasst."));

				this.neueAnforderungTitel = null;
				this.neueAnforderungBeschreibung = null;

			} catch (Exception e) {
				// Bei Fehlern wird die Transaktion zurückgerollt, um Dateninkonsistenzen zu
				// verhindern.
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Fehler!", "Unerwarteter Fehler beim Speichern der Anforderung: " + e.getMessage()));
			}

		}
	}

	/*
	 * Erstellt eine Liste von SelectItem-Objekten, die für UI-Komponenten wie
	 * p:selectOneRadio oder p:selectOneMenu verwendet werden können.
	 */
	public List<SelectItem> getAnforderungenAsSelectItems() {
		List<SelectItem> selectItems = new ArrayList<>();
		if (liste != null) {
			for (Anforderung anforderung : liste) {
				// Nützt anforderung.getID() als value und
				// anforderung.getAnforderungstitel() als label
				selectItems.add(new SelectItem(anforderung.getID(), anforderung.getAnforderungstitel()));
			}
		}
		return selectItems;
	}

}