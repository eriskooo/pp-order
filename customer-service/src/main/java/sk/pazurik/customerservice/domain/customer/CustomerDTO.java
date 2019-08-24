package sk.pazurik.customerservice.domain.customer;

import sk.pazurik.customerservice.domain.file.FileDTO;
import sk.pazurik.customerservice.domain.order.OrderDTO;
import sk.pazurik.customerservice.infrastructure.value.AbstractValueObject;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class CustomerDTO extends AbstractValueObject {

    private Long id;

    @NotEmpty(message = "Name must have at least one character")
    private String name;
    
    @NotEmpty(message = "Surname must have at least one character")
    private String surname;

    @Pattern(regexp="^\\+(?:[0-9] ?){6,14}[0-9]$", message="Please provide a valid phone number")
    private String phone;
    
    @Email(message="Please provide a valid email address")
    private String email;
    
    private FileDTO photo;

    private Collection<OrderDTO> orders = new ArrayList<>();
    
    public CustomerDTO() {
        super();
    }
    
    public CustomerDTO(CustomerEntity entity) {
        id = entity.getId();
        name = entity.getName();
        surname = entity.getSurname();
        phone = entity.getPhone();
        email = entity.getEmail();
        orders = entity.getOrders().stream().map(OrderDTO::new).collect(Collectors.toList());
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

    public FileDTO getPhoto() {
        return photo;
    }

    public void setPhoto(FileDTO photo) {
        this.photo = photo;
    }
    
    public Collection<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(Collection<OrderDTO> orders) {
        this.orders = orders;
    }

    @Override
    protected Object[] values() {
        return new Object[]{id, name, surname, phone, email};
    }
    
    @Override
    public String toString() {
        return "CustomerDTO{" + 
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    } 
}
