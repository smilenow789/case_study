public class Benutzer {
	String name;
	String passwort;
	String rolle;

	public Benutzer() {
	}

	public Benutzer(String name, String passwort) {
		this.name = name;
		this.passwort = passwort;
	}

	public String getRolle() {
		return rolle;
	}

	public void setRolle(String rolle) {
		this.rolle = rolle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

}