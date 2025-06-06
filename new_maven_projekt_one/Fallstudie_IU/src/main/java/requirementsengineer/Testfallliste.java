package requirementsengineer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.faces.model.SelectItem;

@Named
@ApplicationScoped
public class Testfallliste implements Serializable {
	private List<Testfall> liste;

	public Testfallliste() {
		liste = new ArrayList<>();
		liste.add(new Testfall("Testfall Titel 1", "Beschreibung für Testfall 1", 2001));
		liste.add(new Testfall("Testfall Titel 2", "Beschreibung für Testfall 2", 2002));
		liste.add(new Testfall("Testfall Titel 3", "Beschreibung für Testfall 3", 2003));
		liste.add(new Testfall("Testfall Titel 4", "Beschreibung für Testfall 4", 2004));
		liste.add(new Testfall("Testfall Titel 5", "Beschreibung für Testfall 5", 2005));

	}

	public List<Testfall> getListe() {
		return liste;
	}

	public void setListe(List<Testfall> liste) {
		this.liste = liste;
	}

	public List<SelectItem> getTestfaelleAsSelectItems() {
		List<SelectItem> selectItems = new ArrayList<>();
		for (Testfall testfall : liste) {
			// Erstellen eines SelectItem: SelectItem(Object value, String label)
			selectItems.add(new SelectItem(testfall.getId(), testfall.getTestfallTitel()));
		}
		return selectItems;
	}
}
