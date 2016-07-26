package com.lucas.example.liveorders;

/**
 * @author Brendt Lucas
 */
public class BoardEntry {

    private int pricePerUnit;

    private long quantity;

    public BoardEntry() {
    }

    public BoardEntry(int pricePerUnit, long quantity) {
        this.pricePerUnit = pricePerUnit;
        this.quantity = quantity;
    }

    public int getPricePerUnit() {
        return pricePerUnit;
    }

    public long getQuantity() {
        return quantity;
    }
}
