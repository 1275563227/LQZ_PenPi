package org.server.DAO;

import java.sql.SQLException;
import java.util.List;

import org.server.domain.Order;


public interface OrderDao {

	public void insertOrder(Order order) throws SQLException;
	
	public void deleteOrder(String id) throws SQLException;
	
	public void alterOrder(Order order, String id) throws SQLException;
	
	public void alterOrderState(String state, String id) throws SQLException;
	
	public Order findOrderById(String id) throws SQLException;
	
	public List<Order> findAllOrder() throws SQLException;

	public List<Order> findOrderByState(String state) throws SQLException;
	
}
