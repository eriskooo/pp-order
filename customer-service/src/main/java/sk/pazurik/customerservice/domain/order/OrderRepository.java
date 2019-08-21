package sk.pazurik.customerservice.domain.order;

import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import sk.pazurik.customerservice.domain.customer.CustomerEntity;
import sk.pazurik.customerservice.infrastructure.stereotype.Repository;

@Repository
public class OrderRepository {
    @Inject
    private EntityManager entityManager;

    @Inject
    private Logger logger;

    public List<OrderEntity> getAllOrders(CustomerEntity customerEntity) {
        return entityManager.createNamedQuery(OrderEntity.GET_ALL_ORDERS, OrderEntity.class).setParameter("customer", customerEntity).getResultList();
    }

    public void saveOrUpdateOrder(CustomerEntity customerEntity, OrderEntity orderEntity) {
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

    public void deleteOrder(CustomerEntity customerEntity, OrderEntity orderEntity) {
        customerEntity.getOrders().remove(orderEntity);
        entityManager.merge(customerEntity);
        logger.info("persisted, {}", customerEntity);
    }
}