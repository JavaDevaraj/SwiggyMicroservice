package com.stackroute.restaurantpartner.repository;

import com.stackroute.restaurantpartner.domain.Order;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantOrderRepository extends MongoRepository<Order,String> {
	
	@Query("{'restaurantId':?0}")
	List<Order> findOrdersByResturantId(String resturantId);

	@Query("{'orderId':?0}")
	Order findByOrderId(String orderId);
}


