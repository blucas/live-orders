package com.lucas.example.liveorders;

/**
 * @author Brendt Lucas
 */
public interface OrderService {

    /**
     * @return The live order board
     */
    OrderBoard getLiveOrderBoard();

    /**
     * Registers an order in the system.
     *
     * @param userId The user who placed the order
     * @param quantity The order quantity
     * @param pricePerUnit The price of the item per unit
     * @param orderType The type of the order
     * @return An order reference which can be used to cancel the order
     */
    String registerOrder(int userId, int quantity, int pricePerUnit, OrderType orderType);

    /**
     * Cancels a registered order
     *
     * @param orderKey The order reference which was generated when registering the order
     */
    void cancelOrder(String orderKey);


}
