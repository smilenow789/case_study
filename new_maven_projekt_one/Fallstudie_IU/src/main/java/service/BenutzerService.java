package service;

import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import model.Benutzer;
import repository.UserRepository;

@ApplicationScoped
public class BenutzerService {

    @Inject
    private UserRepository userRepository;

    public List<Benutzer> getAllBenutzer() {
        return userRepository.findAll();
    }

    public List<Benutzer> getTester() {
        return userRepository.findByRole("tester");
    }
}