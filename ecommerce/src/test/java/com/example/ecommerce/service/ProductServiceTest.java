package com.example.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.http.ResponseEntity;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.exception.ProductNotFoundException;
import com.example.ecommerce.dto.*;
import com.example.ecommerce.repository.ProductRepository;

@SpringBootTest(classes = {ProductServiceTest.class})
@ContextConfiguration
@AutoConfigureMockMvc
public class ProductServiceTest {

	   @Mock
	    private ProductRepository productRepository;

	    @InjectMocks
	    private ProductService productService;

	    @Test
	    void testGetAllProducts() {
	        // Arrange
	        List<Product> products = new ArrayList<>();
	        when(productRepository.findAll()).thenReturn(products);

	        // Act
	        ResponseEntity<List<Product>> responseEntity = productService.getAllProducts();

	        // Assert
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	        assertNotNull(responseEntity.getBody());
	        assertEquals(products, responseEntity.getBody());
	    }

	    @Test
	    void testGetProductById_ProductFound() {
	        // Arrange
	        Long productId = 1L;
	        Product product = new Product();
	        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

	        // Act
	        ResponseEntity<Product> responseEntity = productService.getProductById(productId);

	        // Assert
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	        assertNotNull(responseEntity.getBody());
	        assertEquals(product, responseEntity.getBody());
	    }

	    @Test
	    void testGetProductById_ProductNotFound() {
	        // Arrange
	        Long productId = 1L;
	        when(productRepository.findById(productId)).thenReturn(Optional.empty());

	        // Act and Assert
	        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(productId));
	    }

	    @Test
	    void testCreateProduct() {
	        // Arrange
	        ProductRequestDTO productRequestDTO = new ProductRequestDTO();
	        Product createdProduct = new Product();
	        when(productRepository.save(any(Product.class))).thenReturn(createdProduct);

	        // Act
	        ResponseEntity<Product> responseEntity = productService.createProduct(productRequestDTO);

	        // Assert
	        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	        assertNotNull(responseEntity.getBody());
	        assertEquals(createdProduct, responseEntity.getBody());
	    }

	    @Test
	    void testUpdateProduct_ProductFound() {
	        // Arrange
	        ProductUpdateRequestDTO updatedProductDTO = new ProductUpdateRequestDTO();
	        updatedProductDTO.setId(1L);
	        Product existingProduct = new Product();
	        when(productRepository.findById(updatedProductDTO.getId())).thenReturn(Optional.of(existingProduct));
	        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

	        // Act
	        ResponseEntity<Product> responseEntity = productService.updateProduct(updatedProductDTO);

	        // Assert
	        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	        assertNotNull(responseEntity.getBody());
	        assertEquals(existingProduct, responseEntity.getBody());
	        
	        
	    }

	    @Test
	    void testUpdateProduct_ProductNotFound() {
	        // Arrange
	        ProductUpdateRequestDTO updatedProductDTO = new ProductUpdateRequestDTO();
	        updatedProductDTO.setId(1L);
	        when(productRepository.findById(updatedProductDTO.getId())).thenReturn(Optional.empty());

	        // Act and Assert
	        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(updatedProductDTO));
	    }

	    @Test
	    void testDeleteProduct_ProductFound() {
	        // Arrange
	        Long productId = 1L;
	        Product existingProduct = new Product();
	        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

	        // Act
	        ResponseEntity<Void> responseEntity = productService.deleteProduct(productId);

	        // Assert
	        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
	        verify(productRepository, times(1)).deleteById(productId);
	    }

	    @Test
	    void testDeleteProduct_ProductNotFound() {
	        // Arrange
	        Long productId = 1L;
	        when(productRepository.findById(productId)).thenReturn(Optional.empty());

	        // Act and Assert
	        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(productId));
	    }

}
