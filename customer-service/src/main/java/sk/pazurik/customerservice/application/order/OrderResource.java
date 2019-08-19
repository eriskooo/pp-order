package sk.pazurik.customerservice.application.order;

import java.math.BigDecimal;
import java.util.Collection;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import sk.pazurik.customerservice.domain.order.OrderDTO;
import sk.pazurik.customerservice.domain.order.OrderService;

@Path("order")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class OrderResource {
    
    @Inject
    private Logger logger;

    @Inject
    private OrderService orderService;

    @GET
    @Path("{minPrice}")
    public Response getOrders(@PathParam("minPrice") @NotNull BigDecimal minPrice) {
        logger.info("called getOrders, {}", minPrice);

        Collection<OrderDTO> orders = orderService.getOrders(minPrice);

        return Response.ok(orders).status(Response.Status.OK).build();
    }

    @GET
    @Path("{id}")
    public Response getOrderById(@PathParam("id") @NotNull Long id) {
        logger.info("called getOrderById, {}", id);

        OrderDTO dto = orderService.getOrderById(id);

        return Response.ok(dto).status(Response.Status.OK).build();
    }
    
    @POST
    public Response createOrder(@Valid OrderDTO order) {
        logger.info("called createOrder");

        orderService.saveOrder(order);

        return Response.ok(order.getId()).status(Response.Status.CREATED).build();
    }

    @PUT
    public Response updateOrder(@Valid OrderDTO order) {
        logger.info("called updateOrder");

        orderService.updateOrder(order);

        return Response.ok(order.getId()).status(Response.Status.OK).build();
    }
    
    @DELETE
    @Path("{id}")
    public Response deleteOrder(@PathParam("id") @NotNull Long id) {
        logger.info("called deleteOrder");

        orderService.deleteOrder(id);

        return Response.ok().status(Response.Status.OK).build();
    }    
}
