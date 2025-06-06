package requirementsengineer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.faces.model.SelectItem;

@Named
@ApplicationScoped
public class Anforderungsliste implements Serializable {
	private List<Anforderung> liste;

	public Anforderungsliste() {
		liste = new ArrayList<>();
		liste.add(new Anforderung(1, "EinsAnforderungsTitel", "<p>BeschreibungAnforderungEins<p>"));
		liste.add(new Anforderung(2, "ZweisAnforderungsTitel", "<p>BeschreibungAnforderungZwei<p>"));
		liste.add(new Anforderung(3, "DreiAnforderungsTitel", "<p>BeschreibungAnforderungDrei<p>"));
		liste.add(new Anforderung(4, "VierAnforderungsTitel", "<p>BeschreibungAnforderungVier<p>"));
		liste.add(new Anforderung(5, "FünfAnforderungsTitel", "<p>BeschreibungAnforderungFünf<p>"));
		liste.add(new Anforderung(6, "SechsAnforderungsTitel", "<p>BeschreibungAnforderungSechs<p>"));

	}

	public List<Anforderung> getListe() {
		return liste;
	}

	public void setListe(List<Anforderung> liste) {
		this.liste = liste;
	}

	/*
	 * Erstellt eine Liste von SelectItem-Objekten, die für UI-Komponenten wie
	 * p:selectOneRadio oder p:selectOneMenu verwendet werden können.
	 */
	public List<SelectItem> getAnforderungenAsSelectItems() {
		List<SelectItem> selectItems = new ArrayList<>();
		for (Anforderung anforderung : liste) {
			// Erstellen eines SelectItem: SelectItem(Object value, String label)
			selectItems.add(new SelectItem(anforderung.getId(), anforderung.getAnforderungstitel()));
		}
		return selectItems;
	}

}