package sk.pazurik.customerservice.domain.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import sk.pazurik.customerservice.infrastructure.value.AbstractValueObject;

public class OrderDTO extends AbstractValueObject {
    private Long id;
    
    @NotNull(message = "Date of order must be set")
    @Past(message = "Date of order must be in the past")
    @JsonbDateFormat(value = "yyyy-MM-dd")
    private LocalDate orderDate;

    @DecimalMin(value = "0.01", message = "Price without VAT must be at least 0.01")
    @Digits(integer=6, fraction=2, message = "Price without VAT must have maximum 6 integral digits and maximum 2 fractional digits")
    private BigDecimal price_wo_VAT;

    @DecimalMin(value = "0.01", message = "Price with VAT must be at least 0.01")
    @Digits(integer=6, fraction=2, message = "Price without VAT must have maximum 6 integral digits and maximum 2 fractional digits")
    private BigDecimal price_w_VAT;

    public OrderDTO() {
        super();
    }
    
    public OrderDTO(OrderEntity entity) {
        id = entity.getId();
        orderDate = entity.getOrderDate();
        price_wo_VAT = entity.getPrice_wo_VAT();
        price_w_VAT = entity.getPrice_w_VAT();
    }
    
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
    
    @Override
    protected Object[] values() {
        return new Object[]{id, orderDate, price_wo_VAT, price_w_VAT};
    }
    
    @Override
    public String toString() {
        return "OrderDTO{" + 
                "id=" + id +
                ", orderDate='" + orderDate + '\'' +
                ", price_wo_VAT='" + price_wo_VAT + '\'' +
                ", price_w_VAT='" + price_w_VAT + '\'' +
                '}';
    }
}