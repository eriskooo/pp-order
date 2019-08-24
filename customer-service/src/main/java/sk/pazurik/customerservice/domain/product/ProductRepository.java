package sk.pazurik.customerservice.domain.product;

import org.slf4j.Logger;
import sk.pazurik.customerservice.infrastructure.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;

@Repository
public class ProductRepository {

    @Inject
    private EntityManager entityManager;

    @Inject
    private Logger logger;

    public Collection<ProductEntity> getAllProducts() {
        return entityManager.createNamedQuery(ProductEntity.GET_ALL_PRODUCTS, ProductEntity.class).getResultList();
    }

    public List<ProductEntity> getProductsByName(String name) {
        List<ProductEntity> products = entityManager.createNamedQuery(ProductEntity.GET_PRODUCTS_BY_NAME, ProductEntity.class).setParameter("name", name).getResultList();
        if (products == null || products.isEmpty()) {
            throw new EntityNotFoundException("Product not found");
        }
        return products;
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