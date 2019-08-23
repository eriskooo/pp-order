package sk.pazurik.customerservice.domain.customer;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private Logger logger;

    @Mock
    private CustomerRepository repository;

    @Test
    public void getAllCustomersShouldReturnCollection() {
        Mockito.when(repository.getAllCustomers()).thenReturn(Collections.singletonList(TestCustomer.CUSTOMER_1_ENTITY()));
        Collection<CustomerDTO> allCustomers = customerService.getAllCustomers();
        Mockito.verify(repository, Mockito.times(1)).getAllCustomers();
        assertThat(allCustomers.iterator().next().getId()).isEqualTo(TestCustomer.CUSTOMER_1_ENTITY().getId());
    }

    @Test
    public void getCustomersByNameShouldReturnCollection() {
        Mockito.when(repository.getCustomersByName(Mockito.anyString())).thenReturn(Collections.singletonList(TestCustomer.CUSTOMER_1_ENTITY()));
        Collection<CustomerDTO> customers = customerService.getCustomersByName(Mockito.anyString());
        Mockito.verify(repository, Mockito.times(1)).getCustomersByName(Mockito.anyString());
        assertThat(customers.iterator().next().getId()).isEqualTo(TestCustomer.CUSTOMER_1_ENTITY().getId());
    }
    
    @Test
    public void getCustomersByNameWhenNotFoundShouldReturnCollection() {
        thrown.expect(EntityNotFoundException.class);
        Mockito.doThrow(EntityNotFoundException.class).when(repository).getCustomersByName(Mockito.anyString());
        Collection<CustomerDTO> customers = customerService.getCustomersByName(Mockito.anyString());
        Mockito.verify(repository, Mockito.times(1)).getCustomersByName(Mockito.anyString());
    }

    @Test
    public void getCustomerByIdShouldReturnCustomer() {
        Mockito.when(repository.getCustomerById(anyLong())).thenReturn(TestCustomer.CUSTOMER_1_ENTITY());
        CustomerDTO customer = customerService.getCustomerById(TestCustomer.CUSTOMER_1_ENTITY().getId());
        Mockito.verify(repository, Mockito.times(1)).getCustomerById(anyLong());
        assertThat(customer.getId()).isEqualTo(TestCustomer.CUSTOMER_1_ENTITY().getId());
    }

    @Test
    public void getCustomertByIdWhenNotFoundShouldThrowException() {
        thrown.expect(EntityNotFoundException.class);
        Mockito.when(repository.getCustomerById(anyLong())).thenReturn(null);
        CustomerDTO customer = customerService.getCustomerById(777l);
        Mockito.verify(repository, Mockito.times(1)).getCustomerById(anyLong());
    }

    @Test
    public void saveCustomer() {
        customerService.saveCustomer(TestCustomer.CUSTOMER_1_DTO());
        Mockito.verify(repository, Mockito.times(1)).saveOrUpdateCustomer(any());
    }
    
    @Test
    public void updateCustomer() {
        Mockito.when(repository.getCustomerById(anyLong())).thenReturn(TestCustomer.CUSTOMER_1_ENTITY());
        customerService.updateCustomer(TestCustomer.CUSTOMER_1_DTO());
        Mockito.verify(repository, Mockito.times(1)).getCustomerById(anyLong());
        Mockito.verify(repository, Mockito.times(1)).saveOrUpdateCustomer(any());
    }

    @Test
    public void updateCustomerWhenNotFoundShouldThrowException() {
        thrown.expect(EntityNotFoundException.class);
        Mockito.when(repository.getCustomerById(anyLong())).thenReturn(null);
        customerService.updateCustomer(TestCustomer.CUSTOMER_1_DTO());
        Mockito.verify(repository, Mockito.times(1)).getCustomerById(anyLong());
    }
    
    @Test
    public void deleteCustomer() {
        customerService.deleteCustomer(anyLong());
        Mockito.verify(repository, Mockito.times(1)).deleteCustomer(anyLong());
    }

    @Test
    public void deleteCustomerWhenNotFoundShouldThrowException() {
        thrown.expect(EntityNotFoundException.class);
        Mockito.doThrow(EntityNotFoundException.class).when(repository).deleteCustomer(anyLong());
        customerService.deleteCustomer(anyLong());
        Mockito.verify(repository, Mockito.times(1)).deleteCustomer(anyLong());        
    }
}