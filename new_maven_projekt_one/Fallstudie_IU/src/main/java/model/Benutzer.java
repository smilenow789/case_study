package model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

//Entität für einen Benutzer in der Datenbank
@Entity
public class Benutzer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ID;
	private String name;
	private String passwort;
	private String rolle;

	@ManyToMany(mappedBy = "zugewieseneBenutzer")
	private Set<Testfall> zugewieseneTestfaelle = new HashSet<>();

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

	public Set<Testfall> getZugewieseneTestfaelle() {
		return zugewieseneTestfaelle;
	}

	public void setZugewieseneTestfaelle(Set<Testfall> zugewieseneTestfaelle) {
		this.zugewieseneTestfaelle = zugewieseneTestfaelle;
	}

	public Benutzer() {
		super();
	}

	public Benutzer(String name, String passwort, String rolle) {
		super();
		this.name = name;
		this.passwort = passwort;
		this.rolle = rolle;
	}

	// Überschreibt die equals-Methode, um Benutzer anhand von Name und Passwort zu
	// vergleichen
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
