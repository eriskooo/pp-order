package sk.pazurik.customerservice.domain.product;

import java.util.Collection;

public interface ProductService {

    Collection<ProductDTO> getAllProducts();

    ProductDTO getProductById(Long id);
    
    void saveProduct(ProductDTO product);
    
    void updateProduct(ProductDTO product);
    
    void deleteProduct(Long id);
}
