package org.server.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.server.domain.User;
import org.server.service.UserService;
import org.server.servive.impl.UserServiceImpl;
import org.server.util.ClientUtil;


public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		UserService userService = new UserServiceImpl();
		
		String result = ClientUtil.receive(request);
		System.out.println("result = " + result);
		
		User user = ClientUtil.JsontoUser(result);
		System.out.println("user = " + user);
		String phone_number = user.getPhone_number();
		String password = user.getPassword();
		
		User user_bySQL = userService.findUserByPhone(user
				.getPhone_number());

		if (phone_number.equals(user_bySQL.getPhone_number())
				&& password.equals(user_bySQL.getPassword())) {
			out.println("true");
		} else {
			out.println("flase");
		}
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
