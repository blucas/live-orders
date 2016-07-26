package com.lucas.example.liveorders;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Brendt Lucas
 */
public interface OrderRepository extends CrudRepository<Order, Long> {

    /**
     * @return Live order board entries pertaining to SELL orders
     */
    @Query("select new com.lucas.example.liveorders.BoardEntry(o.pricePerUnit, sum(o.quantity)) from OrderItem o " +
            "where o.orderType = 'SELL' " +
            "group by o.pricePerUnit order by o.pricePerUnit asc")
    List<BoardEntry> getSellOrderBoard();

    /**
     * @return Live order board entries pertaining to BUY orders
     */
    @Query("select new com.lucas.example.liveorders.BoardEntry(o.pricePerUnit, sum(o.quantity)) from OrderItem o " +
            "where o.orderType = 'BUY' " +
            "group by o.pricePerUnit order by o.pricePerUnit desc")
    List<BoardEntry> getBuyOrderBoard();

    /**
     * @param orderKey The order reference generated when registering the order
     * @return The Order with the given order key
     */
    Order findByOrderKey(String orderKey);
}
