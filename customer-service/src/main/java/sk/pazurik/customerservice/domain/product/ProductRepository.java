package sk.pazurik.customerservice.domain.product;

import org.slf4j.Logger;
import sk.pazurik.customerservice.infrastructure.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collection;
import javax.persistence.EntityNotFoundException;

@Repository
public class ProductRepository {

    @Inject
    private EntityManager entityManager;

    @Inject
    private Logger logger;

    public Collection<ProductEntity> getAllProducts() {
        return entityManager.createNamedQuery(ProductEntity.GET_ALL_PRODUCTS, ProductEntity.class).getResultList();
    }

    public ProductEntity getProductById(Long id) {
        return entityManager.find(ProductEntity.class, id);
    }

    public void saveOrUpdateProduct(ProductEntity product) {
        if (product.getId() == null) {
            entityManager.persist(product);
            logger.info("persisted, {}" , product);
        } else {
            entityManager.merge(product);
            logger.info("merged, {}" ,product);
        }
    }
    
    public void deleteProduct(Long id) {
        ProductEntity product = entityManager.find(ProductEntity.class, id);
        if (product == null) {
            throw new EntityNotFoundException("Product not found");
        }
        entityManager.remove(product);
    }
}