package com.example.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import com.example.ecommerce.entity.OrderItem;
import com.example.ecommerce.exception.OrderItemNotFound;
import com.example.ecommerce.repository.OrderItemRepository;

@SpringBootTest(classes = {OrderItemServiceTest.class})
@ContextConfiguration
@AutoConfigureMockMvc
public class OrderItemServiceTest {

	@Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderItemService orderItemService;

    @Test
    void getAllOrderItems() {
        // Arrange
        OrderItem orderItem1 = new OrderItem();
        OrderItem orderItem2 = new OrderItem();
        List<OrderItem> orderItems = Arrays.asList(orderItem1, orderItem2);
        when(orderItemRepository.findAll()).thenReturn(orderItems);

        // Act
        ResponseEntity<List<OrderItem>> responseEntity = orderItemService.getAllOrderItems();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(orderItems, responseEntity.getBody());
        verify(orderItemRepository, times(1)).findAll();
    }

    @Test
    void getOrderItemById_OrderItemFound() {
        // Arrange
        Long orderItemId = 1L;
        OrderItem orderItem = new OrderItem();
        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.of(orderItem));

        // Act
        ResponseEntity<OrderItem> responseEntity = orderItemService.getOrderItemById(orderItemId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(orderItem, responseEntity.getBody());
        verify(orderItemRepository, times(1)).findById(orderItemId);
    }


	
    @Test
    void getOrderItemById_OrderItemNotFound() {
        // Arrange
        Long orderItemId = 1L;
        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(OrderItemNotFound.class, () -> orderItemService.getOrderItemById(orderItemId));

       
    }

    @Test
    void createOrderItem() {
        // Arrange
        OrderItem orderItem = new OrderItem();
        when(orderItemRepository.save(orderItem)).thenReturn(orderItem);

        // Act
        ResponseEntity<OrderItem> responseEntity = orderItemService.createOrderItem(orderItem);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(orderItem, responseEntity.getBody());
        verify(orderItemRepository, times(1)).save(orderItem);
    }

    @Test
    void updateOrderItem_OrderItemFound() {
        // Arrange
        Long orderItemId = 1L;
        OrderItem existingOrderItem = new OrderItem();
        OrderItem updatedOrderItem = new OrderItem();
        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.of(existingOrderItem));
        when(orderItemRepository.save(existingOrderItem)).thenReturn(updatedOrderItem);

        // Act
        ResponseEntity<OrderItem> responseEntity = orderItemService.updateOrderItem(orderItemId, existingOrderItem);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedOrderItem, responseEntity.getBody());
        verify(orderItemRepository, times(1)).findById(orderItemId);
        verify(orderItemRepository, times(1)).save(existingOrderItem);
    }

    @Test
    void updateOrderItem_OrderItemNotFound() {
        // Arrange
        Long orderItemId = 1L;
        OrderItem updatedOrderItem = new OrderItem();
        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.empty());

        // Act and Assert
        OrderItemNotFound exception = assertThrows(OrderItemNotFound.class,
                () -> orderItemService.updateOrderItem(orderItemId, updatedOrderItem));
        assertEquals("OrderItem  with ID " + orderItemId + " not found", exception.getMessage());
        verify(orderItemRepository, times(1)).findById(orderItemId);
        verify(orderItemRepository, never()).save(any());
    }

    @Test
    void deleteOrderItem_OrderItemFound() {
        // Arrange
        Long orderItemId = 1L;
        OrderItem existingOrderItem = new OrderItem();
        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.of(existingOrderItem));

        // Act
        ResponseEntity<Void> responseEntity = orderItemService.deleteOrderItem(orderItemId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(orderItemRepository, times(1)).findById(orderItemId);
        verify(orderItemRepository, times(1)).deleteById(orderItemId);
    }

    @Test
    void deleteOrderItem_OrderItemNotFound() {
        // Arrange
        Long orderItemId = 1L;
        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.empty());

        // Act and Assert
        OrderItemNotFound exception = assertThrows(OrderItemNotFound.class,
                () -> orderItemService.deleteOrderItem(orderItemId));
        assertEquals("Order Item with ID " + orderItemId + " not found", exception.getMessage());
        verify(orderItemRepository, times(1)).findById(orderItemId);
        verify(orderItemRepository, never()).deleteById(any());
    }
}