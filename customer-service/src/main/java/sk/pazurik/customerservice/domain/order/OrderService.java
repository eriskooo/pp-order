package sk.pazurik.customerservice.domain.order;

import java.math.BigDecimal;
import java.util.Collection;

public interface OrderService {
    Collection<OrderDTO> getOrders(BigDecimal minPrice);

    OrderDTO getOrderById(Long id);
    
    void saveOrder(OrderDTO order);
    
    void updateOrder(OrderDTO order);
    
    void deleteOrder(Long id);
}
