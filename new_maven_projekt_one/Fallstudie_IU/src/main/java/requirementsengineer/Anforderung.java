package requirementsengineer;

import java.io.Serializable;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Anforderung implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ID;
	private String anforderungstitel;
	private String beschreibung;

//	@Inject
	// @OneToMany
	// private Anforderungsliste anforderungsliste;

	public Anforderung() {
	}

	public Anforderung(String anforderungstitel, String beschreibung) {
		this.anforderungstitel = anforderungstitel;
		this.beschreibung = beschreibung;

	}

	public String getAnforderungstitel() {
		return anforderungstitel;
	}

	public void setAnforderungstitel(String anforderungstitel) {
		this.anforderungstitel = anforderungstitel;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public static void info() {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Erfolg!", "Anforderung erfolgreich erfasst."));
	}

}