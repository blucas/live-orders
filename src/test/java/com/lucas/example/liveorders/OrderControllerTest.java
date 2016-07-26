package com.lucas.example.liveorders;

import com.lucas.example.liveorders.message.CreateOrderRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Brendt Lucas
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(OrderController.class)
@WebIntegrationTest
public class OrderControllerTest {

    private RestTemplate restTemplate = new TestRestTemplate();

    // Make sure the service is not currently running when executing these tests
    private String baseUrl = "http://localhost:8080";

    @Test
    public void testOrderRegistration() throws Exception {
        final CreateOrderRequest orderRequest = new CreateOrderRequest(1, 5, 300, OrderType.SELL);
        final ResponseEntity<String> response =
                restTemplate.postForEntity(baseUrl + "/orders", orderRequest, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        final String orderKey = response.getBody();
        assertNotNull(orderKey);
    }

    @Test
    public void testOrderCancellation() throws Exception {

        // Initialize the system with a BUY order
        final CreateOrderRequest orderRequest = new CreateOrderRequest(2, 10, 1500, OrderType.BUY);
        final ResponseEntity<String> createResponse =
                restTemplate.postForEntity(baseUrl + "/orders", orderRequest, String.class);

        assertEquals(HttpStatus.OK, createResponse.getStatusCode());

        final String orderKey = createResponse.getBody();

        assertNotNull(orderKey);

        final ResponseEntity<Void> cancelResponse = restTemplate.exchange(baseUrl + "/orders/{orderKey}",
                HttpMethod.DELETE, null, Void.class, orderKey);

        assertEquals(HttpStatus.OK, cancelResponse.getStatusCode());
    }

    @Test
    public void testOrderAggregation() throws Exception {

        final CreateOrderRequest orderRequest = new CreateOrderRequest(34, 10, 3000, OrderType.BUY);
        restTemplate.postForEntity(baseUrl + "/orders", orderRequest, String.class);
        restTemplate.postForEntity(baseUrl + "/orders", orderRequest, String.class);

        final ResponseEntity<OrderBoard> response = restTemplate.getForEntity(baseUrl + "/orders", OrderBoard.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        final OrderBoard orderBoard = response.getBody();
        assertNotNull(orderBoard);

        final List<BoardEntry> buyOrderBoard = orderBoard.getBuyOrderBoard();
        assertNotNull(buyOrderBoard);
        assertFalse(buyOrderBoard.isEmpty());

        boolean found = false;
        for (BoardEntry boardEntry : buyOrderBoard) {
            if (boardEntry.getPricePerUnit() == orderRequest.getPricePerUnit()) {
                assertEquals(orderRequest.getQuantity() * 2, boardEntry.getQuantity());
                found = true;
                break;
            }
        }

        assertTrue("Failed to find matching order on the order board", found);
        assertEquals(orderRequest.getQuantity() * 2, buyOrderBoard.get(0).getQuantity());
    }

    @Test
    public void testOrderSortDirection() throws Exception {

        CreateOrderRequest orderRequest = new CreateOrderRequest(34, 10, 1, OrderType.BUY);
        restTemplate.postForEntity(baseUrl + "/orders", orderRequest, String.class);
        orderRequest = new CreateOrderRequest(34, 10, 10, OrderType.BUY);
        restTemplate.postForEntity(baseUrl + "/orders", orderRequest, String.class);
        orderRequest = new CreateOrderRequest(34, 10, 100, OrderType.BUY);
        restTemplate.postForEntity(baseUrl + "/orders", orderRequest, String.class);

        orderRequest = new CreateOrderRequest(34, 7, 2, OrderType.SELL);
        restTemplate.postForEntity(baseUrl + "/orders", orderRequest, String.class);
        orderRequest = new CreateOrderRequest(6, 7, 20, OrderType.SELL);
        restTemplate.postForEntity(baseUrl + "/orders", orderRequest, String.class);
        orderRequest = new CreateOrderRequest(6, 7, 200, OrderType.SELL);
        restTemplate.postForEntity(baseUrl + "/orders", orderRequest, String.class);

        final ResponseEntity<OrderBoard> response = restTemplate.getForEntity(baseUrl + "/orders", OrderBoard.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        final OrderBoard orderBoard = response.getBody();
        assertNotNull(orderBoard);

        List<BoardEntry> orders = orderBoard.getBuyOrderBoard();
        assertNotNull(orders);
        assertFalse(orders.isEmpty());

        Integer previousPrice = null;
        for (BoardEntry boardEntry : orders) {
            if (previousPrice != null) {
                assertTrue("The BUY orders must be sorted in descending order by pricePerUnit",
                        previousPrice > boardEntry.getPricePerUnit());
            }

            previousPrice = boardEntry.getPricePerUnit();
        }

        orders = orderBoard.getSellOrderBoard();
        assertNotNull(orders);
        assertFalse(orders.isEmpty());

        previousPrice = null;
        for (BoardEntry boardEntry : orders) {
            if (previousPrice != null) {
                assertTrue("The SELL orders must be sorted in ascending order by pricePerUnit",
                        previousPrice < boardEntry.getPricePerUnit());
            }

            previousPrice = boardEntry.getPricePerUnit();
        }
    }
}
