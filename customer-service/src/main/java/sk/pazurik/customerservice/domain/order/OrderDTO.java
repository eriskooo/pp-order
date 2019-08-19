package sk.pazurik.customerservice.domain.order;

import sk.pazurik.customerservice.infrastructure.value.AbstractValueObject;

public class OrderDTO extends AbstractValueObject {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderDTO() {
        super();
    }
    
    public OrderDTO(OrderEntity entity) {
        id = entity.getId();
    }

    @Override
    protected Object[] values() {
        return new Object[]{id};
    }
    
    @Override
    public String toString() {
        return "OrderDTO{" + 
                "id=" +
                '}';
    }
}