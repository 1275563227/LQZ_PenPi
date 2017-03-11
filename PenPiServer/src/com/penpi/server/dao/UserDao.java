package com.penpi.server.dao;

import java.util.List;

import com.penpi.server.domain.User;

public interface UserDao {

	public boolean insertUser(User user);

	public boolean deleteUser(User user);

	public User findUserByID(int ID);

	public User findUserByUserName(String name);

	public List<User> findAllUser();

	public boolean alterUser(User user);
}
