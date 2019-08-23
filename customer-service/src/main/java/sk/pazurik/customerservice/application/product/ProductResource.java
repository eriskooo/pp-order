package sk.pazurik.customerservice.application.product;

import org.slf4j.Logger;
import sk.pazurik.customerservice.domain.product.ProductDTO;
import sk.pazurik.customerservice.domain.product.ProductService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("product")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class ProductResource {

    @Inject
    private Logger logger;

    @Inject
    private ProductService productService;

    @GET
    public Response getAllProducts() {
        logger.info("called getAllProducts");

        Collection<ProductDTO> products = productService.getAllProducts();

        return Response.ok(products).status(Response.Status.OK).build();
    }
    
    @GET
    @Path("getProductsByName/{name}")
    public Response getProductsByName(@PathParam("name") @NotNull String name) {
        logger.info("called getProductsByName, {}", name);

        Collection<ProductDTO> products = productService.getProductsByName(name);

        return Response.ok(products).status(Response.Status.OK).build();
    }

    @GET
    @Path("{id}")
    public Response getProductById(@PathParam("id") @NotNull Long id) {
        logger.info("called getProductById, {}", id);

        ProductDTO dto = productService.getProductById(id);

        return Response.ok(dto).status(Response.Status.OK).build();
    }

    @POST
    public Response createProduct(@Valid ProductDTO product) {
        logger.info("called createProduct");

        productService.saveProduct(product);

        return Response.ok(product.getId()).status(Response.Status.CREATED).build();
    }
    
    @PUT
    public Response updateProduct(@Valid ProductDTO product) {
        logger.info("called updateProduct");

        productService.updateProduct(product);

        return Response.ok(product.getId()).status(Response.Status.OK).build();
    }
    
    @DELETE
    @Path("{id}")
    public Response deleteProduct(@PathParam("id") @NotNull Long id) {
        logger.info("called deleteProduct");

        productService.deleteProduct(id);

        return Response.ok().status(Response.Status.OK).build();
    }
}
