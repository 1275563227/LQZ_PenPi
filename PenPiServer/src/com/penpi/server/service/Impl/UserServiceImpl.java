package com.penpi.server.service.Impl;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.penpi.server.dao.UserDao;
import com.penpi.server.domain.Order;
import com.penpi.server.domain.User;
import com.penpi.server.service.UserService;
import com.penpi.server.utils.InfoBean;
import com.penpi.server.utils.JacksonUtils;

public class UserServiceImpl implements UserService {

	private UserDao userDao;

	private PrintWriter writer;
	private final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
	private InfoBean info;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}

	@Override
	public boolean login(User user) {
		User userByName = userDao.findUserByUserName(user.getUsername());
		// 登录成功
		if (userByName != null
				&& userByName.getPassword().equals(user.getPassword())) {
			userByName.setLoginTime(new Date());
			info = new InfoBean();
			info.setValidate(true);
			info.setReturnInfo(JacksonUtils.writeJSON(userByName));
			String jsonData = JacksonUtils.writeJSON(info);
			writer.write(jsonData);
			LOG.info("【返回】->" + jsonData); // 返回完整的user信息
			LOG.info("login成功！！");
			return true;
		} else { // 登录失败
			info = new InfoBean();
			String jsonData = JacksonUtils.writeJSON(info);
			writer.write(jsonData);
			LOG.info("【返回】->" + jsonData);
			LOG.error("login失败！！");
			return false;
		}
	}

	@Override
	public boolean register(User user) {
		if (this.saveUser(user)) {
			LOG.error("register成功！！");
			return true;
		}
		LOG.error("register失败！！");
		return false;
	}

	@Override
	public boolean saveUser(User user) {
		if (userDao.insertUser(user)) {
			info = new InfoBean();
			info.setValidate(true);
			String jsonData = JacksonUtils.writeJSON(info);
			writer.write(jsonData);
			LOG.info("【返回】->" + jsonData);
			LOG.info("saveUser成功！！");
			return true;
		} else {
			info = new InfoBean();
			String jsonData = JacksonUtils.writeJSON(info);
			writer.write(jsonData);
			LOG.info("【返回】->" + jsonData);
			LOG.error("saveUser失败！！");
			return false;
		}
	}

	@Override
	public boolean deleteUser(User user) {
		if (userDao.deleteUser(user)) {
			info = new InfoBean();
			info.setValidate(true);
			String jsonData = JacksonUtils.writeJSON(info);
			writer.write(jsonData);
			LOG.info("【返回】->" + jsonData);
			LOG.info("deleteUser成功！！");
			return true;
		} else {
			info = new InfoBean();
			writer.write(JacksonUtils.writeJSON(info));
			LOG.error("deleteUser失败！！");
			return false;
		}
	}

	@Override
	public User findUserByID(int ID) {
		User userByID = userDao.findUserByID(ID);
		if (userByID != null) {
			info = new InfoBean();
			info.setValidate(true);
			info.setReturnInfo(JacksonUtils.writeJSON(userByID));
			String jsonData = JacksonUtils.writeJSON(info);
			writer.write(jsonData);
			LOG.info("【返回】->" + jsonData);
			LOG.info("findUserByID查询成功！！");
			return userByID;
		} else {
			info = new InfoBean();
			writer.write(JacksonUtils.writeJSON(info));
			LOG.error("findUserByID查询失败！！");
			return null;
		}
	}

	@Override
	public List<User> findAllUser() {
		List<User> users = userDao.findAllUser();
		if (users != null && users.size() != 0) {
			info = new InfoBean();
			info.setValidate(true);
			info.setReturnInfo(JacksonUtils.writeJSON(users));
			String jsonData = JacksonUtils.writeJSON(info);
			writer.write(jsonData);
			LOG.info("【返回】->" + jsonData);
			LOG.info("findAllUser查询成功！！");
			return users;
		} else {
			info = new InfoBean();
			writer.write(JacksonUtils.writeJSON(info));
			LOG.error("findAllUser查询失败！！");
			return null;
		}
	}

	@Override
	public boolean alterUser(User user) {
		if (userDao.alterUser(user)) {
			info = new InfoBean();
			info.setValidate(true);
			String jsonData = JacksonUtils.writeJSON(info);
			writer.write(jsonData);
			LOG.info("【返回】->" + jsonData);
			LOG.info("alterUser成功！！");
			return true;
		} else {
			info = new InfoBean();
			writer.write(JacksonUtils.writeJSON(info));
			LOG.error("alterUser失败！！");
			return false;
		}
	}

	@Override
	public List<Order> findMySendOrders(int ID) {
		User userByID = userDao.findUserByID(ID);
		Set<Order> orders = null;
		if (userByID != null) {
			orders = userByID.getSendOrders();
			info = new InfoBean();
			info.setValidate(true);
			info.setReturnInfo(JacksonUtils.writeJSON(orders));
			String jsonData = JacksonUtils.writeJSON(info);
			writer.write(jsonData);
			//LOG.info("【返回】->" + jsonData);
			LOG.info("findMySendOrders查询成功！！");
			return new ArrayList<Order>(orders);
		} else {
			info = new InfoBean();
			writer.write(JacksonUtils.writeJSON(info));
			LOG.error("findMySendOrders查询失败！！");
			return null;
		}
	}

	@Override
	public List<Order> findMyTakeOrders(int ID) {
		User userByID = userDao.findUserByID(ID);
		Set<Order> orders = null;
		if (userByID != null) {
			orders = userByID.getTakeOrders();
			info = new InfoBean();
			info.setValidate(true);
			info.setReturnInfo(JacksonUtils.writeJSON(orders));
			String jsonData = JacksonUtils.writeJSON(info);
			writer.write(jsonData);
			//LOG.info("【返回】->" + jsonData);
			LOG.info("findMyTakeOrders查询成功！！");
			return new ArrayList<Order>(orders);
		} else {
			info = new InfoBean();
			writer.write(JacksonUtils.writeJSON(info));
			LOG.error("findMyTakeOrders查询失败！！");
			return null;
		}
	}

}
