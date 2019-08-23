package sk.pazurik.customerservice.domain.order;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import sk.pazurik.customerservice.domain.customer.CustomerRepository;
import sk.pazurik.customerservice.domain.customer.TestCustomer;
import sk.pazurik.customerservice.domain.product.ProductRepository;
import sk.pazurik.customerservice.domain.product.TestProduct;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private Logger logger;

    @Mock
    OrderRepository orderRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    CustomerRepository customerRepository;

    @Test
    public void getAllOrdersShouldReturnCollection() {
        Mockito.when(orderRepository.getAllOrders(anyLong())).thenReturn(Collections.singletonList(TestOrder.ORDER_1_ENTITY()));
        Collection<OrderDTO> allOrders = orderService.getAllOrders(anyLong());
        Mockito.verify(orderRepository, Mockito.times(1)).getAllOrders(anyLong());
        assertThat(allOrders.iterator().next().getId()).isEqualTo(TestOrder.ORDER_1_ENTITY().getId());
    }
    
    @Test
    public void getAllOrdersWhenCustomerNotFoundShouldThrowException() {
        thrown.expect(EntityNotFoundException.class);
        Mockito.doThrow(EntityNotFoundException.class).when(orderRepository).getAllOrders(anyLong());
        Collection<OrderDTO> allOrders = orderService.getAllOrders(anyLong());
        Mockito.verify(orderRepository, Mockito.times(1)).getAllOrders(anyLong());
    }

    @Test
    public void getOrdersByMinPriceShouldReturnCollection() {
        Mockito.when(orderRepository.getOrdersByMinPrice(anyLong(), any())).thenReturn(Collections.singletonList(TestOrder.ORDER_1_ENTITY()));
        Collection<OrderDTO> orders = orderService.getOrdersByMinPrice(anyLong(), any());
        Mockito.verify(orderRepository, Mockito.times(1)).getOrdersByMinPrice(anyLong(), any());
        assertThat(orders.iterator().next().getId()).isEqualTo(TestOrder.ORDER_1_ENTITY().getId());
    }
    
    @Test
    public void getOrdersByMinPriceWhenEntityNotFoundShouldThrowException() {
        thrown.expect(EntityNotFoundException.class);
        Mockito.doThrow(EntityNotFoundException.class).when(orderRepository).getOrdersByMinPrice(anyLong(), any());
        Collection<OrderDTO> orders = orderService.getOrdersByMinPrice(anyLong(), any());
        Mockito.verify(orderRepository, Mockito.times(1)).getOrdersByMinPrice(anyLong(), any());
    }
    
    @Test
    public void getOrderByIdShouldReturnCollection() {
        Mockito.when(orderRepository.getOrderById(anyLong(), anyLong())).thenReturn(TestOrder.ORDER_1_ENTITY());
        OrderDTO order = orderService.getOrderById(anyLong(), anyLong());
        Mockito.verify(orderRepository, Mockito.times(1)).getOrderById(anyLong(), anyLong());
        assertThat(order.getId()).isEqualTo(TestOrder.ORDER_1_ENTITY().getId());
    }
    
    @Test
    public void getOrderByIdWhenEntityNotFoundShouldThrowException() {
        thrown.expect(EntityNotFoundException.class);
        Mockito.doThrow(EntityNotFoundException.class).when(orderRepository).getOrderById(anyLong(), anyLong());
        OrderDTO order = orderService.getOrderById(anyLong(), anyLong());
        Mockito.verify(orderRepository, Mockito.times(1)).getOrderById(anyLong(), anyLong());
    }
    
    @Test
    public void saveOrder() {
        Mockito.when(productRepository.getProductById(anyLong())).thenReturn(TestProduct.PRODUCT_1_ENTITY());
        orderService.saveOrder(anyLong(), TestOrder.ORDER_1_DTO());
        Mockito.verify(productRepository, Mockito.times(1)).getProductById(anyLong());
        
        orderService.saveOrder(anyLong(), TestOrder.ORDER_1_DTO());
        Mockito.verify(orderRepository, Mockito.times(1)).saveOrUpdateOrder(anyLong(), TestOrder.ORDER_1_ENTITY());
    }
    
    @Test
    public void saveOrderWhenProductNotFoundShouldThrowException() {
        thrown.expect(EntityNotFoundException.class);
        Mockito.when(productRepository.getProductById(anyLong())).thenReturn(null);
        orderService.saveOrder(anyLong(), TestOrder.ORDER_1_DTO());
        Mockito.verify(productRepository, Mockito.times(1)).getProductById(anyLong());

        thrown.expect(EntityNotFoundException.class);
        Mockito.doThrow(EntityNotFoundException.class).when(orderRepository).saveOrUpdateOrder(anyLong(), TestOrder.ORDER_1_ENTITY());
        orderService.saveOrder(anyLong(), TestOrder.ORDER_1_DTO());
        Mockito.verify(orderRepository, Mockito.times(1)).saveOrUpdateOrder(anyLong(), TestOrder.ORDER_1_ENTITY());
    }

    @Test
    public void updateOrder() {
        Mockito.when(customerRepository.getCustomerById(anyLong())).thenReturn(TestCustomer.CUSTOMER_1_ENTITY());
        orderService.updateOrder(anyLong(), TestOrder.ORDER_1_DTO());
        Mockito.verify(customerRepository, Mockito.times(1)).getCustomerById(anyLong());
        
        Mockito.when(productRepository.getProductById(anyLong())).thenReturn(TestProduct.PRODUCT_1_ENTITY());
        orderService.updateOrder(anyLong(), TestOrder.ORDER_1_DTO());
        Mockito.verify(productRepository, Mockito.times(1)).getProductById(anyLong());
        
        orderService.updateOrder(anyLong(), TestOrder.ORDER_1_DTO());
        Mockito.verify(orderRepository, Mockito.times(1)).saveOrUpdateOrder(anyLong(), TestOrder.ORDER_1_ENTITY());
    }

    @Test
    public void updateOrderWhenCustomerNotFoundShouldThrowException() {
        thrown.expect(EntityNotFoundException.class);
        Mockito.when(customerRepository.getCustomerById(anyLong())).thenReturn(null);
        orderService.saveOrder(anyLong(), TestOrder.ORDER_1_DTO());
        Mockito.verify(customerRepository, Mockito.times(1)).getCustomerById(anyLong());
       
        thrown.expect(EntityNotFoundException.class);
        Mockito.when(productRepository.getProductById(anyLong())).thenReturn(null);
        orderService.saveOrder(anyLong(), TestOrder.ORDER_1_DTO());
        Mockito.verify(productRepository, Mockito.times(1)).getProductById(anyLong());
    }

    @Test
    public void deleteOrder() {
        orderService.deleteOrder(anyLong(), anyLong());
        Mockito.verify(orderRepository, Mockito.times(1)).deleteOrder(anyLong(), anyLong());
    }

    @Test
    public void deleteOrderWhenEntityNotFoundShouldThrowException() {
        thrown.expect(EntityNotFoundException.class);
        Mockito.doThrow(EntityNotFoundException.class).when(orderRepository).deleteOrder(anyLong(), anyLong());
        orderService.deleteOrder(anyLong(), anyLong());
        Mockito.verify(orderRepository, Mockito.times(1)).deleteOrder(anyLong(), anyLong());        
    }
}