package sk.pazurik.customerservice.application.product;

import org.slf4j.Logger;
import sk.pazurik.customerservice.domain.product.ProductService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import sk.pazurik.customerservice.domain.product.ProductDTO;

@Path("product")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class ProductResource {

    @Inject
    private Logger logger;

    @Inject
    private ProductService productService;

    @GET
    public Response getProducts() {
        logger.info("called getProducts");
        // todo: call productService

        return Response.ok().status(Response.Status.OK).build();
    }

    @GET
    @Path("{id}")
    public Response getProductById(@PathParam("id") @NotNull Long id) {
        logger.info("called getProductById, {}", id);
        // todo: call productService

        return Response.ok().status(Response.Status.OK).build();
    }
    
    @POST
    public Response createProduct(@Valid ProductDTO product) {
        logger.info("called createProduct");

        productService.saveProduct(product);

        return Response.ok().status(Response.Status.OK).build();
    }
}
