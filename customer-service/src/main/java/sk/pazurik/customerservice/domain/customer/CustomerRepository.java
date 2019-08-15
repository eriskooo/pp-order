package sk.pazurik.customerservice.domain.customer;

import java.util.Collection;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import sk.pazurik.customerservice.infrastructure.stereotype.Repository;

@Repository
public class CustomerRepository {
    
    @Inject
    private EntityManager entityManager;

    @Inject
    private Logger logger;
    
    public Collection<CustomerEntity> getAllCustomers() {
        return entityManager.createNamedQuery(CustomerEntity.GET_ALL_CUSTOMERS, CustomerEntity.class).getResultList();
    }

    public void saveCustomer(CustomerEntity customer) {
        if (customer.getId() == null) {
            entityManager.persist(customer);
            logger.info("persisted, {}" , customer);
        } else {
            entityManager.merge(customer);
            logger.info("merged, {}" ,customer);
        }
    }
}
