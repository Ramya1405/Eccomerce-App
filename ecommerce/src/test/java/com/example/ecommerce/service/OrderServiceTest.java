package com.example.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import com.example.ecommerce.entity.*;
import com.example.ecommerce.exception.OrderNotFoundException;
import com.example.ecommerce.dto.*;
import com.example.ecommerce.repository.OrderRepository;
import java.math.BigDecimal;

@SpringBootTest(classes = { OrderServiceTest.class })
@ContextConfiguration
@AutoConfigureMockMvc
public class OrderServiceTest {
	@InjectMocks
	private OrderService orderService;

	@Mock
	private OrderRepository orderRepository;

	@Test
    void testGetAllOrders_Success() {
        // Arrange
        when(orderRepository.findAll()).thenReturn(List.of(new Order()));

        // Act
        ResponseEntity<List<Order>> responseEntity = orderService.getAllOrders();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertFalse(responseEntity.getBody().isEmpty());

        verify(orderRepository, times(1)).findAll();
    }

	@Test
	void testGetOrderById_OrderFound() {
		// Arrange
		Long orderId = 1L;
		Order mockOrder = new Order();
		when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));

		// Act
		ResponseEntity<Order> responseEntity = orderService.getOrderById(orderId);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());

		verify(orderRepository, times(1)).findById(orderId);
	}

	@Test
	void testGetOrderById_OrderNotFound() {
	
		// Arrange
		Long orderId = 1L;
		when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(orderId));

	}
	
	

	@Test
	void testCreateOrder_Success() {
		// Arrange
		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setOrderLineItems(Collections.singletonList(new OrderLineItemsDto()));

		// Mocking the save method of the orderRepository
		when(orderRepository.save(any())).thenReturn(createSampleOrder());

		// Act
		ResponseEntity<Order> responseEntity = orderService.createOrder(orderRequest);

		// Assert
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertEquals("Order created successfully.", responseEntity.getHeaders().getFirst("success"));

		// Verify that the save method of the orderRepository is called once
		verify(orderRepository, times(1)).save(any());
	}

	// Helper method to create a sample Order for testing
	private Order createSampleOrder() {
		Order order = new Order();
		order.setId(1L);
		order.setOrderNumber(UUID.randomUUID().toString());
		order.setOrderLineItems(Collections.singletonList(createSampleOrderItem()));
		return order;
	}

	// Helper method to create a sample OrderItem for testing
	private OrderItem createSampleOrderItem() {
		OrderItem orderItem = new OrderItem();
		orderItem.setId(1L);
		orderItem.setPrice(BigDecimal.valueOf(10.0));
		orderItem.setQuantity(2);
		orderItem.setSkuCode("ABC123");
		return orderItem;
	}

	@Test
	void testUpdateOrder_Success() {
		// Arrange
		Long orderId = 1L;
		OrderItem updatedOrderItem = new OrderItem();

		updatedOrderItem.setPrice(BigDecimal.valueOf(20.0));
		updatedOrderItem.setQuantity(3);
		updatedOrderItem.setSkuCode("SKU123");

		Order existingOrder = new Order();
		existingOrder.setOrderLineItems(Collections.singletonList(new OrderItem()));

		when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));
		when(orderRepository.save(any())).thenReturn(existingOrder);

		// Act
		ResponseEntity<Order> responseEntity = orderService.updateOrder(orderId, updatedOrderItem);

		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());

		verify(orderRepository, times(1)).findById(orderId);
		verify(orderRepository, times(1)).save(any());
	}

	@Test
	void testUpdateOrder_OrderNotFound() {
		// Arrange
		Long orderId = 1L;
		OrderItem updatedOrderItem = new OrderItem();

		when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

		// Act
        assertThrows(OrderNotFoundException.class, () -> orderService.updateOrder(orderId,updatedOrderItem));

	}

	
	
	@Test
	void testDeleteOrder_Success() {
		// Arrange
		Long orderId = 1L;
		Order existingOrder = new Order();
		when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));

		// Act
		ResponseEntity<Void> responseEntity = orderService.deleteOrder(orderId);

		// Assert
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
		assertNull(responseEntity.getBody());

		verify(orderRepository, times(1)).findById(orderId);
		verify(orderRepository, times(1)).deleteById(orderId);
	}

	@Test
	void testDeleteOrder_OrderNotFound() {
		// Arrange
		Long orderId = 1L;
		when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

		 // Act and Assert
		assertThrows(OrderNotFoundException.class, () -> orderService.deleteOrder(orderId));
	}

}