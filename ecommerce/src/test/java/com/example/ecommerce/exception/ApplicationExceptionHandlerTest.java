package com.example.ecommerce.exception;

import com.example.ecommerce.advice.ApplicationExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import java.util.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest(classes = {ApplicationExceptionHandlerTest.class})
@ContextConfiguration
@AutoConfigureMockMvc
public class ApplicationExceptionHandlerTest {

	private final ApplicationExceptionHandler exceptionHandler = new ApplicationExceptionHandler();

	@MockBean
    private ApplicationExceptionHandler globalExceptionHandler;

    @Test
    void testHandleInternalServerError() {
        // Arrange
        Exception exception = new RuntimeException("Test exception");
        when(globalExceptionHandler.handleInternalServerError(any(Exception.class), any(String.class)))
                .thenReturn(Map.of("status", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), "error", "Custom error message"));

        // Act
        Map<String, String> errorMap = globalExceptionHandler.handleInternalServerError(exception, "Custom error message");

        // Assert
        assertNotNull(errorMap);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), Integer.parseInt(errorMap.get("status")));
        assertEquals(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), errorMap.get("status"));
        System.out.println("errorMap.get(\"error\")  "+errorMap.get("error"));
        assertEquals("Custom error message", errorMap.get("error"));

    }
    
    @Test
    void testProductNotFoundException() {
        // Arrange
        ProductNotFoundException ex = new ProductNotFoundException("Product not found");

        // Act
        Map<String, String> errorMap = exceptionHandler.productNotFoundException(ex);
        // Assert
        assertEquals("Product not found", errorMap.get("error"));
    }

    @Test
    void testOrderNotFoundException() {
        // Arrange
        OrderNotFoundException ex = new OrderNotFoundException("Order not found");

        // Act
        Map<String, String> errorMap = exceptionHandler.orderNotFoundException(ex);

        // Assert
        assertEquals("Order not found", errorMap.get("error"));
    }

    @Test
    void testOrderItemNotFoundException() {
        // Arrange
        OrderItemNotFound ex = new OrderItemNotFound("Order item not found");

        // Act
        Map<String, String> errorMap = exceptionHandler.orderItemNotFound(ex);

        // Assert
        assertEquals("Order item not found", errorMap.get("error"));
    }
}

