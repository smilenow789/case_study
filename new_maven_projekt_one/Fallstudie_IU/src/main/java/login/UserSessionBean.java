package login;

import java.io.Serializable;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@SessionScoped
public class UserSessionBean implements Serializable {

	private Benutzer authenticatedUser;

	public UserSessionBean() {
	}

	public Benutzer getAuthenticatedUser() {
		return authenticatedUser;
	}

	public void setAuthenticatedUser(Benutzer authenticatedUser) {
		this.authenticatedUser = authenticatedUser;
	}

	public String getAuthenticatedUsername() {
		if (authenticatedUser != null) {
			return authenticatedUser.getName();
		} else {
			return null;
		}
	}

	public String getAuthenticatedUserRole() {
		if (authenticatedUser != null) {
			return authenticatedUser.getRolle();
		} else {
			return null;
		}

	}

	// Optional: Methode zum Ausloggen
	public String logout() {
		authenticatedUser = null; // Benutzer abmelden
		return "/login.xhtml?faces-redirect=true"; // Zur Login-Seite umleiten
	}
}
