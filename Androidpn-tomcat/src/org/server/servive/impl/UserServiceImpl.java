package org.server.servive.impl;

import java.sql.SQLException;
import java.util.List;

import org.server.DAO.UserDao;
import org.server.DAO.impl.UserDAOImpl;
import org.server.domain.User;
import org.server.service.UserService;


public class UserServiceImpl implements UserService {

	public void insertUser(User user){
		UserDao userDao = new UserDAOImpl();
		try {
			userDao.insertUser(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteUser(String phone_number){
		UserDao userDao = new UserDAOImpl();
		try {
			userDao.deleteUser(phone_number);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void alterUser(User user, String phone_number){
		UserDao userDao = new UserDAOImpl();
		try {
			userDao.alterUser(user, phone_number);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public User findUserByPhone(String phone_number){
		UserDao userDao = new UserDAOImpl();
		try {
			return userDao.findUserByPhone(phone_number);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<User> findAllUser(){
		UserDao userDao = new UserDAOImpl();
		try {
			return userDao.findAllUser();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
