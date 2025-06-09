package repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import model.Testfall;
import model.Anforderung;
import model.Benutzer;

@ApplicationScoped
public class TestfallRepository {

	@Inject
	private EntityManager em;

	public long count() {
		return em.createQuery("SELECT COUNT(t) FROM Testfall t", Long.class).getSingleResult();
	}

	@Transactional
	public void save(Testfall testfall) {
		em.getTransaction().begin();
		em.persist(testfall);
		em.getTransaction().commit();
	}

	@Transactional
	public Testfall update(Testfall testfall) {
		return em.merge(testfall);
	}

	public List<Testfall> findAll() {
		return em.createQuery("SELECT t FROM Testfall t", Testfall.class).getResultList();
	}

	public Testfall findById(Integer id) {
		return em.find(Testfall.class, id);
	}

	public List<Testfall> findByAssignedTester(Benutzer tester) {
		TypedQuery<Testfall> query = em.createQuery(
				"SELECT tf FROM Testfall tf JOIN tf.zugewieseneBenutzer b WHERE b = :tester", Testfall.class);
		query.setParameter("tester", tester);
		return query.getResultList();
	}

	// This method logically belongs to AnforderungRepository, but given it's used
	// here for initial data, we'll keep it here for now or move it to
	// AnforderungService.
	// For a cleaner architecture, findOrCreateAnforderung should be part of
	// AnforderungService
	// and injected into TestfallService.
	@Transactional
	public Anforderung findOrCreateAnforderung(String titel, String beschreibung) {
		try {
			return em.createQuery("SELECT a FROM Anforderung a WHERE a.anforderungstitel = :titel", Anforderung.class)
					.setParameter("titel", titel).getSingleResult();
		} catch (NoResultException e) {
			Anforderung newAnf = new Anforderung(titel, beschreibung);
			em.persist(newAnf);
			return newAnf;
		}
	}
}