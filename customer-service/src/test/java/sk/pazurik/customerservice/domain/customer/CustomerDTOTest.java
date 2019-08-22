package sk.pazurik.customerservice.domain.customer;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerDTOTest {

    @Test
    public void convertDTOtoEntity() {
        CustomerDTO dto = TestCustomer.CUSTOMER_1_DTO();
        CustomerEntity entity = new CustomerEntity(dto);

        assertThat(dto.getId()).isEqualTo(entity.getId());
        assertThat(dto.getName()).isEqualTo(entity.getName());
        assertThat(dto.getSurname()).isEqualTo(entity.getSurname());
        assertThat(dto.getPhone()).isEqualTo(entity.getPhone());
        assertThat(dto.getEmail()).isEqualTo(entity.getEmail());
        assertThat(dto.getPhoto()).isEqualTo(entity.getPhoto());
    }

    @Test
    public void convertEntityToDTO() {
        CustomerEntity entity = TestCustomer.CUSTOMER_1_ENTITY();
        CustomerDTO dto = new CustomerDTO(entity);

        assertThat(dto.getId()).isEqualTo(entity.getId());
        assertThat(dto.getName()).isEqualTo(entity.getName());
        assertThat(dto.getSurname()).isEqualTo(entity.getSurname());
        assertThat(dto.getPhone()).isEqualTo(entity.getPhone());
        assertThat(dto.getEmail()).isEqualTo(entity.getEmail());
        assertThat(dto.getPhoto()).isEqualTo(entity.getPhoto());
    }
}
