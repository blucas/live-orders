package com.lucas.example.liveorders.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lucas.example.liveorders.OrderType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Brendt Lucas
 */
public class CreateOrderRequest {

    @NotNull
    @JsonProperty
    private Integer userId;

    @NotNull
    @Min(0)
    @JsonProperty
    private Integer quantity;

    @NotNull
    @Min(0)
    @JsonProperty
    private Integer pricePerUnit;

    @NotNull
    private OrderType orderType;

    public CreateOrderRequest() {
    }

    public CreateOrderRequest(Integer userId, Integer quantity, Integer pricePerUnit, OrderType orderType) {
        this.userId = userId;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.orderType = orderType;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getPricePerUnit() {
        return pricePerUnit;
    }

    public OrderType getOrderType() {
        return orderType;
    }
}
