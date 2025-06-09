package repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import model.Anforderung;

@ApplicationScoped
public class AnforderungRepository {

	@Inject
	private EntityManager em;

	public long count() {
		return em.createQuery("SELECT COUNT(a) FROM Anforderung a", Long.class).getSingleResult();
	}

	@Transactional
	public void save(Anforderung anforderung) {
		em.persist(anforderung);
	}

	public List<Anforderung> findAll() {
		return em.createQuery("SELECT a FROM Anforderung a", Anforderung.class).getResultList();
	}

}