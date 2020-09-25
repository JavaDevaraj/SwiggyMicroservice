package com.stackroute.deliveryagent.service;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.stackroute.deliveryagent.domain.Order;

public interface AgentOrderService {
	List<Order> getAllOrdersOfAgentId(String agentId);
	Order getOrderByOrderId(String orderId);
	Order orderArrive(Order order, String orderId);
	Order orderPickup(Order order, String orderId);
	Order orderDeliverd(Order order, String orderId);
	Order processOrder(ConsumerRecord<String, String> data);
}
