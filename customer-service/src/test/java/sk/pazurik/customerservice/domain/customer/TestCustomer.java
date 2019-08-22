package sk.pazurik.customerservice.domain.customer;

public class TestCustomer {

    public static CustomerEntity CUSTOMER_1_ENTITY() {
        return new CustomerEntity(CUSTOMER_1_DTO());
    }

    public static CustomerDTO CUSTOMER_1_DTO() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setName("name");
        customerDTO.setSurname("surname");
        customerDTO.setPhone("+421907907907");
        customerDTO.setEmail("test@test.com");
        customerDTO.setPhoto("fileBytes".getBytes());
        return customerDTO;
    }
}
