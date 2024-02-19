package com.example.ecommerce.exception;
import java.io.Serializable;

public class OrderItemNotFound extends RuntimeException implements Serializable {
    
	private static final long serialVersionUID = 1L;
    public OrderItemNotFound(String message) {
        super(message);
    }
}