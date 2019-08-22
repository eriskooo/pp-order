package sk.pazurik.customerservice.domain.product;

import java.math.BigDecimal;

public class TestProduct {

    public static ProductEntity PRODUCT_1_ENTITY() {
        return new ProductEntity(PRODUCT_1_DTO());
    }

    public static ProductDTO PRODUCT_1_DTO() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setDescription("desc");
        productDTO.setEan("ean");
        productDTO.setName("name ");
        productDTO.setPrice_w_VAT(BigDecimal.TEN);
        productDTO.setPrice_wo_VAT(BigDecimal.ONE);
        return productDTO;
    }
}
