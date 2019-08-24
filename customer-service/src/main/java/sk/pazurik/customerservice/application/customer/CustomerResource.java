package sk.pazurik.customerservice.application.customer;

import org.apache.deltaspike.core.util.StringUtils;
import org.slf4j.Logger;
import sk.pazurik.customerservice.domain.customer.CustomerDTO;
import sk.pazurik.customerservice.domain.customer.CustomerService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("customer")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class CustomerResource {
    
    @Inject
    private Logger logger;

    @Inject
    private CustomerService customerService;

    @GET
    public Response getAllCustomers(@QueryParam("name") String name) {
        Collection<CustomerDTO> customers;
        if (StringUtils.isEmpty(name)) {
            logger.info("called getAllCustomers");
            customers = customerService.getAllCustomers();
        } else {
            logger.info("called getCustomersByName, {}", name);
            customers = customerService.getCustomersByName(name);
        }
        return Response.ok(customers).status(Response.Status.OK).build();
    }
        
    @GET
    @Path("{id}")
    public Response getCustomerById(@PathParam("id") @NotNull Long id) {
        logger.info("called getCustomerById, {}", id);

        CustomerDTO dto = customerService.getCustomerById(id);

        return Response.ok(dto).status(Response.Status.OK).build();
    }
    
    @POST
    public Response createCustomer(@Valid CustomerDTO customer) {
        logger.info("called createCustomer");

        customerService.saveCustomer(customer);

        return Response.ok(customer.getId()).status(Response.Status.CREATED).build();
    }

    @PUT
    public Response updateCustomer(@Valid CustomerDTO customer) {
        logger.info("called updateCustomer");

        customerService.updateCustomer(customer);

        return Response.ok(customer.getId()).status(Response.Status.OK).build();
    }
    
    @DELETE
    @Path("{id}")
    public Response deleteCustomer(@PathParam("id") @NotNull Long id) {
        logger.info("called deleteCustomer");

        customerService.deleteCustomer(id);

        return Response.ok().status(Response.Status.OK).build();
    }
}