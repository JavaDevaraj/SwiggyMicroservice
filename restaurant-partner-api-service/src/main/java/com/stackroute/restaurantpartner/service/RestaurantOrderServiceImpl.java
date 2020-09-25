package com.stackroute.restaurantpartner.service;

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
import com.stackroute.restaurantpartner.config.ConsumerType;
import com.stackroute.restaurantpartner.domain.Agent;
import com.stackroute.restaurantpartner.domain.AgentStatus;
import com.stackroute.restaurantpartner.domain.Order;
import com.stackroute.restaurantpartner.domain.OrderStatus;
import com.stackroute.restaurantpartner.exception.OrderNotFoundException;
import com.stackroute.restaurantpartner.exception.RestaurantNotExist;
import com.stackroute.restaurantpartner.repository.RestaurantOrderRepository;


@Service
public class RestaurantOrderServiceImpl implements RestaurantOrderService {
	
	private static final Logger logger = LoggerFactory.getLogger(RestaurantOrderServiceImpl.class);
	
	
	@Autowired
	private RestaurantOrderRepository restaurantOrderRepository;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	RestaurantOrderEventsProducer restaurantOrderEventsProducer;

	public Order getOrderByOrderId(String orderId) {
		// TODO Auto-generated method stub
		Order order = restaurantOrderRepository.findByOrderId(orderId);
		logger.debug("Order exist: "+order);
		if(order == null )
			throw new OrderNotFoundException("Order with given orderId is not found : "+ orderId);
		
		return order;
	}

	public Order acceptOrder(Order orderInput, String orderId) {
		Order order = restaurantOrderRepository.findByOrderId(orderId);
		if(order == null )
			throw new OrderNotFoundException("Order with given orderId is not found : "+ orderId);
		
		orderInput = restaurantOrderRepository.save(orderInput);
		try {
			restaurantOrderEventsProducer.sendOrderEvent(orderInput, ConsumerType.ORDER_DELIVERY_SERVICE );
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			logger.error("Restaurant order error :"+orderInput.getOrderId());
		}
		return orderInput;
	}

	public Order preparedOrder(Order orderInput, String orderId) {
		Order order = restaurantOrderRepository.findByOrderId(orderId);
		if(order == null)
			throw new OrderNotFoundException("Order with given orderId is not found : "+ orderId);
		
		orderInput = restaurantOrderRepository.save(orderInput);
		try {
			restaurantOrderEventsProducer.sendOrderEvent(orderInput, ConsumerType.ORDER_DELIVERY_SERVICE );
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			logger.error("Restaurant order error :"+orderInput.getOrderId());
		}
		return orderInput;
	}

	public Order rejectOrder(Order orderInput, String orderId) {
		Order order = restaurantOrderRepository.findByOrderId(orderId);
		if(order == null)
			throw new OrderNotFoundException(",Order with given orderId is not found : "+ orderId);
		
		orderInput = restaurantOrderRepository.save(orderInput);
		try {
			restaurantOrderEventsProducer.sendOrderEvent(orderInput, ConsumerType.ORDER_SERVICE );
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			logger.error("Restaurant order error :"+orderInput.getOrderId());
		}
		return orderInput;
	}

	public List<Order> getAllOrdersOfResturants(String resturantId) {
		List<Order> orders = null;
		if(StringUtils.isEmpty(resturantId)) {
			orders = restaurantOrderRepository.findAll();
			return orders;
		} else {
			orders = restaurantOrderRepository.findOrdersByResturantId(resturantId);
			if(CollectionUtils.isEmpty(orders)) {
				throw new RestaurantNotExist(",NO orders for given resturant Id: "+resturantId);
			}
			return restaurantOrderRepository.findOrdersByResturantId(resturantId);
		}
	}

	public Order processOrder(ConsumerRecord<String, String> consumerdata) {
		String orderValue = consumerdata.value();
		Order order = null;
		try {
			Order consumerOrder = objectMapper.readValue(orderValue, Order.class);
			order = restaurantOrderRepository.save(consumerOrder);
			
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return order;
	}
}
