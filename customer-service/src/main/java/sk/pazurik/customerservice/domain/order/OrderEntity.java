package sk.pazurik.customerservice.domain.order;

import sk.pazurik.customerservice.domain.customer.CustomerEntity;
import sk.pazurik.customerservice.domain.product.ProductEntity;
import sk.pazurik.customerservice.infrastructure.entity.AbstractEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Entity
@NamedQuery(name = OrderEntity.GET_ALL_ORDERS, query = "select o from OrderEntity o where o.customer = :customer")
@NamedQuery(name = OrderEntity.GET_ORDERS_BY_MIN_PRICE, query = "select o from OrderEntity o where o.customer = :customer and o.price_wo_VAT > :minPrice")
@NamedQuery(name = OrderEntity.GET_ORDERS_BY_ID, query = "select o from OrderEntity o where o.customer = :customer and o.id = :id")
public class OrderEntity extends AbstractEntity<Long> {
    
    public static final String GET_ALL_ORDERS = "GetAllOrders";
    public static final String GET_ORDERS_BY_MIN_PRICE = "GetOrders";
    public static final String GET_ORDERS_BY_ID = "GetOrdersById";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDate orderDate;
    
    private BigDecimal price_wo_VAT;

    private BigDecimal price_w_VAT;

    @OneToOne
    private CustomerEntity customer; 
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    @MapKey(name = "id")
    @ElementCollection
    private Map<ProductEntity, Long> products = new HashMap<>();
    
    public OrderEntity() {
        super();
    }
    
    public OrderEntity(OrderDTO dto) {
        id = dto.getId();
        orderDate = dto.getOrderDate();
        price_wo_VAT = dto.getPrice_wo_VAT();
        price_w_VAT = dto.getPrice_w_VAT();
        for (OrderProductQuantityDTO quantityDTO : dto.getProducts()) {
            products.put(new ProductEntity(quantityDTO.getProductId()), quantityDTO.getQuantity());
        }
    }
    
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getPrice_wo_VAT() {
        return price_wo_VAT;
    }

    public void setPrice_wo_VAT(BigDecimal price_wo_VAT) {
        this.price_wo_VAT = price_wo_VAT;
    }

    public BigDecimal getPrice_w_VAT() {
        return price_w_VAT;
    }

    public void setPrice_w_VAT(BigDecimal price_w_VAT) {
        this.price_w_VAT = price_w_VAT;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public Map<ProductEntity, Long> getProducts() {
        return products;
    }

    public void setProducts(Map<ProductEntity, Long> products) {
        this.products = products;
    }
    
    @Override
    public String toString() {
        return "OrderEntity{" + 
                "id=" + id +
                ", orderDate='" + orderDate + '\'' +
                ", price_wo_VAT='" + price_wo_VAT + '\'' +
                ", price_w_VAT='" + price_w_VAT + '\'' +
                ", products='" + products.toString() + '\'' +
                '}';
    }
}