package sk.pazurik.customerservice.domain.order;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import sk.pazurik.customerservice.domain.product.ProductEntity;
import sk.pazurik.customerservice.domain.product.ProductRepository;
import sk.pazurik.customerservice.infrastructure.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Inject
    private Logger logger;

    @Inject
    OrderRepository repository;
    
    @Inject
    ProductRepository productRepository;

    @Override
    public Collection<OrderDTO> getAllOrders() {
        return repository.getAllOrders().stream().map(OrderDTO::new).collect(Collectors.toList());
    }
    
    @Override
    public Collection<OrderDTO> getOrders(BigDecimal minPrice) {
        List<OrderEntity> orderEntities = repository.getOrders(minPrice);
        if (orderEntities == null || orderEntities.isEmpty()) {
            throw new EntityNotFoundException("Order not found");
        } else {
            return orderEntities.stream().map(OrderDTO::new).collect(Collectors.toList());
        }
    }

   @Override
    public OrderDTO getOrderById(Long id) throws EntityNotFoundException {
        OrderEntity orderEntity = repository.getOrderById(id);
        if (orderEntity == null) {
            throw new EntityNotFoundException("Order not found");
        } else {
            return new OrderDTO(orderEntity);
        }
    }
    
    @Override
    public void saveOrder(OrderDTO orderDTO) throws EntityNotFoundException {
        OrderEntity orderEntity = new OrderEntity(orderDTO);
        for (Long id : orderDTO.getProducts().keySet()) {
            ProductEntity productEntity = productRepository.getProductById(id);
            if (productEntity == null) {
                throw new EntityNotFoundException("Product not found");
            } else {
                orderEntity.getProducts().put(productEntity, orderDTO.getProducts().get(id));
            }
        }
        repository.saveOrder(orderEntity);
        logger.info("saveOrder ok, {}", orderEntity);
    }
    
    @Override
    public void updateOrder(OrderDTO orderDTO) {
        OrderEntity orderEntity = new OrderEntity(orderDTO);
        for (Long id : orderDTO.getProducts().keySet()) {
            ProductEntity productEntity = productRepository.getProductById(id);
            if (productEntity == null) {
                throw new EntityNotFoundException("Product not found");
            } else {
                orderEntity.getProducts().put(productEntity, orderDTO.getProducts().get(id));
            }
            if (!repository.updateOrder(orderEntity)) {
                throw new EntityNotFoundException("Order not found");
            }
        }
        logger.info("updateOrder ok, {}", orderEntity);
    }
    
    @Override
    public void deleteOrder(Long id) throws EntityNotFoundException {
        if (!repository.deleteOrder(id)) {
            throw new EntityNotFoundException("Order not found");
        }
        logger.info("deleteOrder ok, {}", id);
    }
}
