package sk.pazurik.customerservice.domain.product;

import sk.pazurik.customerservice.infrastructure.entity.AbstractEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Arrays;

@Entity
@NamedQuery(name = ProductEntity.GET_ALL_PRODUCTS, query = "select p from ProductEntity p")
public class ProductEntity extends AbstractEntity<Long> {

    public static final String GET_ALL_PRODUCTS = "GetAllProducts";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private BigDecimal price_wo_VAT;

    private BigDecimal price_w_VAT;

    private String ean;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] picture;

    public ProductEntity() {
        super();
    }
    
    public ProductEntity(ProductDTO dto) {
        id = dto.getId();
        name = dto.getName();
        description = dto.getDescription();
        price_wo_VAT = dto.getPrice_wo_VAT();
        price_w_VAT = dto.getPrice_w_VAT();
        ean = dto.getEan();
        picture = dto.getPicture();
    }

    @Override
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
    public String toString() {
        return "ProductEntity{" +
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
