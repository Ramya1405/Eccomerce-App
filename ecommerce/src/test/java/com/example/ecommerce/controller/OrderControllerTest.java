package com.example.ecommerce.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import com.example.ecommerce.entity.*;
import com.example.ecommerce.dto.*;
import com.example.ecommerce.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@SpringBootTest(classes = {OrderControllerTest.class})
@ContextConfiguration
@AutoConfigureMockMvc
public class OrderControllerTest {
	@Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    void test_getAllOrders() {
        // Arrange
        List<Order> orders = Arrays.asList(new Order(), new Order());
        when(orderService.getAllOrders()).thenReturn(ResponseEntity.ok(orders));

        // Act
        ResponseEntity<List<Order>> response = orderController.getAllOrders();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orders, response.getBody());
    }

    @Test
    void test_getOrderById() {
        // Arrange
        Long orderId = 1L;
        Order order = new Order();
        when(orderService.getOrderById(orderId)).thenReturn(ResponseEntity.ok(order));

        // Act
        ResponseEntity<Order> response = orderController.getOrderById(orderId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
    }

    @Test
    void test_createOrder() {
        // Arrange
        OrderRequest orderRequest = new OrderRequest();
        Order createdOrder = new Order();
        when(orderService.createOrder(orderRequest)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(createdOrder));

        // Act
        ResponseEntity<Order> response = orderController.createOrder(orderRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdOrder, response.getBody());
    }

    @Test
    void test_updateOrder() {
        // Arrange
        Long orderId = 1L;
        OrderItem orderItem = new OrderItem();
        Order updatedOrder = new Order();
        when(orderService.updateOrder(orderId, orderItem)).thenReturn(ResponseEntity.ok(updatedOrder));

        // Act
        ResponseEntity<Order> response = orderController.updateOrder(orderId, orderItem);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedOrder, response.getBody());
    }

    @Test
    void test_deleteOrder() {
        // Arrange
        Long orderId = 1L;
        when(orderService.deleteOrder(orderId)).thenReturn(ResponseEntity.noContent().build());

        // Act
        ResponseEntity<Void> response = orderController.deleteOrder(orderId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
    
    
}
