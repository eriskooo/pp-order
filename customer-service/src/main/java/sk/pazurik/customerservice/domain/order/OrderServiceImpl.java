package sk.pazurik.customerservice.domain.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import sk.pazurik.customerservice.domain.customer.CustomerEntity;
import sk.pazurik.customerservice.domain.customer.CustomerRepository;
import sk.pazurik.customerservice.domain.product.ProductEntity;
import sk.pazurik.customerservice.domain.product.ProductRepository;
import sk.pazurik.customerservice.infrastructure.stereotype.Service;

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
        CustomerEntity customerEntity = customerRepository.getCustomerById(customerId);
        if (customerEntity == null) {
            throw new EntityNotFoundException("Customer not found");
        }
        return customerEntity.getOrders().stream().map(OrderDTO::new).collect(Collectors.toList());
    }
    
    @Override
    public Collection<OrderDTO> getOrdersByMinPrice(Long customerId, BigDecimal minPrice) {
        CustomerEntity customerEntity = customerRepository.getCustomerById(customerId);
        if (customerEntity == null) {
            throw new EntityNotFoundException("Customer not found");
        }
        Collection<OrderEntity> filteredOrders = new ArrayList<>();
        for (OrderEntity orderEntity : customerEntity.getOrders()) {
            if (orderEntity.getPrice_wo_VAT().compareTo(minPrice) == 1) {
                filteredOrders.add(orderEntity);
            }
        }
        if (filteredOrders.isEmpty()) {
            throw new EntityNotFoundException("Order not found");
        }
        return filteredOrders.stream().map(OrderDTO::new).collect(Collectors.toList());
    }

   @Override
    public OrderDTO getOrderById(Long customerId, Long orderId) throws EntityNotFoundException {
        CustomerEntity customerEntity = customerRepository.getCustomerById(customerId);
        if (customerEntity == null) {
            throw new EntityNotFoundException("Customer not found");
        }
        OrderEntity orderEntity = customerEntity.getOrders().stream().filter(a -> a.getId().equals(orderId)).findAny().get();
        if (orderEntity == null) {
            throw new EntityNotFoundException("Order not found");
        }
        return new OrderDTO(orderEntity);
    }
    
    @Override
    public void saveOrder(Long customerId, OrderDTO orderDTO) throws EntityNotFoundException {
        CustomerEntity customerEntity = customerRepository.getCustomerById(customerId);
        if (customerEntity == null) {
            throw new EntityNotFoundException("Customer not found");
        }
        OrderEntity orderEntity = new OrderEntity(orderDTO);
        for (OrderProductQuantityDTO quantityDTO : orderDTO.getProducts()) {
            ProductEntity productEntity = productRepository.getProductById(quantityDTO.getProductId());
            if (productEntity == null) {
                throw new EntityNotFoundException("Product not found");
            }
            orderEntity.getProducts().put(productEntity, quantityDTO.getQuantity());
        }
        orderRepository.saveOrUpdateOrder(customerEntity, orderEntity);
        logger.info("saveOrder ok, {}", orderEntity);
    }
    
    @Override
    public void updateOrder(Long customerId, OrderDTO orderDTO) {
        CustomerEntity customerEntity = customerRepository.getCustomerById(customerId);
        if (customerEntity == null) {
            throw new EntityNotFoundException("Customer not found");
        }
        OrderEntity orderEntity = customerEntity.getOrders().stream().filter(a -> a.getId().equals(orderDTO.getId())).findAny().get();
        if (orderEntity == null) {
            throw new EntityNotFoundException("Order not found");
        }
        for (OrderProductQuantityDTO quantityDTO : orderDTO.getProducts()) {
            ProductEntity productEntity = productRepository.getProductById(quantityDTO.getProductId());
            if (productEntity == null) {
                throw new EntityNotFoundException("Product not found");
            }
            orderEntity.getProducts().put(productEntity, quantityDTO.getQuantity());
        }
        orderRepository.saveOrUpdateOrder(customerEntity, orderEntity);
        logger.info("updateOrder ok, {}", orderEntity);
    }
    
    @Override
    public void deleteOrder(Long customerId, Long orderId) throws EntityNotFoundException {
        CustomerEntity customerEntity = customerRepository.getCustomerById(customerId);
        if (customerEntity == null) {
            throw new EntityNotFoundException("Customer not found");
        }
        OrderEntity orderEntity = customerEntity.getOrders().stream().filter(a -> a.getId().equals(orderId)).findAny().get();
        if (orderEntity == null) {
            throw new EntityNotFoundException("Order not found");
        }
        orderRepository.deleteOrder(customerEntity, orderEntity);
        logger.info("deleteOrder ok, {}", orderId);
    }
}
