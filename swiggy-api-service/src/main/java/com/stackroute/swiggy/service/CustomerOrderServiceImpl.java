package com.stackroute.swiggy.service;

import java.util.List;
import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.swiggy.config.ConsumerType;
import com.stackroute.swiggy.domain.Order;
import com.stackroute.swiggy.domain.OrderStatus;
import com.stackroute.swiggy.exception.CustomerNotFoundException;
import com.stackroute.swiggy.exception.OrderNotFound;
import com.stackroute.swiggy.repository.CustomerOrderRepository;
@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerOrderServiceImpl.class);

	@Autowired
	private CustomerOrderRepository orderRepository;
	
	@Autowired
	private OrderEventsProducer orderEventsProducer;
	
	@Autowired
	ObjectMapper objectMapper;
	
	public Order findByOrderId(String orderId) throws OrderNotFound {
		Optional<Order> order = orderRepository.findById(orderId);
		
		if(!order.isPresent())
			throw new OrderNotFound("Specified orderId doesn't exist");
		return order.get();
	}

	public List<Order> findByUserId(String userId) {
		if(StringUtils.isEmpty(userId)) {
			return orderRepository.findAll();
		}
		List<Order> orders = orderRepository.findByUserId(userId);
		if(CollectionUtils.isEmpty(orders)) {
			throw new CustomerNotFoundException("User doesn't have any order");
		}
		return orders;
	}

	public Order saveOrder(Order order) {
		order.setOrderStatus(OrderStatus.CREATED);
		order.setOrderTime(System.currentTimeMillis());
		order.setTotalPrice(order.calculateTotalPrice(order.getItems()));
		order.setDeliveryTime(System.currentTimeMillis()+1000*60*30);
		order = orderRepository.save(order);
		try {
			orderEventsProducer.sendOrderEvent(order, ConsumerType.RESTAURANT_SERVICE);
		} catch (JsonProcessingException e) {
			logger.error("ERROR while posting order :"+e.getMessage());
		}
		return order;
	}

	public Order processOrder(ConsumerRecord<String, String> consumerdata) {
		String orderValue = consumerdata.value();
		Order order = null;
		try {
			Order consumerOrder = objectMapper.readValue(orderValue, Order.class);
			order = orderRepository.save(consumerOrder);
			
		} catch (JsonMappingException e) {
			logger.error("JsonMappingException processOrder : {}", e.getMessage());
		} catch (JsonProcessingException e) {
			logger.error("JsonProcessingException processOrder : {}", e.getMessage());
		}
		return order;
	}
}
