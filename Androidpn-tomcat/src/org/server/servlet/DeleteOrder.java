package org.server.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.server.domain.Order;
import org.server.service.OrderService;
import org.server.servive.impl.OrderServiceImpl;
import org.server.util.ClientUtil;


public class DeleteOrder extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		OrderService orderService = new OrderServiceImpl();

		String result = ClientUtil.receive(request);
		System.out.println("result = " + result);

		Order order = ClientUtil.JsontoOrder(result);
		System.out.println("order = " + order);
		
		orderService.deleteOrder(order.getPhone_number());
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
}
