// ProductController.java
package com.example.ecommerce.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.ecommerce.dto.ProductRequestDTO;
import com.example.ecommerce.dto.ProductUpdateRequestDTO;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.service.ProductService;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Get a list of all products")
    @ApiResponse(responseCode = "200", description = "List of products", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Product.class))))
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        ResponseEntity<List<Product>> products = productService.getAllProducts();
        return products;
    }

    @Operation(summary = "Get a product by ID")
    @ApiResponse(responseCode = "200", description = "The product", content = @Content(schema = @Schema(implementation = Product.class)))
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        ResponseEntity<Product> product = productService.getProductById(id);
        return product;
    }

    @Operation(summary = "Create a new product")
    @ApiResponse(responseCode = "201", description = "Product created", content = @Content(schema = @Schema(implementation = Product.class)))
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequestDTO product) {
        ResponseEntity<Product> createdProduct = productService.createProduct(product);
        return createdProduct;
    }

    @Operation(summary = "Update an existing product")
    @ApiResponse(responseCode = "200", description = "Product updated", content = @Content(schema = @Schema(implementation = Product.class)))
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody ProductUpdateRequestDTO product) {
        ResponseEntity<Product> updatedProduct = productService.updateProduct(product);
        return updatedProduct;
    }

    @Operation(summary = "Delete a product by ID")
    @ApiResponse(responseCode = "204", description = "Product deleted")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
   
    	ResponseEntity<Void>  resultEntity = productService.deleteProduct(id);
		
        return resultEntity;
    }
  
}
