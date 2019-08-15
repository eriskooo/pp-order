package sk.pazurik.customerservice.domain.product;

import java.util.Collection;

// servisna vrstva len transformujeEntity na DTO
public interface ProductService {

    Collection<ProductDTO> getAllProducts();

    ProductDTO getProductById(Long id);
    
    void saveProduct(ProductDTO product);
}
