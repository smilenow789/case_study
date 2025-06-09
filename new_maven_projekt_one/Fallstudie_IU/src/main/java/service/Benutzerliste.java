package service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import model.Benutzer;

@Named
@RequestScoped
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
			selectItems.add(new SelectItem(benutzer.getID(), benutzer.getName()));
		}
		return selectItems;
	}

}
