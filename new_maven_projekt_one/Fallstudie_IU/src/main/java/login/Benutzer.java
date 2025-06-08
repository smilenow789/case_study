package login;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Benutzer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ID;
	private String name;
	private String passwort;
	private String rolle;

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

	public void setRolle(String rolle) {
		this.rolle = rolle;
	}

	public String getRolle() {
		return rolle;
	}

	public Benutzer(String name, String passwort, String rolle) {
		super();
		this.name = name;
		this.passwort = passwort;
		this.rolle = rolle;
	}

	public Benutzer() {
		super();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Benutzer) {
			Benutzer b = (Benutzer) obj;
			if (b.getName().equals(this.name) && b.getPasswort().equals(this.passwort)) {
				return true;
			}
		}
		return false;
	}
}
