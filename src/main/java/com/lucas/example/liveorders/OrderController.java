package com.lucas.example.liveorders;

import com.lucas.example.liveorders.message.CreateOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Brendt Lucas
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/orders")
@ComponentScan("com.lucas.example.liveorders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * @return Live order board summary information
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    OrderBoard getLiveOrderBoard() throws Exception {
        return orderService.getLiveOrderBoard();
    }

    /**
     * Cancels a registered order
     *
     * @param orderKey The order reference generated when registering the order
     * @throws Exception
     */
    @RequestMapping(value = "/{orderKey}", method = RequestMethod.DELETE)
    void cancelOrder(@PathVariable String orderKey) throws Exception {
        orderService.cancelOrder(orderKey);
    }

    /**
     * Registers an order in the system
     *
     * @param request Request containing the details required to register an order
     * @return The {@code orderKey} - a reference to the order which can be used to cancel the order
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST)
    String registerOrder(@RequestBody @Valid CreateOrderRequest request) throws Exception {

        return orderService.registerOrder(request.getUserId(), request.getQuantity(), request.getPricePerUnit(),
                request.getOrderType());
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderController.class, args);
    }
}
