package sk.pazurik.customerservice.domain.order;

import org.slf4j.Logger;
import sk.pazurik.customerservice.domain.customer.CustomerEntity;
import sk.pazurik.customerservice.infrastructure.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class OrderRepository {
    @Inject
    private EntityManager entityManager;

    @Inject
    private Logger logger;

    public List<OrderEntity> getAllOrders(Long customerId) {
        CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, customerId);
        return entityManager.createNamedQuery(OrderEntity.GET_ALL_ORDERS, OrderEntity.class).setParameter("customer", customerEntity).getResultList();
    }

    public List<OrderEntity> getOrders(Long customerId, BigDecimal minPrice) {
        CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, customerId);
        return entityManager.createNamedQuery(OrderEntity.GET_ORDERS_BY_MIN_PRICE, OrderEntity.class).setParameter("customer", customerEntity).setParameter("minPrice", minPrice).getResultList();
    }

    public OrderEntity getOrderById(Long customerId, Long id) {
        CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, customerId);
        return entityManager.createNamedQuery(OrderEntity.GET_ORDERS_BY_ID, OrderEntity.class).setParameter("customer", customerEntity).setParameter("id", customerId).getSingleResult();
    }

    public void saveOrUpdateOrder(Long customerId, OrderEntity orderEntity) {
        CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, customerId);
        orderEntity.setCustomer(customerEntity);

        if (orderEntity.getId() == null) {
            customerEntity.getOrders().add(orderEntity);
            entityManager.persist(customerEntity);
            logger.info("persisted, {}", customerEntity);
        } else {
            entityManager.merge(orderEntity);
            logger.info("merged, {}", orderEntity);
        }
    }

    public void deleteOrder(Long customerId, OrderEntity orderEntity) {
        CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, customerId);
        customerEntity.getOrders().remove(orderEntity);
        entityManager.merge(customerEntity);
        logger.info("persisted, {}", customerEntity);
    }
}