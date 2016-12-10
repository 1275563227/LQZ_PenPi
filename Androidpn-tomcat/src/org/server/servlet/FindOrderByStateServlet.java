package org.server.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.server.domain.Order;
import org.server.service.OrderService;
import org.server.servive.impl.OrderServiceImpl;
import org.server.util.ClientUtil;

public class FindOrderByStateServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write("---");
		OrderService orderService = new OrderServiceImpl();

		// String result = ClientUtil.receive(request);
		// System.out.println("result = " + result);
		String result = (String) request.getParameter("state");
		// result = "未取";
		System.out.println("state = " + result);
		List<Order> orderList = orderService.findOrderByState(result);
		if (orderList.size() > 0) {
			for (Order order : orderList) {
				System.out.println(order);
				out.write(order.toResponse() + "###");
			}
		} else
			out.write("empty");
		out.write("---");
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
