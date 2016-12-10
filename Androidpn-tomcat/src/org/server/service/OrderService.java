package org.server.service;

import java.util.List;

import org.server.domain.Order;


public interface OrderService {
	
	public void insertOrder(Order order);
	
	public void deleteOrder(String id);
	
	public void alterOrder(Order order, String id);
	
	public void alterOrderState(String state, String id);
	
	public Order findOrderById(String id);
	
	public List<Order> findOrderByState(String state);
	
	public List<Order> findAllOrder();
}
