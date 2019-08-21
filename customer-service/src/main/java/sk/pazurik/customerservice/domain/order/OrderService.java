package sk.pazurik.customerservice.domain.order;

import java.math.BigDecimal;
import java.util.Collection;

public interface OrderService {
    Collection<OrderDTO> getAllOrders(Long customerId);
    
    Collection<OrderDTO> getOrdersByMinPrice(Long customerId, BigDecimal minPrice);

    OrderDTO getOrderById(Long customerId, Long id);
    
    void saveOrder(Long customerId, OrderDTO order);
    
    void updateOrder(Long customerId, OrderDTO order);
    
    void deleteOrder(Long customerId, Long id);
}
