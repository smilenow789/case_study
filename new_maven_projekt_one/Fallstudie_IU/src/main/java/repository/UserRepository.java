package repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import model.Benutzer;

@ApplicationScoped
public class UserRepository {

	@Inject
	private EntityManager em;

	public Benutzer findByName(String username) {
		try {
			TypedQuery<Benutzer> query = em.createQuery("SELECT b FROM Benutzer b WHERE b.name = :username",
					Benutzer.class);
			query.setParameter("username", username);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Benutzer findById(Integer id) {
		return em.find(Benutzer.class, id);
	}

	@Transactional
	public void save(Benutzer benutzer) {
		em.getTransaction().begin();
		em.persist(benutzer);
		em.getTransaction().commit();
	}

	public List<Benutzer> findAll() {
		return em.createQuery("SELECT b FROM Benutzer b", Benutzer.class).getResultList();
	}

	public List<Benutzer> findByRole(String role) {
		TypedQuery<Benutzer> query = em.createQuery("SELECT b FROM Benutzer b WHERE b.rolle = :role", Benutzer.class);
		query.setParameter("role", role);
		return query.getResultList();
	}
}