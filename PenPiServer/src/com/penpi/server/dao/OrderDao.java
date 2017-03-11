package com.penpi.server.dao;

import java.util.List;

import com.penpi.server.domain.Order;

public interface OrderDao {

	public boolean insertOrder(Order order);

	public boolean deleteOrder(Order order);

	public Order findOrderByID(int ID);

	public List<Order> findAllOrder();

	public List<Order> findOrderByState(String state);

	public Order alterOrderState(Order order);
}
