package login;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import requirementsengineer.Testfall;

@Entity
public class Benutzer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ID;
	private String name;
	private String passwort;
	private String rolle;

	@OneToMany(mappedBy = "zugehoerigerBenutzer", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Testfall> zugehoerigeTestfaelle = new HashSet<>();

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
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

	public void setRolle(String rolle) {
		this.rolle = rolle;
	}

	public String getRolle() {
		return rolle;
	}

	public Set<Testfall> getZugehoerigeTestfaelle() {
		return zugehoerigeTestfaelle;
	}

	public void setZugehoerigeTestfaelle(Set<Testfall> zugehoerigeTestfaelle) {
		this.zugehoerigeTestfaelle = zugehoerigeTestfaelle;
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
