package com.penpi.server.service;

import java.io.PrintWriter;
import java.util.List;

import com.penpi.server.domain.Order;

public interface OrderService {

	public void setWriter(PrintWriter writer);
	
	public boolean saveOrder(Order order);

	public boolean deleteOrder(Order order);

	public Order findOrderByID(int ID);

	public List<Order> findAllOrder();

	public List<Order> findOrderByState(String state);

	public boolean alterOrder(Order order);
}
