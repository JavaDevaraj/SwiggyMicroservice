package com.stackroute.deliveryagent.service;

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
import com.stackroute.deliveryagent.config.ConsumerType;
import com.stackroute.deliveryagent.domain.AgentStatus;
import com.stackroute.deliveryagent.domain.Order;
import com.stackroute.deliveryagent.domain.OrderStatus;
import com.stackroute.deliveryagent.exception.OrderNotFound;
import com.stackroute.deliveryagent.kafka.DeliveryOrderEventsProducer;
import com.stackroute.deliveryagent.repository.DeliveryAgentRepository;
@Service
public class AgentOrderServiceimpl implements AgentOrderService {
	
	private static final Logger logger = LoggerFactory.getLogger(AgentOrderServiceimpl.class);
	
	@Autowired
	private DeliveryAgentRepository deliveryAgentRepository;
	
	@Autowired
	DeliveryOrderEventsProducer deliveryOrderEventsProducer;
	
	@Autowired
	ObjectMapper objectMapper;

	public List<Order> getAllOrdersOfAgentId(String agentId) {
		
		List<Order> orders = null;
		
		if(StringUtils.isEmpty(agentId)) {
			orders = deliveryAgentRepository.findAll();
		} else {
			orders = deliveryAgentRepository.findOrdersByAgentId(agentId);
			if(CollectionUtils.isEmpty(orders)) {
				throw new OrderNotFound("No order for agentId :"+agentId);
			}
		}
		return orders;
	}

	public Order getOrderByOrderId(String orderId) {
		Optional<Order> order = deliveryAgentRepository.findById(orderId);
		if(!order.isPresent()) {
			throw new OrderNotFound("Order not found for given OrderId: "+orderId);
		}
		return order.get();
	}

	public Order orderArrive(Order orderInput, String orderId) {
		Optional<Order> order = deliveryAgentRepository.findById(orderId);
		if(!order.isPresent()) {
			throw new OrderNotFound("Order not found for given OrderId: "+orderId);
		}
		
		orderInput = deliveryAgentRepository.save(orderInput);
		try {
			deliveryOrderEventsProducer.sendOrderEvent(orderInput, ConsumerType.ORDER_SERVICE );
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			logger.error("Restaurant order error :"+orderInput.getOrderId());
		}
		return orderInput;
	}

	public Order orderPickup(Order orderInput, String orderId) {
		Optional<Order> order = deliveryAgentRepository.findById(orderId);
		if(!order.isPresent()) {
			throw new OrderNotFound("Order not found for given OrderId: "+orderId);
		}
		orderInput = deliveryAgentRepository.save(orderInput);
		try {
			deliveryOrderEventsProducer.sendOrderEvent(orderInput, ConsumerType.ORDER_SERVICE );
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			logger.error("Restaurant order error :"+orderInput.getOrderId());
		}
		return orderInput;
	}

	public Order orderDeliverd(Order orderInput, String orderId) {
		Optional<Order> order = deliveryAgentRepository.findById(orderId);
		if(!order.isPresent()) {
			throw new OrderNotFound("Order not found for given OrderId: "+orderId);
		}
		orderInput = deliveryAgentRepository.save(orderInput);
		try {
			deliveryOrderEventsProducer.sendOrderEvent(orderInput, ConsumerType.ORDER_SERVICE );
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			logger.error("Restaurant order error :"+orderInput.getOrderId());
		}
		return orderInput;
	}

	public Order processOrder(ConsumerRecord<String, String> consumerdata) {
		String orderValue = consumerdata.value();
		Order order = null;
		try {
			Order consumerOrder = objectMapper.readValue(orderValue, Order.class);
			order = deliveryAgentRepository.save(consumerOrder);
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
