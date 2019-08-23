package sk.pazurik.customerservice.domain.order;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderDTOTest {

    @Test
    public void convertDTOtoEntity() {
        OrderDTO dto = TestOrder.ORDER_1_DTO();
        OrderEntity entity = new OrderEntity(dto);
        
        assertThat(dto.getId()).isEqualTo(entity.getId());
        assertThat(dto.getOrderDate()).isEqualTo(entity.getOrderDate());
        assertThat(dto.getPrice_w_VAT()).isEqualTo(entity.getPrice_w_VAT());
        assertThat(dto.getPrice_wo_VAT()).isEqualTo(entity.getPrice_wo_VAT());
    }

    @Test
    public void convertEntityToDTO() {
        OrderEntity entity = TestOrder.ORDER_1_ENTITY();
        OrderDTO dto = new OrderDTO(entity);

        assertThat(dto.getId()).isEqualTo(entity.getId());
        assertThat(dto.getOrderDate()).isEqualTo(entity.getOrderDate());
        assertThat(dto.getPrice_w_VAT()).isEqualTo(entity.getPrice_w_VAT());
        assertThat(dto.getPrice_wo_VAT()).isEqualTo(entity.getPrice_wo_VAT());
    }
}
