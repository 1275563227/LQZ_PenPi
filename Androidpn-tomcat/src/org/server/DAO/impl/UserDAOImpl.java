package org.server.DAO.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.server.DAO.UserDao;
import org.server.domain.User;
import org.server.util.C3P0Util;


public class UserDAOImpl implements UserDao {

	public void insertUser(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		qr.update("INSERT INTO user VALUES(?,?,?)", user.getUsername(),
				user.getPhone_number(), user.getPassword());
	}

	public void deleteUser(String phone_number) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		qr.update("delete from user where phone_number=?", phone_number);
	}

	public void alterUser(User user, String phone_number) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		qr.update(
				"update user set id=?,username=?,phone_number=?,password=? where phone_number=?",
				user.getId(), user.getUsername(), user.getPhone_number(),
				user.getPassword(), phone_number);
	}

	public List<User> findAllUser() throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr.query("select * from user", new BeanListHandler<User>(
				User.class));
	}

	public User findUserByPhone(String phone_number) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr.query("select * from user where phone_number=?",
				new BeanHandler<User>(User.class), phone_number);
	}
}
