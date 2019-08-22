package sk.pazurik.customerservice.domain.customer;

import org.slf4j.Logger;
import sk.pazurik.customerservice.infrastructure.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.stream.Collectors;

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
        }
        return new CustomerDTO(customerEntity);
    }
    
    @Override
    public void saveCustomer(CustomerDTO customerDTO) {
        CustomerEntity customerEntity = new CustomerEntity(customerDTO);
        repository.saveOrUpdateCustomer(customerEntity);
        logger.info("saveCustomer ok, {}", customerEntity);
    }
    
    @Override
    public void updateCustomer(CustomerDTO customerDTO) {
        CustomerEntity customerEntity = repository.getCustomerById(customerDTO.getId());
        if (customerEntity == null) {
            throw new EntityNotFoundException("Customer not found");
        }
        customerEntity = new CustomerEntity(customerDTO);        
        repository.saveOrUpdateCustomer(customerEntity);
        logger.info("updateCustomer ok, {}", customerEntity);
    }
    
    @Override
    public void deleteCustomer(Long id) throws EntityNotFoundException {
        repository.deleteCustomer(id);
        logger.info("deleteCustomer ok, {}", id);
    }
}