package com.penpi.server.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.penpi.server.domain.Order;
import com.penpi.server.service.OrderService;

/**
 * 经使用发现，return不能直接返回网页，否则会把网页也传输过去，应当return null；
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("serial")
public class OrderAction extends ActionSupport implements ModelDriven<Order>,
		ServletRequestAware {

	private HttpServletRequest request;

	// 默认按照名称注入
	private OrderService orderService;

	// 封装数据
	private Order order = new Order();

	@Override
	public Order getModel() {
		return order;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	public void setServletRequest(HttpServletRequest httpServletRequest) {
		this.request = httpServletRequest;
	}

	public String findAllOrder() {
		try {
			List<Order> orders = orderService.findAllOrder();
			request.getSession().setAttribute("allOrders", orders);
			return "findAllOrder";
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
}
