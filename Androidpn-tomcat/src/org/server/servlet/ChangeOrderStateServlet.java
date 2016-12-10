package org.server.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.server.domain.Order;
import org.server.service.OrderService;
import org.server.servive.impl.OrderServiceImpl;
import org.server.util.ClientUtil;

public class ChangeOrderStateServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		OrderService orderService = new OrderServiceImpl();
		PrintWriter out = response.getWriter();
		String state = (String) request.getParameter("state");
		String id = (String) request.getParameter("id");

		System.out.println("state=" + state + "\nid=" + id);
		Order order = orderService.findOrderById(id);
		System.out.println(order.toString());
		System.out.println("state = " + order.getState());
		
		if (order.getState().contains("未抢") && state.contains("已抢")) {
			orderService.alterOrderState(state, id);
			out.write("---true---");
			System.out.println("---true---");
		} else if(order.getState().contains("已抢") && state.contains("完成")){
			orderService.alterOrderState(state, id);
			out.write("---true---");
			System.out.println("---true---");
		} else{
			out.write("---false---");
			System.out.println("---false---");
		}

		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
