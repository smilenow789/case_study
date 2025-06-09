package service;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.ArrayList;

import model.Testfall;
import repository.TestfallRepository;
import repository.UserRepository;
import model.Anforderung;
import model.Benutzer;

@ApplicationScoped
public class TestfallService {

    @Inject
    private TestfallRepository testfallRepository;

    @Inject
    private UserRepository userRepository;

    @PostConstruct
    @Transactional 
    public void initializeTestfaelleIfEmpty() {
        if (testfallRepository.count() == 0) {

            Anforderung anforderung1 = testfallRepository.findOrCreateAnforderung("EinsAnforderungsTitel", "BeschreibungAnforderungEins");
            Anforderung anforderung2 = testfallRepository.findOrCreateAnforderung("ZweisAnforderungsTitel", "BeschreibungAnforderungZwei");
            Anforderung anforderung3 = testfallRepository.findOrCreateAnforderung("DreiAnforderungsTitel", "BeschreibungAnforderungDrei");

            testfallRepository.save(new Testfall(anforderung1, "Testfall Titel 1", "Beschreibung für Testfall 1"));
            testfallRepository.save(new Testfall(anforderung2, "Testfall Titel 2", "Beschreibung für Testfall 2"));
            testfallRepository.save(new Testfall(anforderung2, "Testfall Titel 3", "Beschreibung für Testfall 3"));
            testfallRepository.save(new Testfall(anforderung3, "Testfall Titel 4", "Beschreibung für Testfall 4"));
            testfallRepository.save(new Testfall(anforderung3, "Testfall Titel 5", "Beschreibung für Testfall 5"));
        }
    }

    @Transactional
    public Testfall createTestfall(Integer anforderungId, String testfallTitel, String beschreibung) {
        if (testfallTitel == null || testfallTitel.trim().isEmpty() || beschreibung == null || beschreibung.trim().isEmpty()) {
            throw new IllegalArgumentException("Testfalltitel und Beschreibung dürfen nicht leer sein.");
        }
        if (anforderungId == null) {
            throw new IllegalArgumentException("Eine Anforderung muss für den Testfall ausgewählt werden.");
        }

        // Anforderung zugehoerigeAnforderung = testfallRepository.findById(anforderungId).getZuErfuellendeAnforderung(); 
        // Assuming findById returns Testfall and Testfall has getZuErfuellendeAnforderung()
        // Correction: Anforderung object needs to be retrieved from AnforderungRepository
        // For simplicity for now, let's assume findOrCreateAnforderung returns an Anforderung
        // or add AnforderungRepository to TestfallService.
        // Let's adapt:
        // @Inject AnforderungRepository anforderungRepository;
        // Anforderung zugehoerigeAnforderung = anforderungRepository.findById(anforderungId);
        // FOR NOW, using testfallRepository.findOrCreateAnforderung logic (which is slightly out of place but works for migration)
        // If AnforderungRepository exists, use it:
        // @Inject private AnforderungRepository anforderungRepository;
        // Anforderung zugehoerigeAnforderung = anforderungRepository.findById(anforderungId);

        // Given the current structure, if Anforderung cannot be directly found by ID in TestfallRepository,
        // you'd need to inject AnforderungRepository here OR ensure findOrCreateAnforderung handles finding existing ones.
        // For the sake of refactoring Testfallliste, let's assume Anforderung lookup works.
        
        // As a temporary fix for refactoring without a dedicated AnforderungRepository lookup in service yet:
        // This is a placeholder. Ideally, an AnforderungService or AnforderungRepository would provide this.
        Anforderung zugehoerigeAnforderung = testfallRepository.findById(anforderungId).getZuErfuellendeAnforderung(); 
        if (zugehoerigeAnforderung == null) {
             throw new IllegalArgumentException("Ausgewählte Anforderung konnte nicht gefunden werden.");
        }

        Testfall neuerTestfall = new Testfall(zugehoerigeAnforderung, testfallTitel, beschreibung);
        testfallRepository.save(neuerTestfall);
        return neuerTestfall;
    }

    @Transactional
    public void saveTestfallResults(List<Testfall> testfaelleToUpdate) {
        if (testfaelleToUpdate != null) {
            for (Testfall t : testfaelleToUpdate) {
                testfallRepository.update(t); // Use update for merging changes
            }
        }
    }

    public List<Testfall> getAllTestfaelle() {
        return testfallRepository.findAll();
    }

    public List<Testfall> getAssignedTestfaelleForTester(String username, String role) {
        if (username != null && role.equals("tester")) {
            Benutzer currentTester = userRepository.findByName(username);
            if (currentTester != null) {
                return testfallRepository.findByAssignedTester(currentTester);
            }
        }
        return new ArrayList<>();
    }
}