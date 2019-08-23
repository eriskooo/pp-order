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
        Mockito.when(repository.getAllProducts()).thenReturn(Collections.singletonList(TestProduct.PRODUCT_1_ENTITY()));
        Collection<ProductDTO> allProducts = productService.getAllProducts();
        Mockito.verify(repository, Mockito.times(1)).getAllProducts();
        assertThat(allProducts.iterator().next().getId()).isEqualTo(TestProduct.PRODUCT_1_ENTITY().getId());
    }
    
    @Test
    public void getProductsByNameShouldReturnCollection() {
        Mockito.when(repository.getProductsByName(Mockito.anyString())).thenReturn(Collections.singletonList(TestProduct.PRODUCT_1_ENTITY()));
        Collection<ProductDTO> products = productService.getProductsByName(Mockito.anyString());
        Mockito.verify(repository, Mockito.times(1)).getProductsByName(Mockito.anyString());
        assertThat(products.iterator().next().getId()).isEqualTo(TestProduct.PRODUCT_1_ENTITY().getId());
    }
    
    @Test
    public void getProductsByNameWhenNotFoundShouldReturnCollection() {
        thrown.expect(EntityNotFoundException.class);
        Mockito.doThrow(EntityNotFoundException.class).when(repository).getProductsByName(Mockito.anyString());
        Collection<ProductDTO> products = productService.getProductsByName(Mockito.anyString());
        Mockito.verify(repository, Mockito.times(1)).getProductsByName(Mockito.anyString());
    }

    @Test
    public void getProductByIdShouldReturnProduct() {
        Mockito.when(repository.getProductById(anyLong())).thenReturn(TestProduct.PRODUCT_1_ENTITY());
        ProductDTO product = productService.getProductById(TestProduct.PRODUCT_1_ENTITY().getId());
        Mockito.verify(repository, Mockito.times(1)).getProductById(anyLong());
        assertThat(product.getId()).isEqualTo(TestProduct.PRODUCT_1_ENTITY().getId());
    }

    @Test
    public void getProductByIdWhenNotFoundShouldThrowException() {
        thrown.expect(EntityNotFoundException.class);
        Mockito.when(repository.getProductById(anyLong())).thenReturn(null);
        ProductDTO product = productService.getProductById(777l);
        Mockito.verify(repository, Mockito.times(1)).getProductById(anyLong());
    }

    @Test
    public void saveProduct() {
        productService.saveProduct(TestProduct.PRODUCT_1_DTO());
        Mockito.verify(repository, Mockito.times(1)).saveOrUpdateProduct(any());
    }
    
    @Test
    public void updateProduct() {
        Mockito.when(repository.getProductById(anyLong())).thenReturn(TestProduct.PRODUCT_1_ENTITY());
        productService.updateProduct(TestProduct.PRODUCT_1_DTO());
        Mockito.verify(repository, Mockito.times(1)).getProductById(anyLong());
        Mockito.verify(repository, Mockito.times(1)).saveOrUpdateProduct(any());
    }

    @Test
    public void updateProductWhenNotFoundShouldThrowException() {
        thrown.expect(EntityNotFoundException.class);
        Mockito.when(repository.getProductById(anyLong())).thenReturn(null);
        productService.updateProduct(TestProduct.PRODUCT_1_DTO());
        Mockito.verify(repository, Mockito.times(1)).getProductById(anyLong());
    }
    
    @Test
    public void deleteProduct() {
        productService.deleteProduct(anyLong());
        Mockito.verify(repository, Mockito.times(1)).deleteProduct(anyLong());
    }

    @Test
    public void deleteProductWhenNotFoundShouldThrowException() {
        thrown.expect(EntityNotFoundException.class);
        Mockito.doThrow(EntityNotFoundException.class).when(repository).deleteProduct(anyLong());
        productService.deleteProduct(anyLong());
        Mockito.verify(repository, Mockito.times(1)).deleteProduct(anyLong());        
    }
}