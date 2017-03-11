package com.penpi.server.action.forAndroid;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.penpi.server.domain.Order;
import com.penpi.server.service.OrderService;
import com.penpi.server.utils.InitData;

/**
 * 经使用发现，return不能直接返回网页，否则会把网页也传输过去，应当return null；
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("serial")
public class OrderAction extends ActionSupport implements ModelDriven<Order>,
		ServletRequestAware, ServletResponseAware {

	private HttpServletRequest request;
	private HttpServletResponse response;

	// 封装数据
	private Order order = new Order();

	@Override
	public Order getModel() {
		return order;
	}

	// 默认按照名称注入
	private OrderService orderService;

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	// 下面写自己的业务逻辑 -----------------------------------------------------

	public String saveOrder() {
		InitData initData = new InitData(request, response);
		Order order = initData.receiveData(Order.class);
		if (order != null) {
			orderService.setWriter(initData.getWriter());
			orderService.saveOrder(order);
		}
		return null;
	}

	public String deleteOrder() {
		InitData initData = new InitData(request, response);
		Order order = initData.receiveData(Order.class);
		if (order != null) {
			orderService.setWriter(initData.getWriter());
			orderService.deleteOrder(order);
		}
		return null;
	}

	public String findOrderByID() {
		InitData initData = new InitData(request, response);
		Order order = initData.receiveData(Order.class);
		if (order != null) {
			orderService.setWriter(initData.getWriter());
			orderService.findOrderByID(order.getOrderID());
		}
		return null;
	}

	public String findAllOrder() {
		InitData initData = new InitData(request, response);
		initData.receiveData(null);
		orderService.setWriter(initData.getWriter());
		orderService.findAllOrder();
		return null;
	}

	public String findOrderByState() {
		InitData initData = new InitData(request, response);
		Order order = initData.receiveData(Order.class);
		if (order != null) {
			orderService.setWriter(initData.getWriter());
			orderService.findOrderByState(order.getState());
		}
		return null;
	}

	public String alterOrder() {
		InitData initData = new InitData(request, response);
		Order order = initData.receiveData(Order.class);
		if (order != null) {
			orderService.setWriter(initData.getWriter());
			orderService.alterOrder(order);
		}
		return null;
	}

	@Override
	public void setServletRequest(HttpServletRequest httpServletRequest) {
		this.request = httpServletRequest;
	}

	@Override
	public void setServletResponse(HttpServletResponse httpServletResponse) {
		this.response = httpServletResponse;
	}
}
