package com.example.ecommerce.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="order_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

	
	@Id
	private Long id;
	
	private String skuCode;
	
	private BigDecimal price;
	
	private Integer quantity;
	
	
}