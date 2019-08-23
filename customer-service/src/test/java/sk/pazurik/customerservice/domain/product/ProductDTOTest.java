package sk.pazurik.customerservice.domain.product;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductDTOTest {

    @Test
    public void convertDTOtoEntity() {
        ProductDTO dto = TestProduct.PRODUCT_1_DTO();
        ProductEntity entity = new ProductEntity(dto);

        assertThat(dto.getDescription()).isEqualTo(entity.getDescription());
        assertThat(dto.getEan()).isEqualTo(entity.getEan());
        assertThat(dto.getId()).isEqualTo(entity.getId());
        assertThat(dto.getName()).isEqualTo(entity.getName());
        assertThat(dto.getPrice_w_VAT()).isEqualTo(entity.getPrice_w_VAT());
        assertThat(dto.getPrice_wo_VAT()).isEqualTo(entity.getPrice_wo_VAT());
    }

    @Test
    public void convertEntityToDTO() {
        ProductEntity entity = TestProduct.PRODUCT_1_ENTITY();
        ProductDTO dto = new ProductDTO(entity);

        assertThat(dto.getDescription()).isEqualTo(entity.getDescription());
        assertThat(dto.getEan()).isEqualTo(entity.getEan());
        assertThat(dto.getId()).isEqualTo(entity.getId());
        assertThat(dto.getName()).isEqualTo(entity.getName());
        assertThat(dto.getPrice_w_VAT()).isEqualTo(entity.getPrice_w_VAT());
        assertThat(dto.getPrice_wo_VAT()).isEqualTo(entity.getPrice_wo_VAT());
    }
}
