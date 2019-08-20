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
import sk.pazurik.customerservice.domain.customer.CustomerEntity;
import sk.pazurik.customerservice.domain.order.OrderEntity;
import sk.pazurik.customerservice.infrastructure.log.LoggerProducer;
import sk.pazurik.customerservice.infrastructure.persistence.EntityManagerProducer;

import javax.inject.Inject;
import java.net.URL;

import static org.junit.Assert.assertNotNull;

@RunWith(Arquillian.class)
public class ProductRepositoryTest {

    @Inject
    ProductRepository productRepository;

    @ArquillianResource
    private URL base;

    @Deployment
    public static WebArchive createDeployment() {
        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addAsLibraries(pomFile.resolve("org.slf4j:slf4j-simple").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.apache.deltaspike.core:deltaspike-core-api").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.apache.deltaspike.core:deltaspike-core-impl").withTransitivity().asFile())
                .addClass(ProductRepository.class)
                .addClasses(ProductEntity.class, CustomerEntity.class, OrderEntity.class)
                .addClasses(LoggerProducer.class, EntityManagerProducer.class)
                .addAsResource("persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void greet() {
        ProductEntity entity = new ProductEntity();
        productRepository.saveProduct(entity);
        assertNotNull(entity.getId());
    }
}
