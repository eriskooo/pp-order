package sk.pazurik.customerservice.domain.customer;

import org.slf4j.Logger;
import sk.pazurik.customerservice.infrastructure.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityNotFoundException;

@Repository
public class CustomerRepository {
    
    @Inject
    private EntityManager entityManager;

    @Inject
    private Logger logger;
    
    public Collection<CustomerEntity> getAllCustomers() {
        return entityManager.createNamedQuery(CustomerEntity.GET_ALL_CUSTOMERS, CustomerEntity.class).getResultList();
    }

    public List<CustomerEntity> getCustomersByName(String name) {
        List<CustomerEntity> customers = entityManager.createNamedQuery(CustomerEntity.GET_CUSTOMERS_BY_NAME, CustomerEntity.class).setParameter("name", name).getResultList();
        if (customers == null || customers.isEmpty()) {
            throw new EntityNotFoundException("Customer not found");
        }
        return customers;
    }
        
    public CustomerEntity getCustomerById(Long id) {
        return entityManager.find(CustomerEntity.class, id);
    }
   
    public void saveOrUpdateCustomer(CustomerEntity customer) {
        if (customer.getId() == null) {
            entityManager.persist(customer);
            logger.info("persisted, {}" , customer);
        } else {
            entityManager.merge(customer);
            logger.info("merged, {}" ,customer);
        }
    }

    public void deleteCustomer(Long id) {
        CustomerEntity customer = entityManager.find(CustomerEntity.class, id);
        if (customer == null) {
            throw new EntityNotFoundException("Customer not found");
        }
        entityManager.remove(customer);
    }
}