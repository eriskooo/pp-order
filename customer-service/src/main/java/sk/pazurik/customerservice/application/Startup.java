package sk.pazurik.customerservice.application;

import org.slf4j.Logger;
import sk.pazurik.customerservice.domain.customer.CustomerDTO;
import sk.pazurik.customerservice.domain.customer.CustomerService;
import sk.pazurik.customerservice.domain.product.ProductDTO;
import sk.pazurik.customerservice.domain.product.ProductService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.stream.IntStream;

@ApplicationScoped
public class Startup {

    @Inject
    private Logger logger;

    @Inject
    private CustomerService customerService;

    @Inject
    private ProductService productService;

    @Transactional
    public void postConstruct(@Observes @Initialized(ApplicationScoped.class) Object o) {
        createCustomers();
        IntStream.range(1, 5).forEach(i -> createProducts(i));
    }

    private void createProducts(int i) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setDescription("desc " + i);
        productDTO.setEan("ean");
        productDTO.setName("name " + i);
        productDTO.setPrice_w_VAT(BigDecimal.TEN);
        productDTO.setPrice_wo_VAT(BigDecimal.ONE);
        productService.saveProduct(productDTO);

        logger.info("product created, {}", productDTO);
    }

    private void createCustomers() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setEmail("email");
        customerDTO.setName("name");
        customerDTO.setPhone("phone");
        customerDTO.setOrders(Collections.emptyList());
        customerDTO.setSurname("surname");
        customerService.saveCustomer(customerDTO);

        logger.info("customer created, {}", customerDTO);
    }
}