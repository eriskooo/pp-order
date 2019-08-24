package sk.pazurik.customerservice.domain.order;

import sk.pazurik.customerservice.domain.customer.TestCustomer;
import sk.pazurik.customerservice.domain.product.TestProduct;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

public class TestOrder {

    public static OrderEntity ORDER_1_ENTITY() {
        OrderEntity orderEntity = new OrderEntity(ORDER_1_DTO());
        orderEntity.setCustomer(TestCustomer.CUSTOMER_1_ENTITY());
        orderEntity.setProducts(Collections.singletonMap(TestProduct.PRODUCT_1_ENTITY(), 1L));
        return orderEntity;
    }

    public static OrderDTO ORDER_1_DTO() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        orderDTO.setOrderDate(LocalDate.now().minusDays(1));
        orderDTO.setPrice_w_VAT(BigDecimal.TEN);
        orderDTO.setPrice_wo_VAT(BigDecimal.ONE);
        orderDTO.setProducts(Collections.singletonList(new OrderProductQuantityDTO(1L, 1L)));
        return orderDTO;
    }
}