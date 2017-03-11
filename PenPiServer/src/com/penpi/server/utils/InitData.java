package com.penpi.server.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 1、从客户端接收到信息：IP地址，主机名，receiveData，并打印日志； 2、校验数据；
 * 
 * @author Administrator
 * 
 */
public class InitData {
	private Logger logger = LoggerFactory.getLogger(InitData.class);

	private HttpServletRequest httpServletRequest;
	private PrintWriter writer;

	private InfoBean info; // 给客服端返回信息的类

	public InitData(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		try {
			this.httpServletRequest = httpServletRequest;
			this.httpServletRequest.setCharacterEncoding("UTF-8");
			httpServletResponse.setCharacterEncoding("UTF-8");
			writer = httpServletResponse.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		info = new InfoBean();
	}

	/**
	 * 
	 * @param <T>
	 * @return 数据无误则解析并返回user，否则返回null
	 */
	public <T> T receiveData(Class<T> type) {
		// 从客户端接收信息
		String receiveData = StringUtils.receive(httpServletRequest);
		logger.info(" 【客服端】 -> IP地址： " + httpServletRequest.getRemoteAddr()
				+ " \\ 接收的Date： " + receiveData);

		// 校验信息 -> jsonDate不为空，并且第一个字符为'{'
		if (receiveData.length() != 0) {
			if (receiveData.charAt(0) == '{') {
				// 解析客服端传过来的信息
				T t = JacksonUtils.readJson(receiveData, type);
				logger.info(" 解析成功！！");
				return t;
			} else {
				writer.write(JacksonUtils.writeJSON(info));
				logger.error("解析失败！！从客户端接收的jsonDate【格式不对】！！");
				return null;
			}
		} else {
			writer.write(JacksonUtils.writeJSON(info));
			logger.error("解析失败！！从客户端接收的jsonDate【为空】！！");
			return null;
		}
	}

	public Logger getLogger() {
		return logger;
	}

	public PrintWriter getWriter() {
		return writer;
	}

	public InfoBean getInfo() {
		return info;
	}
}
