package com.example.ecommerce;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import java.math.BigDecimal;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import com.example.ecommerce.dto.*;



@SpringBootTest(classes = {OrderLineItemsDtoTest.class})
@ContextConfiguration
@AutoConfigureMockMvc
public class OrderLineItemsDtoTest {


	    @Test
	    void testOrderLineItemsDtoConstructor() {
	        // Arrange
	        Long id = 1L;
	        String skuCode = "SKU123";
	        BigDecimal price = new BigDecimal("50.00");
	        Integer quantity = 2;

	        // Act
	        OrderLineItemsDto orderLineItemsDto = new OrderLineItemsDto(id, skuCode, price, quantity);

	        // Assert
	        assertEquals(id, orderLineItemsDto.getId());
	        assertEquals(skuCode, orderLineItemsDto.getSkuCode());
	        assertEquals(price, orderLineItemsDto.getPrice());
	        assertEquals(quantity, orderLineItemsDto.getQuantity());
	    }
	    
}
