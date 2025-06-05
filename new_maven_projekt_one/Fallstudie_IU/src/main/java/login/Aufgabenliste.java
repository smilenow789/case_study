package login;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named("aufgabenListe")
@ApplicationScoped
public class Aufgabenliste implements Serializable {
	private static Aufgabenliste instance = new Aufgabenliste();
	private List<Aufgabe> liste = new ArrayList<Aufgabe>();
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

	public Aufgabenliste() {
		try {
			liste.add(new Aufgabe("tue dies", "Jakob", dateFormat.parse("03.09.2024"), true, 14));
			liste.add(new Aufgabe("tue das", "Nina", dateFormat.parse("10.09.2024"), false, 8));
			liste.add(new Aufgabe("tue jenes", "Jessie", dateFormat.parse("17.09.2024"), false, 22));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static Aufgabenliste getInstance() {
		return instance;
	}

	public List<Aufgabe> getListe() {
		return liste;
	}
}
