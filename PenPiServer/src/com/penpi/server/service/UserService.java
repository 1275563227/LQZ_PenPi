package com.penpi.server.service;

import java.io.PrintWriter;
import java.util.List;

import com.penpi.server.domain.Order;
import com.penpi.server.domain.User;

public interface UserService {

	public void setWriter(PrintWriter writer);

	public boolean login(User user);

	public boolean register(User user);

	public boolean saveUser(User user);

	public boolean deleteUser(User user);

	public User findUserByID(int ID);
	
	public List<User> findAllUser();

	public List<Order> findMySendOrders(int ID);

	public List<Order> findMyTakeOrders(int ID);

	public boolean alterUser(User user);

}