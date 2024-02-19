package com.example.ecommerce.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.dto.*;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.service.ProductServiceTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@SpringBootTest(classes = {ProductServiceTest.class})
@ContextConfiguration
@AutoConfigureMockMvc
public class ProductControllerTest {
	@Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    void test_getAllProducts_Controller() {
        // Arrange
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(productService.getAllProducts()).thenReturn(ResponseEntity.ok(products));

        // Act
        ResponseEntity<List<Product>> response = productController.getAllProducts();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
    }

    @Test
    void test_getProductById_Controller() {
        // Arrange
        Long productId = 1L;
        Product product = new Product();
        when(productService.getProductById(productId)).thenReturn(ResponseEntity.ok(product));

        // Act
        ResponseEntity<Product> response = productController.getProductById(productId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
        
  
    }

    @Test
    void test_createProduct_Controller() {
        // Arrange
        ProductRequestDTO productRequestDTO = new ProductRequestDTO();
        Product createdProduct = new Product();
        when(productService.createProduct(productRequestDTO)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(createdProduct));

        // Act
        ResponseEntity<Product> response = productController.createProduct(productRequestDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdProduct, response.getBody());
    }

    @Test
    void test_updateProduct_Controller() {
        // Arrange
        ProductUpdateRequestDTO productUpdateRequestDTO = new ProductUpdateRequestDTO();
        Product updatedProduct = new Product();
        when(productService.updateProduct(productUpdateRequestDTO)).thenReturn(ResponseEntity.ok(updatedProduct));

        // Act
        ResponseEntity<Product> response = productController.updateProduct(productUpdateRequestDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedProduct, response.getBody());
    }

    @Test
    void test_deleteProduct_Controller()  {
        // Arrange
        Long productId = 1L;
        when(productService.deleteProduct(productId)).thenReturn(ResponseEntity.noContent().build());

        // Act
        ResponseEntity<Void> response = productController.deleteProduct(productId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
    
    
}
