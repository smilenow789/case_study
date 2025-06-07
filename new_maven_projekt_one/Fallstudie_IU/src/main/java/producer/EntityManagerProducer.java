package producer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence; // <-- Import this!
// import jakarta.persistence.PersistenceUnit; // <-- We'll remove this field annotation

@ApplicationScoped
public class EntityManagerProducer {

	// REMOVE the @PersistenceUnit annotation from here.
	// Instead, we will create the EntityManagerFactory explicitly in a producer
	// method.
	// private EntityManagerFactory emf; // Keep this field, but it won't be
	// auto-injected by Tomcat's limited JPA support

	// This method produces the EntityManagerFactory for injection.
	// It's crucial for Tomcat as it doesn't auto-inject @PersistenceUnit
	@Produces
	@ApplicationScoped // EntityManagerFactory should be application-scoped
	public EntityManagerFactory createEntityManagerFactory() {
		// "fallstudie" must match the persistence-unit name in persistence.xml
		return Persistence.createEntityManagerFactory("fallstudie");
	}

	// This method injects the EntityManagerFactory created above
	// and then produces an EntityManager instance.
	@Produces
	@RequestScoped // EntityManager should be request-scoped
	public EntityManager createEntityManager(EntityManagerFactory emf) { // emf is now INJECTED here
		// This line (line 20) will now work because emf is guaranteed to be initialized
		return emf.createEntityManager();
	}

	// This method cleans up the EntityManager when it's no longer needed
	public void closeEntityManager(@Disposes EntityManager em) {
		if (em.isOpen()) {
			em.close();
		}
	}

	// This method disposes of the EntityManagerFactory when the application shuts
	// down
	public void closeEntityManagerFactory(@Disposes EntityManagerFactory emf) {
		if (emf.isOpen()) {
			emf.close();
		}
	}
}