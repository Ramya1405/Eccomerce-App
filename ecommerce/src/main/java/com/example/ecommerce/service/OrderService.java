// OrderService.java
package com.example.ecommerce.service;

import com.example.ecommerce.dto.OrderLineItemsDto;
import com.example.ecommerce.dto.OrderRequest;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.OrderItem;
import com.example.ecommerce.exception.OrderNotFoundException;
import com.example.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	public ResponseEntity<List<Order>> getAllOrders() {
		
			List<Order> orders = orderRepository.findAll();
			return ResponseEntity.ok(orders);
		
	}

	public ResponseEntity<Order> getOrderById(Long orderId) {
		
			 Optional<Order> existingOrder = orderRepository.findById(orderId);
			    
			    if (existingOrder.isPresent()) {
			        return ResponseEntity.ok(existingOrder.get());
			    } else {
			        throw new OrderNotFoundException("Order with ID " + orderId + " not found");
			    }
		
	}

	public ResponseEntity<Order> createOrder(OrderRequest orderRequest) {
	
			Order order = new Order();
			order.setOrderNumber(UUID.randomUUID().toString());
			List<OrderItem> orderLineItems = orderRequest.getOrderLineItems().stream()
					.map(OrderLineItemsDto -> mapToDto(OrderLineItemsDto)).toList();
			order.setOrderLineItems(orderLineItems);
			Order createdOrder = orderRepository.save(order);
			return ResponseEntity.status(HttpStatus.CREATED).header("success", "Order created successfully.")
					.body(createdOrder);
		
	}

	public ResponseEntity<Order> updateOrder(Long orderId, OrderItem updatedOrderItem) {
	
			Optional<Order> existingOrder = orderRepository.findById(orderId);

			if (existingOrder.isPresent()) {
				Order orderToUpdate = existingOrder.get();

				// Check if orderLineItems is null before iterating
				List<OrderItem> orderLineItems = orderToUpdate.getOrderLineItems();
				for (OrderItem orderItem : orderLineItems) {
					orderItem.setPrice(updatedOrderItem.getPrice());
					orderItem.setQuantity(updatedOrderItem.getQuantity());
					orderItem.setSkuCode(updatedOrderItem.getSkuCode());
				}
				Order updatedOrder = orderRepository.save(orderToUpdate);
				return ResponseEntity.ok().header("success", "Order updated successfully.").body(updatedOrder);
			} else {
				// Throw exception the Order is not found
				throw new OrderNotFoundException("Order with ID " + orderId + " not found");
			}

	}

	public ResponseEntity<Void> deleteOrder(Long orderId) {

		
			Optional<Order> existingOrder = orderRepository.findById(orderId);
			if (existingOrder.isPresent()) {
				orderRepository.deleteById(orderId);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).header("success", "Order deleted successfully.")
						.build();
			} else {
				// Throw exception the Order is not found
				throw new OrderNotFoundException("Order with ID " + orderId + " not found");
			}

		
	}

	private OrderItem mapToDto(OrderLineItemsDto orderLineItemsDto) {
		OrderItem orderLineItems = new OrderItem();
		orderLineItems.setId(orderLineItemsDto.getId());
		orderLineItems.setPrice(orderLineItemsDto.getPrice());
		orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
		orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
		return orderLineItems;
	}
}
