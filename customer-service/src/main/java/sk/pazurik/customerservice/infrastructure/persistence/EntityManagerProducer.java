package sk.pazurik.customerservice.infrastructure.persistence;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EntityManagerProducer {
    @Produces
    @PersistenceContext(unitName = "pu")
    EntityManager entityManager;
}