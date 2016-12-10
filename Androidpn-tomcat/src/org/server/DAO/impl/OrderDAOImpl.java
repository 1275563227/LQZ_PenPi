package org.server.DAO.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.server.DAO.OrderDao;
import org.server.domain.Order;
import org.server.util.C3P0Util;

public class OrderDAOImpl implements OrderDao {

	public void insertOrder(Order order) throws SQLException {
		System.out.println("OrderDAOImpl:" + order.toString());
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		qr.update("INSERT INTO orders VALUES(?,?,?,?,?,?,?,?,?)",
				order.getId(), order.getStart_place(), order.getEnd_place(),
				order.getName(), order.getPhone_number(), order.getCharges(),
				order.getRemark(), order.getState(), order.getDate());
	}

	public void deleteOrder(String id) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		qr.update("delete from orders where id=?", id);
	}

	public void alterOrder(Order order, String id) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		qr.update(
				"update orders set start_place=?,end_place=?,name=?,phone_number=?,charges=?,remark=?,state=?,date=? "
						+ "where id=?", order.getStart_place(),
				order.getEnd_place(), order.getName(), order.getPhone_number(),
				order.getCharges(), order.getRemark(), order.getState(),
				order.getDate(), id);
	}

	public void alterOrderState(String state, String id) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		qr.update("update orders set state=? where id=?", state, id);
	}

	public Order findOrderById(String id) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr.query("select * from orders where id=?",
				new BeanHandler<Order>(Order.class), id);
	}

	public List<Order> findAllOrder() throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr.query("select * from orders", new BeanListHandler<Order>(
				Order.class));
	}

	public List<Order> findOrderByState(String state) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr.query("select * from orders where state = ?",
				new BeanListHandler<Order>(Order.class), state);
	}

}
