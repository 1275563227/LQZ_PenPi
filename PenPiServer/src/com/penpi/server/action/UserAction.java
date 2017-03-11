package com.penpi.server.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.penpi.server.domain.User;
import com.penpi.server.service.UserService;
import com.penpi.server.utils.InitData;

@SuppressWarnings("serial")
public class UserAction extends ActionSupport implements ModelDriven<User>,
		ServletRequestAware, ServletResponseAware {

	private HttpServletRequest request;
	private HttpServletResponse response;

	// 默认按照名称注入
	private UserService userService;

	// 封装数据
	private User user = new User();

	@Override
	public User getModel() {
		return user;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void setServletRequest(HttpServletRequest httpServletRequest) {
		this.request = httpServletRequest;
	}

	@Override
	public void setServletResponse(HttpServletResponse httpServletResponse) {
		this.response = httpServletResponse;
	}

	// 下面写自己的业务逻辑 -----------------------------------------------------

	public String login() {
		InitData initData = new InitData(request, response);
		// 解析好的user,有userName和password
		User user = initData.receiveData(User.class);
		if (user != null) {
			// 登录校验
			userService.setWriter(initData.getWriter());
			userService.login(user);
			return SUCCESS;
		}
		return ERROR;
	}

	public String register() {
		InitData initData = new InitData(request, response);
		User user = initData.receiveData(User.class);
		if (user != null) {
			userService.setWriter(initData.getWriter());
			userService.register(user);
			return SUCCESS;
		}
		return ERROR;
	}

	public String saveUser() {
		InitData initData = new InitData(request, response);
		User user = initData.receiveData(User.class);
		if (user != null) {
			userService.setWriter(initData.getWriter());
			userService.saveUser(user);
			return SUCCESS;
		}
		return ERROR;
	}

	public String deleteUser() {
		InitData initData = new InitData(request, response);
		User user = initData.receiveData(User.class);
		if (user != null) {
			userService.setWriter(initData.getWriter());
			userService.deleteUser(user);
			return SUCCESS;
		}
		return ERROR;
	}

	public String findUserByID() {
		InitData initData = new InitData(request, response);
		User user = initData.receiveData(User.class);
		if (user != null) {
			userService.setWriter(initData.getWriter());
			userService.findUserByID(user.getUserID());
			return SUCCESS;
		}
		return ERROR;
	}

	public String findAllUser() {
		InitData initData = new InitData(request, response);
		initData.receiveData(User.class);
		userService.setWriter(initData.getWriter());
		List<User> users = userService.findAllUser();
		request.getSession().setAttribute("allUsers", users);
		return "findAllUser";
	}

	public String alterUser() {
		InitData initData = new InitData(request, response);
		User user = initData.receiveData(User.class);
		if (user != null) {
			userService.setWriter(initData.getWriter());
			userService.alterUser(user);
			return SUCCESS;
		}
		return ERROR;
	}

	public String findMySendOrders() {
		InitData initData = new InitData(request, response);
		User user = initData.receiveData(User.class);
		if (user != null) {
			userService.setWriter(initData.getWriter());
			userService.findMySendOrders(user.getUserID());
			return null;
		}
		return null;
	}

	public String findMyTakeOrders() {
		InitData initData = new InitData(request, response);
		User user = initData.receiveData(User.class);
		if (user != null) {
			userService.setWriter(initData.getWriter());
			userService.findMyTakeOrders(user.getUserID());
			return null;
		}
		return null;
	}

}
