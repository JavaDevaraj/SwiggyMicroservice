package com.stackroute.deliveryagent.repository;

import com.stackroute.deliveryagent.domain.Order;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryAgentRepository extends MongoRepository<Order, String> {
	@Query("{'agent.agentId':?0}")
	List<Order> findOrdersByAgentId(String agentId);
}

