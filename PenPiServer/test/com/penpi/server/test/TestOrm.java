package com.penpi.server.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.penpi.server.domain.User;
import com.penpi.server.service.UserService;

public class TestOrm {

	public static void main(String[] args) {
		String xmlPath = "classpath:spring/applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				xmlPath);

		// 获得目标类
		UserService userService = (UserService) applicationContext
				.getBean("userService");
		
		User user = new User();
		user.setUsername("admin");
		userService.login(user );
	}
}
