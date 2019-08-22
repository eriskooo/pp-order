package sk.pazurik.customerservice.domain.product;

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
public class ProductServiceImplTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private Logger logger;

    @Mock
    private ProductRepository repository;

    @Test
    public void getAllProductShouldReturnCollection() {
        // tu povieme, co urobi mockovane repository, ked sa zavola
        Mockito.when(repository.getAllProducts()).thenReturn(Collections.singletonList(TestProduct.PRODUCT_1_ENTITY()));

        // tu zavolame funkcionalitu
        Collection<ProductDTO> allProducts = productService.getAllProducts();

        // overime, ci sa zavolal repo 1x
        Mockito.verify(repository, Mockito.times(1)).getAllProducts();

        // overime, ci sa id vysledku = id entity, ktory vratil mock
        assertThat(allProducts.iterator().next().getId()).isEqualTo(TestProduct.PRODUCT_1_ENTITY().getId());
    }

    @Test
    public void getProductByIdShouldReturnProduct() {
        // tu povieme, co urobi mockovane repository, ked sa zavola
        Mockito.when(repository.getProductById(anyLong())).thenReturn(TestProduct.PRODUCT_1_ENTITY());

        // tu zavolame funkcionalitu
        ProductDTO product = productService.getProductById(TestProduct.PRODUCT_1_ENTITY().getId());

        // overime, ci sa zavolal repo 1x
        Mockito.verify(repository, Mockito.times(1)).getProductById(anyLong());

        // overime, ci sa id vysledku = id entity, ktory vratil mock
        assertThat(product.getId()).isEqualTo(TestProduct.PRODUCT_1_ENTITY().getId());
    }

    @Test
    public void getProductByIdWhenNotFoundShouldThrowException() {
        // ocakaveme vynimku
        thrown.expect(EntityNotFoundException.class);

        // tu povieme, co urobi mockovane repository, ked sa zavola
        Mockito.when(repository.getProductById(anyLong())).thenReturn(null);

        // tu zavolame funkcionalitu
        ProductDTO product = productService.getProductById(777l);

        // overime, ci sa zavolal repo 1x
        Mockito.verify(repository, Mockito.times(1)).getProductById(anyLong());
    }

    @Test
    public void saveProduct() {
        // tu zavolame funkcionalitu
        productService.saveProduct(TestProduct.PRODUCT_1_DTO());

        // overime, ci sa zavolal repo 1x
        Mockito.verify(repository, Mockito.times(1)).saveOrUpdateProduct(any());
    }

// todo: update, delete, ten boolean neprezijem

}