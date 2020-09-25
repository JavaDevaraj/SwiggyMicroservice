package com.stackroute.restaurantpartner.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.restaurantpartner.config.ConsumerType;
import com.stackroute.restaurantpartner.domain.Order;

@Component
public class RestaurantOrderEventsProducer {
	
	private static final Logger logger = LoggerFactory.getLogger(RestaurantOrderEventsProducer.class);
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	ObjectMapper objectMapper;
	
	private static String TOPIC ="order-events";
	
	public ListenableFuture<SendResult<String, String>> sendOrderEvent(Order order, ConsumerType consumerType) throws JsonProcessingException {
		String key = order.getOrderId();
		String value = objectMapper.writeValueAsString(order);
		ProducerRecord<String, String> producerRecord = createProducerRecord(key, value, consumerType.toString());
		//read topic from application.properties
		ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(producerRecord);
		listenableFutureCallback(key, value, listenableFuture);
		return listenableFuture;
	}
	
	private ProducerRecord<String, String> createProducerRecord(String key, String value, String consumerType) {
		// Header to pass additional information & consumer can consume based on header
		List<Header> recordHeader = new ArrayList();
		recordHeader.add(new RecordHeader("consumerType", consumerType.getBytes()));
		
		return new ProducerRecord<String, String>(TOPIC, null, key, value, recordHeader);
	}
	
	private void listenableFutureCallback(final String key, final String value, ListenableFuture<SendResult<String, String>> listenableFuture) {
		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
			public void onSuccess(SendResult<String, String> result) {
				logger.info("Message Sent SuccessFully for the key : {} and the value is {} , partition is {}", key, value, result.getRecordMetadata().partition());
			}
			
			public void onFailure(Throwable ex) {
				logger.error("Error while sending the message & exception is {}", ex.getMessage());
				
			}
		});
	}

	
}
