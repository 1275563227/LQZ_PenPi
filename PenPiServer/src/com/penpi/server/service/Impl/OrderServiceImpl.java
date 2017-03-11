package com.penpi.server.service.Impl;

import java.io.PrintWriter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.penpi.server.dao.OrderDao;
import com.penpi.server.domain.Order;
import com.penpi.server.service.OrderService;
import com.penpi.server.utils.InfoBean;
import com.penpi.server.utils.JPushUtils;
import com.penpi.server.utils.JacksonUtils;

public class OrderServiceImpl implements OrderService {

	private OrderDao orderDao;

	private PrintWriter writer;
	private final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);
	private final String title = "Penpi快递直通车";
	private InfoBean info = new InfoBean();

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	@Override
	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}

	@Override
	public boolean saveOrder(Order order) {
		if (orderDao.insertOrder(order)) {
			info = new InfoBean();
			info.setValidate(true);
			String jsonData = JacksonUtils.writeJSON(info);
			writer.write(jsonData);
			LOG.info("【返回】->" + jsonData);
			LOG.info("saveOrder成功！！");
			// -----------推送------------
			JPushUtils.push(order.getSendOrderPeopleName() + "提交了一个新订单", title,
					null);
			return true;
		} else {
			info = new InfoBean();
			writer.write(JacksonUtils.writeJSON(info));
			LOG.error("saveOrder失败！！");
			return false;
		}
	}

	@Override
	public boolean deleteOrder(Order order) {
		if (orderDao.deleteOrder(order)) {
			info = new InfoBean();
			info.setValidate(true);
			String jsonData = JacksonUtils.writeJSON(info);
			writer.write(jsonData);
			LOG.info("【返回】->" + jsonData);
			LOG.info("deleteOrder成功！！");
			return true;
		} else {
			info = new InfoBean();
			writer.write(JacksonUtils.writeJSON(info));
			LOG.error("deleteOrder失败！！");
			return false;
		}
	}

	@Override
	public Order findOrderByID(int ID) {
		Order orderByID = orderDao.findOrderByID(ID);
		if (orderByID != null) {
			info = new InfoBean();
			info.setValidate(true);
			info.setReturnInfo(JacksonUtils.writeJSON(orderByID));
			String jsonData = JacksonUtils.writeJSON(info);
			writer.write(jsonData);
			LOG.info("【返回】->" + jsonData);
			LOG.info("findOrderByID查询成功！！");
			return orderByID;
		} else {
			info = new InfoBean();
			writer.write(JacksonUtils.writeJSON(info));
			LOG.error("findOrderByID查询失败！！");
		}
		return null;
	}

	@Override
	public List<Order> findAllOrder() {
		List<Order> orders = orderDao.findAllOrder();
		if (orders != null && orders.size() != 0) {
//			info = new InfoBean();
//			info.setValidate(true);
//			info.setReturnInfo(JacksonUtils.writeJSON(orders));
//			String jsonData = JacksonUtils.writeJSON(info);
//			writer.write(jsonData);
//			LOG.info("【返回】->" + jsonData);
			LOG.info("findAllOrder查询成功！！");
			return orders;
		} else {
//			info = new InfoBean();
//			writer.write(JacksonUtils.writeJSON(info));
			LOG.error("findAllOrder查询失败！！");
		}
		return null;
	}

	@Override
	public List<Order> findOrderByState(String state) {
		List<Order> orders = orderDao.findOrderByState(state);
		if (orders != null && orders.size() != 0) {
			info = new InfoBean();
			info.setValidate(true);
			info.setReturnInfo(JacksonUtils.writeJSON(orders));
			String jsonData = JacksonUtils.writeJSON(info);
			writer.write(jsonData);
//			LOG.info("【返回】->" + jsonData);
			LOG.info("findOrderByState查询成功！！");
			return orders;
		} else {
			info = new InfoBean();
			writer.write(JacksonUtils.writeJSON(info));
			LOG.error("findOrderByState查询失败！！");
		}
		return null;
	}

	@Override
	public boolean alterOrder(Order order) {
		Order orderReturn = orderDao.alterOrderState(order);
		if (orderReturn != null) {
			info = new InfoBean();
			info.setValidate(true);
			info.setReturnInfo(JacksonUtils.writeJSON(orderReturn));
			String jsonData = JacksonUtils.writeJSON(info);
			writer.write(jsonData);
			LOG.info("【返回】->" + jsonData);
			LOG.info("alterOrder成功！！");
			return true;
		} else {
			info = new InfoBean();
			writer.write(JacksonUtils.writeJSON(info));
			LOG.error("alterOrder失败！！");
			return false;
		}
	}
}
