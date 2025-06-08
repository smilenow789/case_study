package requirementsengineer;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;

@Named
@ViewScoped
public class Testlaufliste implements Serializable {

	@Inject
	private EntityManager em;

	private String neuerTestlaufTitel;
	private String neueTestlaufBeschreibung;
	private List<Integer> neueAusgewaehlteTestfaelleIds;
	private String neuerZugehoerigerTester;

	public List<Integer> getNeueAusgewaehlteTestfaelleIds() {
		return neueAusgewaehlteTestfaelleIds;
	}

	public void setNeueAusgewaehlteTestfaelleIds(List<Integer> neueAusgewaehlteTestfaelleIds) {
		this.neueAusgewaehlteTestfaelleIds = neueAusgewaehlteTestfaelleIds;
	}

	public String getNeuerTestlaufTitel() {
		return neuerTestlaufTitel;
	}

	public void setNeuerTestlaufTitel(String neuerTestlaufTitel) {
		this.neuerTestlaufTitel = neuerTestlaufTitel;
	}

	public String getNeueTestlaufBeschreibung() {
		return neueTestlaufBeschreibung;
	}

	public void setNeueTestlaufBeschreibung(String neueTestlaufBeschreibung) {
		this.neueTestlaufBeschreibung = neueTestlaufBeschreibung;
	}

	public String getNeuerZugehoerigerTester() {
		return neuerZugehoerigerTester;
	}

	public void setNeuerZugehoerigerTester(String neuerZugehoerigerTester) {
		this.neuerZugehoerigerTester = neuerZugehoerigerTester;
	}

	public Testlaufliste() {
	}

	public void testlaufErstellen() {

		if (neuerTestlaufTitel != null && !neuerTestlaufTitel.trim().isEmpty() && neueTestlaufBeschreibung != null
				&& !neueTestlaufBeschreibung.trim().isEmpty() && neuerZugehoerigerTester != null
				&& !neuerZugehoerigerTester.trim().isEmpty() && neueAusgewaehlteTestfaelleIds != null
				&& !neueAusgewaehlteTestfaelleIds.isEmpty()) {

			try {
				em.getTransaction().begin();

				// Fetch Testfall entities based on selected IDs
				Set<Testfall> selectedTestfaelle = new HashSet<>();
				for (Integer testfallId : neueAusgewaehlteTestfaelleIds) {
					Testfall testfall = em.find(Testfall.class, testfallId);
					if (testfall != null) {
						selectedTestfaelle.add(testfall);
					}
				}

				Testlauf neuerTestlauf = new Testlauf(neuerTestlaufTitel, neueTestlaufBeschreibung, selectedTestfaelle,
						neuerZugehoerigerTester);
				em.persist(neuerTestlauf);
				em.getTransaction().commit();

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Erfolg!", "Testlauf erfolgreich erfasst."));

				this.neuerTestlaufTitel = null;
				this.neueTestlaufBeschreibung = null;
				this.neueAusgewaehlteTestfaelleIds = null;
				this.neuerZugehoerigerTester = null;

			} catch (Exception e) {
				// Bei Fehlern wird die Transaktion zurückgerollt, um Dateninkonsistenzen zu
				// verhindern.
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Fehler!", "Unerwarteter Fehler beim Speichern des Testlaufs: " + e.getMessage()));
			}

		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warnung!",
					"Bitte füllen Sie alle erforderlichen Felder aus und wählen Sie mindestens einen Testfall aus."));
		}
	}

}
