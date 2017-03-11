package com.penpi.server.test;


import org.hibernate.Session;
import org.junit.Test;

import com.penpi.server.domain.Order;
import com.penpi.server.domain.User;
import com.penpi.server.unuser.HibernateUtils;

public class TestSome {

	@Test
	public void fun1() {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();
		// ------------------------------------------------
		User user1 = new User();
		User user2 = new User();
		user1.setUsername("大神");
		user2.setUsername("大所答");
		Order order = new Order();
		order.setSendOrderPeople(user1);
		order.setTakeOrderPeople(user2);

		session.save(order);
		session.save(user1);
		session.save(user2);
		// ------------------------------------------------
		session.getTransaction().commit();
		session.close();
	}

	@Test
	public void fun2() {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();
		// ------------------------------------------------

//		User user = (User)session.get(User.class, 2);
		
		Order order = (Order) session.get(Order.class, 1);
		
		System.out.println(order);
		System.out.println(order.getSendOrderPeople());
		System.out.println(order.getTakeOrderPeople());
		
		// ------------------------------------------------
		session.getTransaction().commit();
		session.close();
	}
	
	@Test
	public void fun3() {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();
		// ------------------------------------------------

		User user1 = new User();
		user1.setUsername("大神");
		System.out.println(user1);
//		User user = (User)session.get(User.class, 2);
		
//		Order order = (Order)session.get(Order.class, 1);
//		
//		System.out.println(order.getSendOrderPeple());
		
		// ------------------------------------------------
		session.getTransaction().commit();
		session.close();
	}
}
