package sk.pazurik.customerservice.domain.customer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import javax.persistence.*;
import sk.pazurik.customerservice.domain.order.OrderEntity;
import sk.pazurik.customerservice.infrastructure.entity.AbstractEntity;

@Entity
@NamedQuery(name = CustomerEntity.GET_ALL_CUSTOMERS, query = "select c from CustomerEntity c")
public class CustomerEntity extends AbstractEntity<Long> {

    public static final String GET_ALL_CUSTOMERS = "GetAllCustomers";
        
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;

    private String surname;

    private String phone;
    
    private String email;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] photo;
    
    @OneToMany(cascade = CascadeType.ALL)
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
        photo = dto.getPhoto();
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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
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
                ", photo=" + Arrays.toString(photo) +
                '}';
    }
}
