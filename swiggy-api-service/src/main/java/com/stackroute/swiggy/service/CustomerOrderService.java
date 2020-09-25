package com.stackroute.swiggy.service;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.stackroute.swiggy.domain.Order;
import com.stackroute.swiggy.exception.OrderNotFound;

public interface CustomerOrderService {
	
	Order findByOrderId(String orderId) throws OrderNotFound;
	
	List<Order> findByUserId(String userId);
	
	Order saveOrder(Order order);
	
	Order processOrder(ConsumerRecord<String, String> consumerdata);
	
}
