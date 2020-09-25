package com.stackroute.restaurantpartner.service;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.stackroute.restaurantpartner.domain.Order;

public interface RestaurantOrderService {
	Order getOrderByOrderId(String orderId);
	Order acceptOrder(Order order, String orderId);
	Order preparedOrder(Order order, String orderId);
	Order rejectOrder(Order order, String orderId);
	List<Order> getAllOrdersOfResturants(String resturantId);
	Order processOrder(ConsumerRecord<String, String> data);
}
