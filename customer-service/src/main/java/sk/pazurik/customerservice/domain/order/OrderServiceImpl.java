package sk.pazurik.customerservice.domain.order;

import org.slf4j.Logger;
import sk.pazurik.customerservice.domain.customer.CustomerRepository;
import sk.pazurik.customerservice.domain.product.ProductEntity;
import sk.pazurik.customerservice.domain.product.ProductRepository;
import sk.pazurik.customerservice.infrastructure.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Inject
    private Logger logger;

    @Inject
    OrderRepository orderRepository;

    @Inject
    ProductRepository productRepository;

    @Inject
    CustomerRepository customerRepository;

    @Override
    public Collection<OrderDTO> getAllOrders(Long customerId) {
        return orderRepository.getAllOrders(customerId).stream().map(OrderDTO::new).collect(Collectors.toList());
    }

    @Override
    public Collection<OrderDTO> getOrdersByMinPrice(Long customerId, BigDecimal minPrice) {
        List<OrderEntity> orderEntities = orderRepository.getOrders(customerId, minPrice);
        if (orderEntities == null || orderEntities.isEmpty()) {
            throw new EntityNotFoundException("Order not found");
        } else {
            return orderEntities.stream().map(OrderDTO::new).collect(Collectors.toList());
        }
    }

    @Override
    public OrderDTO getOrderById(Long customerId, Long orderId) throws EntityNotFoundException {
        OrderEntity orderEntity = orderRepository.getOrderById(customerId, orderId);
        if (orderEntity == null) {
            throw new EntityNotFoundException("Order not found");
        } else {
            return new OrderDTO(orderEntity);
        }
    }

    @Override
    public void saveOrder(Long customerId, OrderDTO orderDTO) throws EntityNotFoundException {
        OrderEntity orderEntity = new OrderEntity(orderDTO);
        for (OrderProductQuantityDTO quantityDTO : orderDTO.getProducts()) {
            ProductEntity productEntity = productRepository.getProductById(quantityDTO.getProductId());
            if (productEntity == null) {
                throw new EntityNotFoundException("Product not found");
            } else {
                orderEntity.getProducts().put(productEntity, quantityDTO.getQuantity());
            }
        }
        orderRepository.saveOrUpdateOrder(customerId, orderEntity);
        logger.info("saveOrUpdateOrder ok, {}", orderEntity);
    }

    @Override
    public void updateOrder(Long customerId, OrderDTO orderDTO) {
        Optional<OrderEntity> orderEntity = customerRepository.getCustomerById(customerId).getOrders().stream().filter(a -> a.getId().equals(orderDTO.getId())).findAny();
        if (!orderEntity.isPresent()) {
            throw new EntityNotFoundException("Order not found");
        }

        for (OrderProductQuantityDTO quantityDTO : orderDTO.getProducts()) {
            ProductEntity productEntity = productRepository.getProductById(quantityDTO.getProductId());
            if (productEntity == null) {
                throw new EntityNotFoundException("Product not found");
            } else {
                orderEntity.get().getProducts().put(productEntity, quantityDTO.getQuantity());
            }
            orderRepository.saveOrUpdateOrder(customerId, orderEntity.get());
        }
        logger.info("updateOrder ok, {}", orderEntity);
    }

    @Override
    public void deleteOrder(Long customerId, Long orderId) throws EntityNotFoundException {
        Optional<OrderEntity> orderEntity = customerRepository.getCustomerById(customerId).getOrders().stream().filter(a -> a.getId().equals(orderId)).findAny();
        if (!orderEntity.isPresent()) {
            throw new EntityNotFoundException("Order not found");
        }

        orderRepository.deleteOrder(customerId, orderEntity.get());

        logger.info("deleteOrder ok, {}", orderId);
    }
}
