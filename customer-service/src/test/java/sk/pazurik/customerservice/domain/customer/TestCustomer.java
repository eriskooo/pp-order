package sk.pazurik.customerservice.domain.customer;

import sk.pazurik.customerservice.domain.order.TestOrder;

import java.util.Collections;

public class TestCustomer {

    public static CustomerEntity CUSTOMER_1_ENTITY() {
        CustomerEntity customerEntity = new CustomerEntity(CUSTOMER_1_DTO());
        return customerEntity;
    }

    public static CustomerDTO CUSTOMER_1_DTO() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setName("name");
        customerDTO.setSurname("surname");
        customerDTO.setPhone("+421907907907");
        customerDTO.setEmail("test@test.com");
        customerDTO.setOrders(Collections.singletonList(TestOrder.ORDER_1_DTO()));
        return customerDTO;
    }
}
