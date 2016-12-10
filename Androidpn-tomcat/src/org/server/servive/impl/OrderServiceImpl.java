package org.server.servive.impl;

import java.sql.SQLException;
import java.util.List;

import org.server.DAO.OrderDao;
import org.server.DAO.impl.OrderDAOImpl;
import org.server.domain.Order;


public class OrderServiceImpl implements org.server.service.OrderService {

	public void insertOrder(Order order) {
		OrderDao orderDao = new OrderDAOImpl();
		try {
			orderDao.insertOrder(order);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteOrder(String id) {
		OrderDao orderDao = new OrderDAOImpl();
		try {
			orderDao.deleteOrder(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void alterOrder(Order order, String id) {
		OrderDao orderDao = new OrderDAOImpl();
		try {
			orderDao.alterOrder(order, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void alterOrderState(String state, String id) {
		OrderDao orderDao = new OrderDAOImpl();
		try {
			orderDao.alterOrderState(state, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Order findOrderById(String id) {
		OrderDao orderDao = new OrderDAOImpl();
		try {
			return orderDao.findOrderById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Order> findAllOrder() {
		OrderDao orderDao = new OrderDAOImpl();
		try {
			return orderDao.findAllOrder();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Order> findOrderByState(String state) {
		OrderDao orderDao = new OrderDAOImpl();
		try {
			return orderDao.findOrderByState(state);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
