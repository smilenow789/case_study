package controller;

import java.io.Serializable;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.validator.ValidatorException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import model.Benutzer;
import util.UserSessionBean;

@Named
@ViewScoped
public class LoginController implements Serializable {

	private String usernameInput;
	private String passwordInput;
	private Benutzer authenticatedBenutzer;

	@Inject
	private EntityManager em;

	@Inject
	private UserSessionBean userSessionBean;

	public LoginController() {
	}

	// Erstellt Initialbenutzer, falls keine vorhanden sind
	// (wird beim Start der Anwendung ausgeführt)
	@PostConstruct
	public void benutzerErstellen() {
		try {
			if (em.createQuery("SELECT COUNT(b) FROM Benutzer b", Long.class).getSingleResult() == 0) {
				em.getTransaction().begin();
				em.persist(new Benutzer("Admin", "123", "requirementsengineer"));
				em.persist(new Benutzer("NameRequirementsengineer", "456", "requirementsengineer"));
				em.persist(new Benutzer("NameTestfallersteller", "456", "testfallersteller"));
				em.persist(new Benutzer("NameTestmanager", "456", "testmanager"));
				em.persist(new Benutzer("Nametester", "456", "tester"));
				em.persist(new Benutzer("NametesterEins", "456", "tester"));
				em.persist(new Benutzer("NametesterZwei", "456", "tester"));
				em.getTransaction().commit();
			}
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
	}

	// Erfasst den Benutzernamen nach der Eingabe
	// (login.xhtml)
	public void postValidateName(ComponentSystemEvent ev) throws AbortProcessingException {
		UIInput temp = (UIInput) ev.getComponent();
		this.usernameInput = (String) temp.getValue();
	}

	// Validiert die Login-Daten
	// (aufgerufen von login.xhtml)
	public void validateLogin(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String enteredPassword = (String) value;
		this.passwordInput = enteredPassword;

		try {
			TypedQuery<Benutzer> query = em.createQuery(
					"SELECT b FROM Benutzer b WHERE b.name = :name AND b.passwort = :passwort", Benutzer.class);
			query.setParameter("name", this.usernameInput);
			query.setParameter("passwort", this.passwordInput);

			this.authenticatedBenutzer = query.getSingleResult();

		} catch (NoResultException e) {
			throw new ValidatorException(new FacesMessage("Login falsch!"));
		}
	}

	// Führt den Login durch und leitet auf die entsprechende Seite weiter
	// (aufgerufen von login.xhtml)
	public String login() {
		if (this.authenticatedBenutzer != null) {
			// Speichern des authentifizierten Benutzers im UserSessionBean
			userSessionBean.setAuthenticatedUser(this.authenticatedBenutzer);

			String rolle = this.authenticatedBenutzer.getRolle();

			switch (rolle) {
			case "requirementsengineer":
				return "/requirementsengineer.xhtml?faces-redirect=true";
			case "testfallersteller":
				return "/testfallersteller.xhtml?faces-redirect=true";
			case "testmanager":
				return "/testmanager.xhtml?faces-redirect=true";
			case "tester":
				return "/tester.xhtml?faces-redirect=true";
			default:
				return "/login.xhtml?faces-redirect=true";
			}
		} else {
			return "/login.xhtml?faces-redirect=true";
		}
	}

	public String getUsernameInput() {
		return usernameInput;
	}

	public void setUsernameInput(String usernameInput) {
		this.usernameInput = usernameInput;
	}

	public String getPasswordInput() {
		return passwordInput;
	}

	public void setPasswordInput(String passwordInput) {
		this.passwordInput = passwordInput;
	}

	public Benutzer getAuthenticatedBenutzer() {
		return authenticatedBenutzer;
	}

	public void setAuthenticatedBenutzer(Benutzer authenticatedBenutzer) {
		this.authenticatedBenutzer = authenticatedBenutzer;
	}
}
