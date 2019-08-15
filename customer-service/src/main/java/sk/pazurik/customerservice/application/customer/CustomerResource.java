package sk.pazurik.customerservice.application.customer;

import java.util.Collection;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import sk.pazurik.customerservice.domain.customer.CustomerDTO;
import sk.pazurik.customerservice.domain.customer.CustomerService;
import sk.pazurik.customerservice.domain.product.ProductDTO;

@Path("customer")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class CustomerResource {
    
    @Inject
    private Logger logger;

    @Inject
    private CustomerService customerService;

    @GET
    public Response getAllCustomers() {
        logger.info("called getAllCustomers");

        Collection<CustomerDTO> customers = customerService.getAllCustomers();

        return Response.ok(customers).status(Response.Status.OK).build();
    }

    @POST
    public Response createCustomer(@Valid CustomerDTO customer) {
        logger.info("called createCustomer");

        customerService.saveCustomer(customer);

        return Response.ok(customer.getId()).status(Response.Status.CREATED).build();
    }
}
