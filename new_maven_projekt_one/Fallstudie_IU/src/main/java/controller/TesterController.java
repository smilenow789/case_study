package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import model.Testfall;
import service.TestfallService;
import util.UserSessionBean;

@Named
@ViewScoped
public class TesterController implements Serializable {

	@Inject
	private TestfallService testfallService;

	@Inject
	private UserSessionBean userSessionBean;

	private List<Testfall> zugewieseneTestfaelleListe;

	@PostConstruct
	public void init() {
		loadAssignedTestfaelle();
	}

	public void loadAssignedTestfaelle() {
		String currentTesterName = userSessionBean.getAuthenticatedUsername();
		String currentTesterRole = userSessionBean.getAuthenticatedUserRole();
		this.zugewieseneTestfaelleListe = testfallService.getAssignedTestfaelleForTester(currentTesterName,
				currentTesterRole);
		if (this.zugewieseneTestfaelleListe == null) {
			this.zugewieseneTestfaelleListe = new ArrayList<>();
		}
	}

	public List<Testfall> getZugewieseneTestfaelleListe() {
		// Important: loadAssignedTestfaelle should be called from PostConstruct or
		// action methods
		// not directly from a getter for a list that is modified by a P:dataTable
		// unless you specifically need to reload on every access (which can be
		// inefficient).
		// Let's rely on PostConstruct and action methods to update it.
		return zugewieseneTestfaelleListe;
	}

	public void setZugewieseneTestfaelleListe(List<Testfall> zugewieseneTestfaelleListe) {
		this.zugewieseneTestfaelleListe = zugewieseneTestfaelleListe;
	}

	public void speicherTestfallErgebnisse() {
		try {
			testfallService.saveTestfallResults(zugewieseneTestfaelleListe);
			loadAssignedTestfaelle();

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Erfolg!", "Testfallergebnisse erfolgreich erfasst."));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler!",
					"Unerwarteter Fehler beim Speichern der Testfallergebnisse: " + e.getMessage()));
		}
	}
}