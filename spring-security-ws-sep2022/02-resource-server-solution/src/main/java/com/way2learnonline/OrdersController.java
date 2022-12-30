package com.way2learnonline;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrdersController {
	
	@GetMapping("/orders")
	public List<Order> getOrders() {
		
		Order order1 = new Order(UUID.randomUUID().toString(),
				"product-id-1", "user-id-1", 1, OrderStatus.NEW);
		
		Order order2 = new Order(UUID.randomUUID().toString(),
				"product-id-2", "user-id-1", 1, OrderStatus.NEW);
		
		return Arrays.asList(order1, order2);
	}

}
