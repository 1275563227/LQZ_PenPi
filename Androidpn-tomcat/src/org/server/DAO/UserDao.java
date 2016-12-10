package org.server.DAO;

import java.sql.SQLException;
import java.util.List;

import org.server.domain.User;


public interface UserDao {
	
	public void insertUser(User user) throws SQLException;
	
	public void deleteUser(String phone_number) throws SQLException;
	
	public void alterUser(User user, String phone_number) throws SQLException;
	
	public User findUserByPhone(String phone_number) throws SQLException;
	
	public List<User> findAllUser() throws SQLException;
}
