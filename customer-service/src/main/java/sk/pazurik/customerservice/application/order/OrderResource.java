package sk.pazurik.customerservice.application.order;

import org.slf4j.Logger;
import sk.pazurik.customerservice.domain.order.OrderDTO;
import sk.pazurik.customerservice.domain.order.OrderService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Collection;

@Path("customer/{customerId}/order")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class OrderResource {
    
    @Inject
    private Logger logger;

    @Inject
    private OrderService orderService;

    @GET
    public Response getAllOrders(@PathParam("customerId") @NotNull Long customerId,
                                 @QueryParam("minPrice") BigDecimal minPrice) {
        Collection<OrderDTO> orders;
        if (minPrice == null) {
            logger.info("called getAllOrders");
            orders = orderService.getAllOrders(customerId);
        } else {
            logger.info("called getOrders, {}", minPrice);
            orders = orderService.getOrdersByMinPrice(customerId, minPrice);
        }
        return Response.ok(orders).status(Response.Status.OK).build();
    }

    @GET
    @Path("{id}")
    public Response getOrderById(@PathParam("customerId") @NotNull Long customerId,
                                 @PathParam("id") @NotNull Long id) {
        logger.info("called getOrderById, {}", id);

        OrderDTO dto = orderService.getOrderById(customerId, id);

        return Response.ok(dto).status(Response.Status.OK).build();
    }
    
    @POST
    public Response createOrder(@PathParam("customerId") @NotNull Long customerId,
                                @Valid OrderDTO order) {
        logger.info("called createOrder");

        orderService.saveOrder(customerId, order);

        return Response.ok(order.getId()).status(Response.Status.CREATED).build();
    }

    @PUT
    public Response updateOrder(@PathParam("customerId") @NotNull Long customerId,
                                @Valid OrderDTO order) {
        logger.info("called updateOrder");

        orderService.updateOrder(customerId, order);

        return Response.ok(order.getId()).status(Response.Status.OK).build();
    }
    
    @DELETE
    @Path("{id}")
    public Response deleteOrder(@PathParam("customerId") @NotNull Long customerId,
                                @PathParam("id") @NotNull Long id) {
        logger.info("called deleteOrder");

        orderService.deleteOrder(customerId, id);

        return Response.ok().status(Response.Status.OK).build();
    }    
}
