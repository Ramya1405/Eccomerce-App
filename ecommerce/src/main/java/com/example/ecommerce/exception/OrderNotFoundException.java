package com.example.ecommerce.exception;

import java.io.Serializable;

public class OrderNotFoundException extends RuntimeException implements Serializable {
    
	private static final long serialVersionUID = 1L;
    public OrderNotFoundException(String message) {
        super(message);
    }
}