package sk.pazurik.customerservice.domain.order;

import javax.validation.constraints.NotNull;
import sk.pazurik.customerservice.infrastructure.value.AbstractValueObject;

public class OrderProductQuantityDTO extends AbstractValueObject {

    @NotNull
    private Long productId;

    @NotNull
    private Long quantity;

    public OrderProductQuantityDTO() {
        super();
    }
    
    public OrderProductQuantityDTO(Long productId, Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    
    
    @Override
    protected Object[] values() {
        return new Object[]{productId, quantity};
    }
    
    @Override
    public String toString() {
        return "OrderDTO{" + 
                "productId=" + productId +
                ", quantity='" + quantity + '\'' +
                '}';
    }   
}