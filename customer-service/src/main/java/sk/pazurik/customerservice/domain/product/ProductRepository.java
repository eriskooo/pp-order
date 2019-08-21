package sk.pazurik.customerservice.domain.product;

import org.slf4j.Logger;
import sk.pazurik.customerservice.infrastructure.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collection;

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

    public void saveProduct(ProductEntity product) {
        if (product.getId() == null) {
            entityManager.persist(product);
            logger.info("persisted, {}" , product);
        } else {
            entityManager.merge(product);
            logger.info("merged, {}" ,product);
        }
    }
    
    public boolean updateProduct(ProductEntity product) {
        if (product.getId() != null) {
            ProductEntity productEntity = entityManager.find(ProductEntity.class, product.getId());
            if (productEntity != null) {
                entityManager.merge(product);
                return true;
            }
        }
        return false;
    }

    public boolean deleteProduct(Long id) {
        ProductEntity productEntity = entityManager.find(ProductEntity.class, id);
        if (productEntity == null) {
            return false;
        } else {
            entityManager.remove(productEntity);
            return true;
        }
    }
}