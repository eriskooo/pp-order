package sk.pazurik.customerservice.domain.customer;

import sk.pazurik.customerservice.domain.order.OrderEntity;
import sk.pazurik.customerservice.infrastructure.entity.AbstractEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Entity
@NamedQuery(name = CustomerEntity.GET_ALL_CUSTOMERS, query = "select c from CustomerEntity c")
@NamedQuery(name = CustomerEntity.GET_CUSTOMERS_BY_NAME, query = "select c from CustomerEntity c where lower(c.name) like CONCAT('%',:name,'%')")
public class CustomerEntity extends AbstractEntity<Long> {

    public static final String GET_ALL_CUSTOMERS = "GetAllCustomers";
    public static final String GET_CUSTOMERS_BY_NAME = "GetCustomersByName";
        
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;

    private String surname;

    private String phone;
    
    private String email;

    private Long pictureId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<OrderEntity> orders = new ArrayList<>();
    
    public CustomerEntity() {
        super();
    }
    
    public CustomerEntity(CustomerDTO dto) {
        id = dto.getId();
        name = dto.getName();
        surname = dto.getSurname();
        phone = dto.getPhone();
        email = dto.getEmail();
        pictureId = dto.getPictureId();
        orders = dto.getOrders().stream().map(OrderEntity::new).collect(Collectors.toList());
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPictureId() {
        return pictureId;
    }

    public void setPictureId(Long pictureId) {
        this.pictureId = pictureId;
    }

    public Collection<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(Collection<OrderEntity> orders) {
        this.orders = orders;
    }
    
    @Override
    public String toString() {
        return "CustomerEntity{" + 
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
