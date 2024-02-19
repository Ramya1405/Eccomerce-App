package com.example.ecommerce.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.ecommerce.dto.OrderRequest;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.OrderItem;
import com.example.ecommerce.service.OrderService;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Get a list of all orders")
    @ApiResponse(responseCode = "200", description = "List of orders", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Order.class))))
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        ResponseEntity<List<Order>> orders = orderService.getAllOrders();
        return orders;
    }

    @Operation(summary = "Get an order by ID")
    @ApiResponse(responseCode = "200", description = "The order", content = @Content(schema = @Schema(implementation = Order.class)))
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        ResponseEntity<Order> order = orderService.getOrderById(id);
        return order;
    }

    @Operation(summary = "Create a new order")
    @ApiResponse(responseCode = "201", description = "Order created", content = @Content(schema = @Schema(implementation = Order.class)))
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) {
        ResponseEntity<Order> createdOrder = orderService.createOrder(orderRequest);
        return createdOrder;
    }

    @Operation(summary = "Update an existing order")
    @ApiResponse(responseCode = "200", description = "Order updated", content = @Content(schema = @Schema(implementation = Order.class)))
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody OrderItem orderItem) {
        ResponseEntity<Order> updatedOrder = orderService.updateOrder(id,orderItem);
        return updatedOrder;
    }

    @Operation(summary = "Delete an order by ID")
    @ApiResponse(responseCode = "204", description = "Order deleted")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
    	ResponseEntity<Void>  resultEntity = orderService.deleteOrder(id);
        return resultEntity;
    }
}

