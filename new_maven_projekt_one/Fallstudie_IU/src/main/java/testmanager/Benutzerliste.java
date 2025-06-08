package testmanager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import login.Benutzer;

@Named
@ViewScoped
public class Benutzerliste implements Serializable {

	@Inject
	private EntityManager em;

	public Benutzerliste() {
	}

	public List<SelectItem> getTesterListe() {
		List<Benutzer> testerListe;
		testerListe = em.createQuery("SELECT b FROM Benutzer b WHERE rolle = 'tester'", Benutzer.class).getResultList();
		List<SelectItem> selectItems = new ArrayList<>();
		for (Benutzer benutzer : testerListe) {
			selectItems.add(new SelectItem(benutzer.getName()));
		}
		return selectItems;
	}

}
