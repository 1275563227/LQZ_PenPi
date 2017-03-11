package com.penpi.server.dao.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.penpi.server.dao.OrderDao;
import com.penpi.server.domain.Order;

public class OrderDaoImol extends HibernateDaoSupport implements OrderDao {

	@Override
	public boolean insertOrder(Order order) {
		try {
			order.setSendOrderDate(new Date());
			super.getHibernateTemplate().save(order);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteOrder(Order order) {
		try {
			super.getHibernateTemplate().delete(order);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Order findOrderByID(int ID) {
		Order order = super.getHibernateTemplate().get(Order.class, ID);
		if (order != null)
			return order;
		return null;
	}

	@Override
	public List<Order> findAllOrder() {
		@SuppressWarnings("unchecked")
		List<Order> orders = super.getHibernateTemplate().find("from Order");
		if (orders.size() > 0)
			return orders;
		return null;
	}

	@Override
	public List<Order> findOrderByState(String state) {
		try {
			@SuppressWarnings("unchecked")
			List<Order> orders = super.getHibernateTemplate().find(
					"from Order where state=?", state);
			if (orders.size() > 0)
				return orders;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Order alterOrderState(Order order) {
		Order temp = findOrderByID(order.getOrderID());
		if (order.getState() != temp.getState()) {
			if ("未抢".equals(temp.getState())) {
				temp.setTakeOrderPeople(order.getTakeOrderPeople());
				temp.setTakeOrderDate(new Date());
			}
			temp.setState(order.getState());
		}
		return temp;
	}
}
