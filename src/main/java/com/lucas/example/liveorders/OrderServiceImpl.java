package com.lucas.example.liveorders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @author Brendt Lucas
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String registerOrder(int userId, int quantity, int pricePerUnit, OrderType orderType) {

        final String orderKey = UUID.randomUUID().toString();
        final Order order = new Order(orderKey, orderType, quantity, pricePerUnit, userId);

        orderRepository.save(order);

        return orderKey;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(String orderKey) {

        final Order order = orderRepository.findByOrderKey(orderKey);
        if (order != null) {
            orderRepository.delete(order);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderBoard getLiveOrderBoard() {

        final List<BoardEntry> buyOrderBoard = orderRepository.getBuyOrderBoard();
        final List<BoardEntry> sellOrderBoard = orderRepository.getSellOrderBoard();

        return new OrderBoard(buyOrderBoard, sellOrderBoard);
    }
}
