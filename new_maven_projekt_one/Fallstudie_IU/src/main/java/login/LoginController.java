package login;

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

@Named
@ViewScoped
public class LoginController implements Serializable {

	private String usernameInput; // Use a dedicated field for the username input
	private String passwordInput; // Use a dedicated field for the password input
	private Benutzer authenticatedBenutzer; // Renamed for clarity

	@Inject
	private EntityManager em;

	public LoginController() {
	}

	@PostConstruct
	public void benutzerErstellen() {
		// This block creates initial users if the database is empty.
		// It's good practice for initial setup.
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
			// Handle potential exceptions during initial user creation (e.g., if
			// transaction is already active)
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			System.err.println("Error creating initial users: " + e.getMessage());
		}
	}

	// Die benutzerListe wird typischerweise nicht komplett geladen,
	// sondern direkt in der Validierung abgefragt.
	// Wenn Sie sie dennoch im View-Scope halten wollen (z.B. für eine Übersicht):
	// List<Benutzer> benutzerListe;
	// benutzerListe = em.createQuery("SELECT b FROM Benutzer b",
	// Benutzer.class).getResultList();

	public void postValidateName(ComponentSystemEvent ev) throws AbortProcessingException {
		UIInput temp = (UIInput) ev.getComponent();
		this.usernameInput = (String) temp.getValue();
	}

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

	public String login() {
		if (this.authenticatedBenutzer != null) {
			String rolle = this.authenticatedBenutzer.getRolle();

			switch (rolle) {
			case "requirementsengineer":
				return "requirementsengineer";

			case "testfallersteller":
				return "testfallersteller";

			case "testmanager":
				return "testmanager";

			case "tester":
				return "tester";

			default:
				return "login";
			}
		} else {
			return "login";
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
