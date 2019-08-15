package sk.pazurik.customerservice.domain.customer;

import java.util.Collection;
import java.util.stream.Collectors;
import javax.inject.Inject;
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
    public void saveCustomer(CustomerDTO customerDTO) {
        CustomerEntity customerEntity = new CustomerEntity(customerDTO);
        repository.saveCustomer(customerEntity);
        logger.info("saveCustomer ok, {}", customerEntity);
    }
    
}
