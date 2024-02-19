package com.example.ecommerce.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.ecommerce.entity.OrderItem;
import com.example.ecommerce.service.OrderItemService;
import java.util.List;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

	@Autowired
	private OrderItemService orderItemService;

	@Operation(summary = "Get a list of all order items")
    @ApiResponse(responseCode = "200", description = "List of order items", content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderItem.class))))
	@GetMapping
	public ResponseEntity<List<OrderItem>> getAllOrderItems() {
		ResponseEntity<List<OrderItem>> orderItems = orderItemService.getAllOrderItems();
		return orderItems;
	}

	@Operation(summary = "Get an order item by ID")
    @ApiResponse(responseCode = "200", description = "The order item", content = @Content(schema = @Schema(implementation = OrderItem.class)))
	@GetMapping("/{id}")
	public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long id) {
		ResponseEntity<OrderItem> orderItem = orderItemService.getOrderItemById(id);
		return orderItem;
	}

	@Operation(summary = "Create a new order item")
    @ApiResponse(responseCode = "201", description = "Order item created", content = @Content(schema = @Schema(implementation = OrderItem.class)))
	@PostMapping
	public ResponseEntity<OrderItem> createOrderItem(@RequestBody OrderItem orderItem) {
		ResponseEntity<OrderItem> createdOrderItem = orderItemService.createOrderItem(orderItem);
		return createdOrderItem;
	}

	@Operation(summary = "Update an existing order item")
    @ApiResponse(responseCode = "200", description = "Order item updated", content = @Content(schema = @Schema(implementation = OrderItem.class)))
	@PutMapping("/{id}")
	public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Long id, @RequestBody OrderItem orderItem) {
		ResponseEntity<OrderItem> updatedOrderItem = orderItemService.updateOrderItem(id, orderItem);
		return updatedOrderItem;
	}

	@Operation(summary = "Delete an order item by ID")
    @ApiResponse(responseCode = "204", description = "Order item deleted")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {

		ResponseEntity<Void> resultEntity = orderItemService.deleteOrderItem(id);
		return resultEntity;
	}
}
