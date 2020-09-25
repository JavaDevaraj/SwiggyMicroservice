package com.stackroute.deliveryagent.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.deliveryagent.domain.Order;
import com.stackroute.deliveryagent.exception.OrderNotFound;
import com.stackroute.deliveryagent.service.AgentOrderService;

@RestController
@RequestMapping(value = "/delivery/api")
public class AgentOrderController {
	
	@Autowired
	private AgentOrderService agentOrderService;

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrdersOfAgent(@RequestParam("agentId") String agentId) {
        return ResponseEntity.ok(agentOrderService.getAllOrdersOfAgentId(agentId));
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<Order> getOrderByOrderId(@PathVariable("orderId") String orderId) {
        return ResponseEntity.ok(agentOrderService.getOrderByOrderId(orderId));
    }

    @PutMapping("/orders/{orderId}/arrive")
    public ResponseEntity<Order> orderArrive(@RequestBody Order order, @PathVariable("orderId") String orderId ) {
    	if(order == null || orderId == null || !order.getOrderId().equalsIgnoreCase(orderId)) {
    		throw new OrderNotFound("Order with given Id doesn't exist orderId :"+orderId+", order.id : "+order);
    	}
        return ResponseEntity.ok(agentOrderService.orderArrive(order, orderId));
    }

    @PutMapping("/orders/{orderId}/pickup")
    public ResponseEntity<Order> orderPickup(@RequestBody Order order, @PathVariable("orderId") String orderId) {
    	if(order == null || orderId == null || !order.getOrderId().equalsIgnoreCase(orderId)) {
    		throw new OrderNotFound("Order with given Id doesn't exist orderId :"+orderId+", order.id : "+order);
    	}
        return ResponseEntity.ok(agentOrderService.orderPickup(order, orderId));
    }

    @PutMapping("/orders/{orderId}/deliver")
    public ResponseEntity<Order> orderDeliverd(@RequestBody Order order, @PathVariable("orderId") String orderId) {
    	if(order == null || orderId == null || !order.getOrderId().equalsIgnoreCase(orderId)) {
    		throw new OrderNotFound("Order with given Id doesn't exist orderId :"+orderId+", order.id : "+order);
    	}
        return ResponseEntity.ok(agentOrderService.orderDeliverd(order, orderId));
    }
}
