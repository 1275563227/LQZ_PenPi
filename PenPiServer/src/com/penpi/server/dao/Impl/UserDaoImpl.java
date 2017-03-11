package com.penpi.server.dao.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.penpi.server.dao.UserDao;
import com.penpi.server.domain.User;

public class UserDaoImpl extends HibernateDaoSupport implements UserDao {

	@Override
	public boolean insertUser(User user) {
		// 用户是用手机号码注册的
		@SuppressWarnings("unchecked")
		List<User> users = super.getHibernateTemplate().find(
				"from User where phoneNumber = ?", user.getPhoneNumber());
		//System.out.println(users);
		try {
			if (users.size() == 0) {
				user.setUsername(user.getPhoneNumber());
				user.setLoginTime(new Date());
				super.getHibernateTemplate().save(user);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteUser(User user) {
		try {
			super.getHibernateTemplate().delete(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public User findUserByID(int ID) {
		User user = super.getHibernateTemplate().get(User.class, ID);
		if (user != null)
			return user;
		return null;
	}

	@Override
	public User findUserByUserName(String name) {
		@SuppressWarnings("unchecked")
		List<User> users = super.getHibernateTemplate().find(
				"from User where username = ?", name);
		if (users.size() > 0)
			return users.get(0);
		return null;
	}

	@Override
	public List<User> findAllUser() {
		@SuppressWarnings("unchecked")
		List<User> users = super.getHibernateTemplate().find("from User");
		if (users.size() > 0)
			return users;
		return null;
	}

	@Override
	public boolean alterUser(User user) {
		try {
			super.getHibernateTemplate().update(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
