package org.server.service;

import java.util.List;

import org.server.domain.User;


public interface UserService {
	
	public void insertUser(User user);
	
	public void deleteUser(String phone_number);
	
	public void alterUser(User user, String phone_number);
	
	public User findUserByPhone(String phone_number);
	
	public List<User> findAllUser();
}
