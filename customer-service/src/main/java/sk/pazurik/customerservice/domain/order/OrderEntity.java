package sk.pazurik.customerservice.domain.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import sk.pazurik.customerservice.domain.product.ProductEntity;
import sk.pazurik.customerservice.infrastructure.entity.AbstractEntity;

@Entity
@NamedQuery(name = OrderEntity.GET_ORDERS, query = "select o from OrderEntity o where o.price_wo_VAT > :greaterThanValue")
public class OrderEntity extends AbstractEntity<Long> {
    public static final String GET_ORDERS = "GetOrders";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDate orderDate;
    
    private BigDecimal price_wo_VAT;

    private BigDecimal price_w_VAT;
    
    @OneToMany
    private Collection<ProductEntity> products = new ArrayList<>();
    
    public OrderEntity() {
        super();
    }
    
    public OrderEntity(OrderDTO dto) {
        id = dto.getId();
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

    public Collection<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(Collection<ProductEntity> products) {
        this.products = products;
    }
}