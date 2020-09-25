package com.stackroute.restaurantpartner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.stackroute.restaurantpartner.domain.Order;
import com.stackroute.restaurantpartner.exception.OrderNotFoundException;
import com.stackroute.restaurantpartner.service.RestaurantOrderService;

@RestController
@RequestMapping(value = "/restaurant/api")
public class RestaurantOrderController {

	@Autowired
	private RestaurantOrderService restaurantOrderService;
	
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrdersOfRestaurant(@RequestParam("restaurantId") String restaurantId) {
        return ResponseEntity.ok(restaurantOrderService.getAllOrdersOfResturants(restaurantId));
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<Order> getOrderByOrderId(@PathVariable("orderId") String orderId) {
        return ResponseEntity.ok(restaurantOrderService.getOrderByOrderId(orderId));
    }

    @PutMapping(path="/orders/{orderId}/accept", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> acceptOrder( @PathVariable("orderId") String orderId, @RequestBody Order order) {
    	if(order == null || orderId == null || !order.getOrderId().equalsIgnoreCase(orderId)) {
    		throw new OrderNotFoundException("Order with given Id doesn't exist orderId :"+orderId+", order.id : "+order);
    	}
        return ResponseEntity.ok(restaurantOrderService.acceptOrder(order, orderId));
    }

    @PutMapping(path="/orders/{orderId}/reject", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> rejectOrder(@PathVariable("orderId") String orderId, @RequestBody Order order) {
    	if(order == null || orderId == null || !order.getOrderId().equalsIgnoreCase(orderId)) {
    		throw new OrderNotFoundException("Order with given Id doesn't exist orderId :"+orderId+", order.id : "+order);
    	}
        return ResponseEntity.ok(restaurantOrderService.rejectOrder(order, orderId));
    }

    @PutMapping(path="/orders/{orderId}/prepared", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> preparedOrder(@PathVariable("orderId") String orderId, @RequestBody Order order) {
    	if(order == null || orderId == null || !order.getOrderId().equalsIgnoreCase(orderId)) {
    		throw new OrderNotFoundException("Order with given Id doesn't exist orderId :"+orderId+", order.id : "+order);
    	}
        return ResponseEntity.ok(restaurantOrderService.preparedOrder(order, orderId));
    }

}
