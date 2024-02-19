// OrderItemService.java
package com.example.ecommerce.service;

import com.example.ecommerce.entity.OrderItem;
import com.example.ecommerce.exception.OrderItemNotFound;
import com.example.ecommerce.repository.OrderItemRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

	private final OrderItemRepository orderItemRepository;

	@Autowired
	public OrderItemService(OrderItemRepository orderItemRepository) {
		this.orderItemRepository = orderItemRepository;
	}

	public ResponseEntity<List<OrderItem>> getAllOrderItems() {
		List<OrderItem> orderItems = orderItemRepository.findAll();
		return ResponseEntity.ok(orderItems);
	}

	public ResponseEntity<OrderItem> getOrderItemById(Long orderItemId) {
		Optional<OrderItem> existingOrderItem = orderItemRepository.findById(orderItemId);

		if (existingOrderItem.isPresent()) {
			return ResponseEntity.ok(existingOrderItem.get());
		} else {
			throw new OrderItemNotFound("orderItem with ID " + orderItemId + " not found");
		}
	}

	public ResponseEntity<OrderItem> createOrderItem(OrderItem orderItem) {
		OrderItem createdorderItem = orderItemRepository.save(orderItem);
		return ResponseEntity.status(HttpStatus.CREATED).header("success", "Order Item created successfully.")
				.body(createdorderItem);

	}

	public ResponseEntity<OrderItem> updateOrderItem(Long orderItemId, OrderItem updatedOrderItem) {
		Optional<OrderItem> existingOrderItem = orderItemRepository.findById(orderItemId);
		if (existingOrderItem.isPresent()) {
			OrderItem orderItemToUpdate = existingOrderItem.get();
			OrderItem updatedOrder = orderItemRepository.save(orderItemToUpdate);

			return ResponseEntity.ok().header("success", "Order Item updated successfully.").body(updatedOrder);
		} else {
			// Throw exception the Order is not found
			throw new OrderItemNotFound("OrderItem  with ID " + orderItemId + " not found");
		}
	}

	@Transactional
	public ResponseEntity<Void> deleteOrderItem(Long orderItemId) {
		

		Optional<OrderItem> existingOrderItem = orderItemRepository.findById(orderItemId);
		if (existingOrderItem.isPresent()) {
			orderItemRepository.deleteById(orderItemId);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).header("success", "Order Item deleted successfully.")
					.build();
		} else {
			// Throw exception the Order is not found
			throw new OrderItemNotFound("Order Item with ID " + orderItemId + " not found");
		}
	}
}
