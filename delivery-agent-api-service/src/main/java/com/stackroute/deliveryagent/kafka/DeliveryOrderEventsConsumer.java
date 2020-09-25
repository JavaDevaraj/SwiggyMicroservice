package com.stackroute.deliveryagent.kafka;

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
import com.stackroute.deliveryagent.config.ConsumerType;
import com.stackroute.deliveryagent.domain.Order;
import com.stackroute.deliveryagent.service.AgentOrderService;



@Component
public class DeliveryOrderEventsConsumer implements AcknowledgingConsumerAwareMessageListener<String, String>{

	private static final Logger logger = LoggerFactory.getLogger(DeliveryOrderEventsConsumer.class);

	@Autowired
	AgentOrderService agentOrderService;

	@Autowired
	ObjectMapper objectMapper;

	@KafkaListener(topics= {"order-events"})
	public void onMessage(ConsumerRecord<String, String> data, Acknowledgment acknowledgment, Consumer<?, ?> consumer) {
		Headers headers =  data.headers();
		logger.info("AgentOrderServiceConsumer order: {}",data.value());
		if(isValidHeaderToProcess(headers)) {
			logger.info("AgentOrderServiceConsumer process");
			Order order =  agentOrderService.processOrder(data);
			if(order != null )
				acknowledgment.acknowledge();
		}
	}

	private boolean isValidHeaderToProcess(Headers headers) {
		Iterator<Header> iterator = headers.iterator();
		while(iterator.hasNext()) {
			Header next = iterator.next();
			String consumerType = new String(next.value());
			logger.info("ConsumerType.RESTAURANT_SERVICE : {}, Status : {}",consumerType, ConsumerType.ORDER_SERVICE.toString().equals(consumerType));
			if (ConsumerType.DELIVERY_SERVICE.toString().equals(consumerType)
					|| ConsumerType.ORDER_DELIVERY_SERVICE.toString().equals(consumerType)
					|| ConsumerType.DELIVERY_RESTAURANT_SERVICE.toString().equals(consumerType)) {
				return true;
			}
		}
		return false;
	}

}
