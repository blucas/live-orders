package com.lucas.example.liveorders;

import javax.persistence.*;

/**
 * @author Brendt Lucas
 */

@Entity(name = "OrderItem")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Basic(optional = false)
    @Column(unique = true)
    private String orderKey;

    @Basic(optional = false)
    private int quantity;

    @Basic(optional = false)
    private int pricePerUnit;

    @Enumerated(EnumType.STRING)
    @Basic(optional = false)
    private OrderType orderType;

    @Basic(optional = false)
    private int userId;

    Order() {
    }

    public Order(String orderKey, OrderType orderType, int quantity, int pricePerUnit, int userId) {
        this.orderKey = orderKey;
        this.orderType = orderType;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPricePerUnit() {
        return pricePerUnit;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public int getUserId() {
        return userId;
    }
}
