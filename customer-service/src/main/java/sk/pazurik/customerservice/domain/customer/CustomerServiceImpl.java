package sk.pazurik.customerservice.domain.customer;

import java.util.Collection;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import sk.pazurik.customerservice.infrastructure.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Inject
    private Logger logger;

    @Inject
    CustomerRepository repository;

    @Override
    public Collection<CustomerDTO> getAllCustomers() {
        return repository.getAllCustomers().stream().map(CustomerDTO::new).collect(Collectors.toList());
    }

   @Override
    public CustomerDTO getCustomerById(Long id) throws EntityNotFoundException {
        CustomerEntity customerEntity = repository.getCustomerById(id);
        if (customerEntity == null) {
            throw new EntityNotFoundException("Customer not found");
        } else {
            return new CustomerDTO(customerEntity);
        }
    }
    
    @Override
    public void saveCustomer(CustomerDTO customerDTO) {
        CustomerEntity customerEntity = new CustomerEntity(customerDTO);
        repository.saveCustomer(customerEntity);
        logger.info("saveCustomer ok, {}", customerEntity);
    }
    
    @Override
    public void updateCustomer(CustomerDTO customerDTO) {
        CustomerEntity customerEntity = new CustomerEntity(customerDTO);
         if (!repository.updateCustomer(customerEntity)) {
            throw new EntityNotFoundException("Customer not found");
         }
         logger.info("updateCustomer ok, {}", customerEntity);
    }
    
    @Override
    public void deleteCustomer(Long id) throws EntityNotFoundException {
        if (!repository.deleteCustomer(id)) {
            throw new EntityNotFoundException("Customer not found");
        }
        logger.info("deleteCustomer ok, {}", id);
    }
}