package com.example.ecommerce.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.http.ResponseEntity;
import com.example.ecommerce.entity.OrderItem;
import com.example.ecommerce.service.OrderItemService;
import com.example.ecommerce.service.OrderServiceTest;

@SpringBootTest(classes = {OrderServiceTest.class})
@ContextConfiguration
@AutoConfigureMockMvc
public class OrderItemControllerTest{
	 @Mock
	    private OrderItemService orderItemService;

	    @InjectMocks
	    private OrderItemController orderItemController;

	    @Test
	    void getAllOrderItems() {
	        // Arrange
	        OrderItem orderItem1 = new OrderItem();
	        OrderItem orderItem2 = new OrderItem();
	        List<OrderItem> orderItems = Arrays.asList(orderItem1, orderItem2);
	        when(orderItemService.getAllOrderItems()).thenReturn(ResponseEntity.ok(orderItems));

	        // Act
	        ResponseEntity<List<OrderItem>> responseEntity = orderItemController.getAllOrderItems();

	        // Assert
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	        assertEquals(orderItems, responseEntity.getBody());
	        verify(orderItemService, times(1)).getAllOrderItems();
	    }

	    @Test
	    void getOrderItemById_OrderItemFound() {
	        // Arrange
	        Long itemId = 1L;
	        OrderItem orderItem = new OrderItem();
	        when(orderItemService.getOrderItemById(itemId)).thenReturn(ResponseEntity.ok(orderItem));

	        // Act
	        ResponseEntity<OrderItem> responseEntity = orderItemController.getOrderItemById(itemId);

	        // Assert
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	        assertEquals(orderItem, responseEntity.getBody());
	        verify(orderItemService, times(1)).getOrderItemById(itemId);
	    }

	    @Test
	    void getOrderItemById_OrderItemNotFound() {
	        // Arrange
	        Long itemId = 1L;
	        when(orderItemService.getOrderItemById(itemId)).thenReturn(ResponseEntity.notFound().build());

	        // Act
	        ResponseEntity<OrderItem> responseEntity = orderItemController.getOrderItemById(itemId);

	        // Assert
	        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	        verify(orderItemService, times(1)).getOrderItemById(itemId);
	    }

	    @Test
	    void createOrderItem() {
	        // Arrange
	        OrderItem orderItem = new OrderItem();
	        when(orderItemService.createOrderItem(any(OrderItem.class))).thenReturn(ResponseEntity.ok(orderItem));

	        // Act
	        ResponseEntity<OrderItem> responseEntity = orderItemController.createOrderItem(orderItem);

	        // Assert
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	        assertEquals(orderItem, responseEntity.getBody());
	        verify(orderItemService, times(1)).createOrderItem(any(OrderItem.class));
	    }

	    @Test
	    void updateOrderItem_OrderItemFound() {
	        // Arrange
	        Long itemId = 1L;
	        OrderItem updatedOrderItem = new OrderItem();
	        when(orderItemService.updateOrderItem(eq(itemId), any(OrderItem.class))).thenReturn(ResponseEntity.ok(updatedOrderItem));

	        // Act
	        ResponseEntity<OrderItem> responseEntity = orderItemController.updateOrderItem(itemId, updatedOrderItem);

	        // Assert
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	        assertEquals(updatedOrderItem, responseEntity.getBody());
	        verify(orderItemService, times(1)).updateOrderItem(eq(itemId), any(OrderItem.class));
	    }

	    @Test
	    void updateOrderItem_OrderItemNotFound() {
	        // Arrange
	        Long itemId = 1L;
	        OrderItem updatedOrderItem = new OrderItem();
	        when(orderItemService.updateOrderItem(eq(itemId), any(OrderItem.class))).thenReturn(ResponseEntity.notFound().build());

	        // Act
	        ResponseEntity<OrderItem> responseEntity = orderItemController.updateOrderItem(itemId, updatedOrderItem);

	        // Assert
	        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	        verify(orderItemService, times(1)).updateOrderItem(eq(itemId), any(OrderItem.class));
	    }

	    @Test
	    void deleteOrderItem_OrderItemFound() {
	        // Arrange
	        Long itemId = 1L;
	        when(orderItemService.deleteOrderItem(itemId)).thenReturn(ResponseEntity.noContent().build());

	        // Act
	        ResponseEntity<Void> responseEntity = orderItemController.deleteOrderItem(itemId);

	        // Assert
	        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
	        verify(orderItemService, times(1)).deleteOrderItem(itemId);
	    }

	    @Test
	    void deleteOrderItem_OrderItemNotFound() {
	        // Arrange
	        Long itemId = 1L;
	        when(orderItemService.deleteOrderItem(itemId)).thenReturn(ResponseEntity.notFound().build());

	        // Act
	        ResponseEntity<Void> responseEntity = orderItemController.deleteOrderItem(itemId);

	        // Assert
	        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	        verify(orderItemService, times(1)).deleteOrderItem(itemId);
	    }
}
