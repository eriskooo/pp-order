package sk.pazurik.customerservice.domain.order;

import org.slf4j.Logger;
import sk.pazurik.customerservice.domain.customer.CustomerEntity;
import sk.pazurik.customerservice.infrastructure.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

@Repository
public class OrderRepository {
    @Inject
    private EntityManager entityManager;

    @Inject
    private Logger logger;

    public List<OrderEntity> getAllOrders(Long customerId) {
        CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, customerId);
        if (customerEntity == null) {
            throw new EntityNotFoundException("Customer not found");
        }
        return entityManager.createNamedQuery(OrderEntity.GET_ALL_ORDERS, OrderEntity.class).setParameter("customer", customerEntity).getResultList();
    }

    public List<OrderEntity> getOrdersByMinPrice(Long customerId, BigDecimal minPrice) {
        CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, customerId);
        if (customerEntity == null) {
            throw new EntityNotFoundException("Customer not found");
        }
        List<OrderEntity> orderEntities = entityManager.createNamedQuery(OrderEntity.GET_ORDERS_BY_MIN_PRICE, OrderEntity.class).setParameter("customer", customerEntity).setParameter("minPrice", minPrice).getResultList();
        if (orderEntities == null || orderEntities.isEmpty()) {
            throw new EntityNotFoundException("Order not found");
        }
        return orderEntities;
    }

    public OrderEntity getOrderById(Long customerId, Long id) {
        CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, customerId);
        if (customerEntity == null) {
            throw new EntityNotFoundException("Customer not found");
        }
        try {
            return entityManager.createNamedQuery(OrderEntity.GET_ORDERS_BY_ID, OrderEntity.class).setParameter("customer", customerEntity).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("Order not found");            
        }
    }

    public void saveOrUpdateOrder(Long customerId, OrderEntity orderEntity) {
        CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, customerId);
        if (customerEntity == null) {
            throw new EntityNotFoundException("Customer not found");
        }
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

    public void deleteOrder(Long customerId, Long orderId) {
        CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, customerId);
        if (customerEntity == null) {
            throw new EntityNotFoundException("Customer not found");
        }
        Optional<OrderEntity> orderEntity = customerEntity.getOrders().stream().filter(a -> a.getId().equals(orderId)).findAny();
        if (!orderEntity.isPresent()) {
            throw new EntityNotFoundException("Order not found");
        }
        customerEntity.getOrders().remove(orderEntity.get());
        entityManager.merge(customerEntity);
        logger.info("persisted, {}", customerEntity);
    }
}