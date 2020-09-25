package com.stackroute.restaurantpartner.service;

import java.util.Iterator;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingConsumerAwareMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.restaurantpartner.config.ConsumerType;
import com.stackroute.restaurantpartner.domain.Order;


@Component
public class RestaurantOrderEventsConsumer implements AcknowledgingConsumerAwareMessageListener<String, String>{
	
	private static final Logger logger = LoggerFactory.getLogger(RestaurantOrderEventsConsumer.class);

	@Autowired
	RestaurantOrderService restaurantOrderService;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@KafkaListener(topics= {"order-events"})
	public void onMessage(ConsumerRecord<String, String> data, Acknowledgment acknowledgment, Consumer<?, ?> consumer) {
		logger.info("RestaurantOrderEventsConsumer order: {}",data.value());
		Headers headers =  data.headers();
		if(isValidHeaderToProcess(headers)) {
			logger.info("RestaurantOrderEventsConsumer Process");
			Order order =  restaurantOrderService.processOrder(data);
			if(order != null )
			 acknowledgment.acknowledge();
		}
		
		
	}
	
	private boolean isValidHeaderToProcess(Headers headers) {
		Iterator<Header> iterator = headers.iterator();
		while(iterator.hasNext()) {
			Header next = iterator.next();
			String consumerType = new String(next.value());
			logger.info("ConsumerType.RESTAURANT_SERVICE : {}, Status : {}",consumerType, ConsumerType.RESTAURANT_SERVICE.toString().equals(consumerType));
			if (ConsumerType.RESTAURANT_SERVICE.toString().equals(consumerType)
					|| ConsumerType.ORDER_RESTAURANT_SERVICE.toString().equals(consumerType)
					|| ConsumerType.DELIVERY_RESTAURANT_SERVICE.toString().equals(consumerType)) {
				return true;
			}
		}
		return false;
	}

}
