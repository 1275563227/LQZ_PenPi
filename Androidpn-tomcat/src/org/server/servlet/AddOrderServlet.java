package org.server.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.androidpn.server.xmpp.push.NotificationManager;
import org.server.domain.Order;
import org.server.service.OrderService;
import org.server.servive.impl.OrderServiceImpl;
import org.server.util.ClientUtil;

import sun.jdbc.odbc.OdbcDef;


@SuppressWarnings("serial")
public class AddOrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
	
		OrderService orderService = new OrderServiceImpl();
		
		String result = ClientUtil.receive(request);
		System.out.println("serve result = " + result);
		
		Order order = ClientUtil.JsontoOrder(result);
//		order.setState("未抢");
//		SimpleDateFormat simFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
//		order.setDate(simFormat.format(new Date()));
		System.out.println("serve order = " + order);
		 
		orderService.insertOrder(order);
		
		String apiKey = "1234567890";
		String title = order.getName() + "发布了一个新订单:";
		String message = order.getId() + ","
				+ order.getStart_place() + ","
				+ order.getEnd_place() + ","
				+ order.getName() + ","
				+ order.getPhone_number() + ","
				+ order.getCharges() + ","
				+ order.getRemark() + ","
				+ order.getState() + ","
				+ order.getDate();
		NotificationManager notificationManager = new NotificationManager();
		notificationManager.sendBroadcast(apiKey, title, message, "");
		
		out.write("out = : ---" + order.toResponse() + "---");
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
