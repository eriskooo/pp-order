package sk.pazurik.customerservice.domain.customer;

import org.slf4j.Logger;
import sk.pazurik.customerservice.infrastructure.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collection;

@Repository
public class CustomerRepository {
    
    @Inject
    private EntityManager entityManager;

    @Inject
    private Logger logger;
    
    public Collection<CustomerEntity> getAllCustomers() {
        return entityManager.createNamedQuery(CustomerEntity.GET_ALL_CUSTOMERS, CustomerEntity.class).getResultList();
    }

    public CustomerEntity getCustomerById(Long id) {
        return entityManager.find(CustomerEntity.class, id);
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

    public boolean updateCustomer(CustomerEntity customer) {
        if (customer.getId() != null) {
            CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, customer.getId());
            if (customerEntity != null) {
                entityManager.merge(customer);
                return true;
            }
        }
        return false;
    }

    public boolean deleteCustomer(Long id) {
        CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, id);
        if (customerEntity == null) {
            return false;
        } else {
            entityManager.remove(customerEntity);
            return true;
        }
    }
}
