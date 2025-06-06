package testfallersteller;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class Testfallliste implements Serializable {
	private static Testfallliste instance = new Testfallliste();
	private List<Testfall> liste = new ArrayList<Testfall>();

	public Testfallliste() {
		liste.add(new Testfall("EinsTestfalltitel", "EinsTestfallBeschreibung", 1, "noch nicht durchgefuehrt"));
		liste.add(new Testfall("ZweiTestfalltitel", "ZweiTestfallBeschreibung", 2, "noch nicht durchgefuehrt"));
		liste.add(new Testfall("DreiTestfalltitel", "DreiTestfallBeschreibung", 3, "noch nicht durchgefuehrt"));

	}

	public static Testfallliste getInstance() {
		return instance;
	}

	public List<Testfall> getListe() {
		return liste;
	}
}
