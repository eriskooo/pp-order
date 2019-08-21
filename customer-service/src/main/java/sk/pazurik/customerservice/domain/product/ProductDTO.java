package sk.pazurik.customerservice.domain.product;

import sk.pazurik.customerservice.infrastructure.value.AbstractValueObject;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Arrays;

public class ProductDTO extends AbstractValueObject {

    protected Long id;

    @NotEmpty(message = "Name must have at least one character")
    private String name;

    private String description;

    @DecimalMin(value = "0.01", message = "Price without VAT must be at least 0.01")
    @Digits(integer=6, fraction=2, message = "Price without VAT must have maximum 6 integral digits and maximum 2 fractional digits")
    private BigDecimal price_wo_VAT;

    @DecimalMin(value = "0.01", message = "Price with VAT must be at least 0.01")
    @Digits(integer=6, fraction=2, message = "Price without VAT must have maximum 6 integral digits and maximum 2 fractional digits")
    private BigDecimal price_w_VAT;
    
    @Size(min=13, max=13, message = "EAN must have exactly 13 characters")
    private String ean;
    
    private byte[] picture;
    
    public ProductDTO() {
        super();
    }
    
    public ProductDTO(ProductEntity entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        price_wo_VAT = entity.getPrice_wo_VAT();
        price_w_VAT = entity.getPrice_w_VAT();
        ean = entity.getEan();
        picture = entity.getPicture();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
    
    @Override
    protected Object[] values() {
        return new Object[]{id, name, description, price_wo_VAT, price_w_VAT, ean, picture};
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price_wo_VAT=" + price_wo_VAT +
                ", price_w_VAT=" + price_w_VAT +
                ", ean='" + ean + '\'' +
                ", picture=" + Arrays.toString(picture) +
                '}';
    }
}