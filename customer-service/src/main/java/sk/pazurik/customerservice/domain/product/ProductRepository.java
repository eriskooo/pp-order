package sk.pazurik.customerservice.domain.product;

import sk.pazurik.customerservice.infrastructure.stereotype.Repository;

import java.util.Collection;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Repository
public class ProductRepository {

    @Inject
    EntityManager entityManager;

    public Collection<ProductEntity> getAllProducts() {
        return null;
    }

    public ProductEntity getProductById() {
        return null;
    }
    
    public void saveProduct(ProductEntity product) {
        if (product.getId() == null) {
            entityManager.persist(product);
        } else {
            entityManager.merge(product);
        }
    }
}