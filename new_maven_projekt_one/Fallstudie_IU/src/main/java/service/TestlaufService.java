package service;

import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import model.Benutzer;
import model.Testfall;
import model.Testlauf;
import repository.TestlaufRepository;
import repository.TestfallRepository;
import repository.UserRepository;

@ApplicationScoped
public class TestlaufService {

	@Inject
	private TestlaufRepository testlaufRepository;

	@Inject
	private TestfallRepository testfallRepository;

	@Inject
	private UserRepository userRepository;

	@Transactional
	public Testlauf createTestlauf(String titel, String beschreibung, List<Integer> selectedTestfaelleIds,
			Integer zugewiesenerTesterId) {

		if (titel == null || titel.trim().isEmpty() || beschreibung == null || beschreibung.trim().isEmpty()
				|| zugewiesenerTesterId == null || selectedTestfaelleIds == null || selectedTestfaelleIds.isEmpty()) {
			throw new IllegalArgumentException(
					"Alle erforderlichen Felder müssen ausgefüllt und mindestens ein Testfall ausgewählt sein.");
		}

		Benutzer tester = userRepository.findById(zugewiesenerTesterId);
		if (tester == null) {
			throw new IllegalArgumentException("Der zugewiesene Tester konnte nicht gefunden werden.");
		}

		Set<Testfall> selectedTestfaelle = new HashSet<>();
		for (Integer testfallId : selectedTestfaelleIds) {
			Testfall testfall = testfallRepository.findById(testfallId);
			if (testfall != null) {
				testfall.addZugewiesenerBenutzer(tester);
				testfallRepository.update(testfall);
				selectedTestfaelle.add(testfall);
			}
		}

		if (selectedTestfaelle.isEmpty()) {
			throw new IllegalArgumentException(
					"Es wurden keine gültigen Testfälle zum Anlegen des Testlaufs gefunden.");
		}

		Testlauf neuerTestlauf = new Testlauf(titel, beschreibung, selectedTestfaelle);
		testlaufRepository.save(neuerTestlauf);

		// Ensure bidirectional relationship from Testfall to Testlauf
		// Testlauf's ManyToMany 'ausgewaehlteTestfaelle' owns the relationship
		// So, this loop is not strictly necessary if Testlauf's side is correctly
		// managing,
		// but it can help ensure data consistency or if Testlauf is not the owning
		// side.
		// Given your Testlauf.java, Testlauf is the owning side, so this might be
		// redundant
		// if `testlaufRepository.save` cascades correctly and sets the join table
		// entries.
		// However, for safety with existing setup, we'll keep it for now.
		for (Testfall tf : selectedTestfaelle) {
			tf.getZugehoerigeTestlaeufe().add(neuerTestlauf);
			testfallRepository.update(tf); // Ensure this merge cascade is handled or entity is managed
		}
		return neuerTestlauf;
	}

	public List<Testlauf> getAllTestlaeufe() {
		return testlaufRepository.findAll();
	}

}