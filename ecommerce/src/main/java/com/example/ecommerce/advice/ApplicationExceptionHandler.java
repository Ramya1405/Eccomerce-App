package com.example.ecommerce.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.ecommerce.exception.OrderItemNotFound;
import com.example.ecommerce.exception.OrderNotFoundException;
import com.example.ecommerce.exception.ProductNotFoundException;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class ApplicationExceptionHandler {
	
	
	 @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	    @ExceptionHandler(ProductNotFoundException.class)
	    public Map<String, String> productNotFoundException(ProductNotFoundException ex) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put("status", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
	        errorMap.put("error", ex.getMessage());
	        return errorMap;
	    }

	    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	    @ExceptionHandler(OrderNotFoundException.class)
	    public Map<String, String> orderNotFoundException(OrderNotFoundException ex) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put("status", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
	        errorMap.put("error", ex.getMessage());
	        return errorMap;
	    }

	    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	    @ExceptionHandler(OrderItemNotFound.class)
	    public Map<String, String> orderItemNotFound(OrderItemNotFound ex) {
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put("status", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
	        errorMap.put("error", ex.getMessage());
	        return errorMap;
	    }
	
	    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	    @ExceptionHandler(Exception.class)
		public Map<String, String> handleInternalServerError(Exception e, String errorMessage) {
	        e.printStackTrace();
	        Map<String, String> errorMap = new HashMap<>();
	        errorMap.put("status", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
	        errorMap.put("error", errorMessage);
	        return errorMap;
	    }
}
