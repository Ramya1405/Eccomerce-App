package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductRequestDTO;
import com.example.ecommerce.dto.ProductUpdateRequestDTO;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.exception.ProductNotFoundException;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

	private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<List<Product>> getAllProducts() {
            List<Product> products = productRepository.findAll();
            return ResponseEntity.ok(products);
        
    }

    public ResponseEntity<Product> getProductById(Long productId) {
    
            Optional<Product> existingProduct = productRepository.findById(productId);
		    
		    if (existingProduct.isPresent()) {
		        return ResponseEntity.ok(existingProduct.get());
		    } else {
		        throw new ProductNotFoundException("Product with ID " + productId + " not found");
		    }
        
    }

    public ResponseEntity<Product> createProduct(@RequestBody ProductRequestDTO productRequestDTO) {
            // Create a new product using the provided DTO
            Product product = new Product();
            product.setName(productRequestDTO.getName());
            product.setPrice(productRequestDTO.getPrice());
            // Save the newly created product
            Product createdProduct = productRepository.save(product);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .header("success", "Product created successfully.").body(createdProduct);
       
    }

    public ResponseEntity<Product> updateProduct(ProductUpdateRequestDTO updatedProduct) {
            Optional<Product> existingProduct = productRepository.findById(updatedProduct.getId());
            if (existingProduct.isPresent()) {
                Product productToUpdate = existingProduct.get();
                productToUpdate.setName(updatedProduct.getName());
                productToUpdate.setPrice(updatedProduct.getPrice());
                Product updated = productRepository.save(productToUpdate);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .header("success", "Product updated successfully.").body(updated);
            }else {
		        throw new ProductNotFoundException("Product with ID " + updatedProduct.getId() + " not found");
		    }
            
       
    }

    public ResponseEntity<Void> deleteProduct(Long productId) {
            Optional<Product> existingProduct = productRepository.findById(productId);
        	if (existingProduct.isPresent()) {
				productRepository.deleteById(productId);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).header("success", "Product deleted successfully.")
						.build();
			} else {
				// Throw exception the Order is not found
				throw new ProductNotFoundException("Product with ID " + productId + " not found");
			}
        
    }
}