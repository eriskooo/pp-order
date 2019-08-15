package sk.pazurik.customerservice.domain.customer;

import java.util.Collection;

public interface CustomerService {
    Collection<CustomerDTO> getAllCustomers();

    void saveCustomer(CustomerDTO customer);
}
