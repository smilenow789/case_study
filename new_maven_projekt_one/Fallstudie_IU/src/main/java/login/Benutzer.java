package login;

public class Benutzer {
	String name;
	String passwort;
	
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
	public Benutzer(String name, String passwort) {
		super();
		this.name = name;
		this.passwort = passwort;
	}
	public Benutzer() {
		super();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Benutzer) {
			Benutzer b = (Benutzer)obj;
			if(b.getName().equals(this.name) && b.getPasswort().equals(this.passwort)) {
				return true;
			}
		}
		return false;
	}
}
