package sk.pazurik.customerservice.domain.product;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.Test;
import org.junit.runner.RunWith;
import sk.pazurik.customerservice.application.Startup;
import sk.pazurik.customerservice.domain.customer.*;
import sk.pazurik.customerservice.domain.order.*;
import sk.pazurik.customerservice.infrastructure.log.LoggerProducer;
import sk.pazurik.customerservice.infrastructure.persistence.EntityManagerProducer;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Arquillian.class)
public class ProductRepositoryTest {

    @Inject
    ProductService productService;

    @Inject
    CustomerService customerServce;

    @Inject
    OrderService orderService;

    @ArquillianResource
    private URL base;

    @Deployment
    public static WebArchive createDeployment() {
        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addAsLibraries(pomFile.resolve("org.slf4j:slf4j-simple").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.apache.deltaspike.core:deltaspike-core-api").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.apache.deltaspike.core:deltaspike-core-impl").withTransitivity().asFile())
                .addClasses(OrderProductQuantityDTO.class, CustomerDTO.class, OrderDTO.class, ProductDTO.class)
                .addClasses(ProductService.class, CustomerService.class, OrderService.class)
                .addClass(Startup.class)
                .addClasses(ProductServiceImpl.class, CustomerServiceImpl.class, OrderServiceImpl.class)
                .addClasses(ProductRepository.class, CustomerRepository.class, OrderRepository.class)
                .addClasses(ProductEntity.class, CustomerEntity.class, OrderEntity.class)
                .addClasses(LoggerProducer.class, EntityManagerProducer.class)
                .addAsResource("persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void addOrderToCustomerFromStartup() {
        Collection<ProductDTO> allProducts = productService.getAllProducts();
        assertThat(allProducts.size()).isEqualTo(4);

        Collection<CustomerDTO> allCustomers = customerServce.getAllCustomers();
        assertThat(allCustomers.size()).isEqualTo(1);

        CustomerDTO customerDTO = allCustomers.iterator().next();

        assertThat(customerServce.getCustomerById(customerDTO.getId()).getOrders().size()).isEqualTo(0);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderDate(LocalDate.MIN);
        orderDTO.setPrice_w_VAT(BigDecimal.ONE);
        orderDTO.setPrice_wo_VAT(BigDecimal.TEN);
        orderDTO.setProducts(allProducts.stream().map(a -> new OrderProductQuantityDTO(a.getId(), 1l)).collect(Collectors.toList()));

        orderService.saveOrder(customerDTO.getId(), orderDTO);

        assertThat(customerServce.getCustomerById(customerDTO.getId()).getOrders().size()).isEqualTo(1);

        Long orderId = customerServce.getCustomerById(customerDTO.getId()).getOrders().iterator().next().getId();

        orderService.deleteOrder(customerDTO.getId(), orderId);

        assertThat(customerServce.getCustomerById(customerDTO.getId()).getOrders().size()).isEqualTo(0);

//        assertThat(orderService.getOrderById(customerDTO.getId(), orderId)).isNull();
    }
}
