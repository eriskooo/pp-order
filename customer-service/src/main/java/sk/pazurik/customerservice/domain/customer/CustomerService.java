package sk.pazurik.customerservice.domain.customer;

import java.util.Collection;

public interface CustomerService {
    Collection<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerById(Long id);
    
    void saveCustomer(CustomerDTO customer);
    
    void updateCustomer(CustomerDTO customer);
    
    void deleteCustomer(Long id);
}
