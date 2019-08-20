package sk.pazurik.customerservice.domain.order;

import java.math.BigDecimal;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import sk.pazurik.customerservice.infrastructure.stereotype.Repository;

@Repository
public class OrderRepository {
    @Inject
    private EntityManager entityManager;

    @Inject
    private Logger logger;

    public List<OrderEntity> getAllOrders() {
        return entityManager.createNamedQuery(OrderEntity.GET_ALL_ORDERS, OrderEntity.class).getResultList();
    }
    
    public List<OrderEntity> getOrders(BigDecimal minPrice) {
        return entityManager.createNamedQuery(OrderEntity.GET_ORDERS, OrderEntity.class).setParameter("minPrice", minPrice).getResultList();
    }

    public OrderEntity getOrderById(Long id) {
        return entityManager.find(OrderEntity.class, id);
    }

    public void saveOrder(OrderEntity order) {
        if (order.getId() == null) {
            entityManager.persist(order);
            logger.info("persisted, {}" , order);
        } else {
            entityManager.merge(order);
            logger.info("merged, {}" ,order);
        }
    }

    public boolean updateOrder(OrderEntity order) {
        if (order.getId() != null) {
            OrderEntity orderEntity = entityManager.find(OrderEntity.class, order.getId());
            if (orderEntity != null) {
                entityManager.merge(order);
                return true;
            }
        }
        return false;
    }

    public boolean deleteOrder(Long id) {
        OrderEntity orderEntity = entityManager.find(OrderEntity.class, id);
        if (orderEntity == null) {
            return false;
        } else {
            entityManager.remove(orderEntity);
            return true;
        }
    }
}