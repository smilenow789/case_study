package repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import model.Testlauf;
import java.util.List;

@ApplicationScoped
public class TestlaufRepository {

	@Inject
	private EntityManager em;

	@Transactional
	public void save(Testlauf testlauf) {
		em.getTransaction().begin();
		em.persist(testlauf);
		em.getTransaction().commit();
	}

	public Testlauf findById(Integer id) {
		return em.find(Testlauf.class, id);
	}

	public List<Testlauf> findAll() {
		return em.createQuery("SELECT tl FROM Testlauf tl", Testlauf.class).getResultList();
	}

	@Transactional
	public Testlauf update(Testlauf testlauf) {
		return em.merge(testlauf);
	}
}