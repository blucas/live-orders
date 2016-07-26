package com.lucas.example.liveorders;

import java.util.List;

/**
 * @author Brendt Lucas
 */
public class OrderBoard {

    private List<BoardEntry> buyOrderBoard;

    private List<BoardEntry> sellOrderBoard;

    public OrderBoard() {
    }

    public OrderBoard(List<BoardEntry> buyOrderBoard, List<BoardEntry> sellOrderBoard) {
        this.buyOrderBoard = buyOrderBoard;
        this.sellOrderBoard = sellOrderBoard;
    }

    public List<BoardEntry> getBuyOrderBoard() {
        return buyOrderBoard;
    }

    public List<BoardEntry> getSellOrderBoard() {
        return sellOrderBoard;
    }
}
