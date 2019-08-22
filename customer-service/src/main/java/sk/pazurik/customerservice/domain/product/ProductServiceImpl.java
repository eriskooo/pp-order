package sk.pazurik.customerservice.domain.product;

import org.slf4j.Logger;
import sk.pazurik.customerservice.infrastructure.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Inject
    private Logger logger;

    @Inject
    ProductRepository repository;

    @Override
    public Collection<ProductDTO> getAllProducts() {
        return repository.getAllProducts().stream().map(ProductDTO::new).collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) throws EntityNotFoundException {
        ProductEntity productEntity = repository.getProductById(id);
        if (productEntity == null) {
            throw new EntityNotFoundException("Product not found");
        }
        return new ProductDTO(productEntity);
    }

    @Override
    public void saveProduct(ProductDTO productDTO) {
        ProductEntity productEntity = new ProductEntity(productDTO);
        repository.saveOrUpdateProduct(productEntity);
        logger.info("saveProduct ok, {}", productEntity);
    }
    
    @Override
    public void updateProduct(ProductDTO productDTO) {
        ProductEntity productEntity = repository.getProductById(productDTO.getId());
        if (productEntity == null) {
            throw new EntityNotFoundException("Product not found");
        }
        productEntity = new ProductEntity(productDTO);
        repository.saveOrUpdateProduct(productEntity);
        logger.info("updateProduct ok, {}", productEntity);
    }
    
    @Override
    public void deleteProduct(Long id) throws EntityNotFoundException {
        repository.deleteProduct(id);
        logger.info("deleteProduct ok, {}", id);
    }
}
