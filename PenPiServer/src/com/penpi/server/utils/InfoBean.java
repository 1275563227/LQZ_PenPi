package com.penpi.server.utils;

/**
 * 1、为每个业务逻辑初始化参数，response的validate参数默认配置为false；
 * @author Administrator
 *
 */
public class InfoBean {
	
	private boolean validate = false;
	private String returnInfo;

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public String getReturnInfo() {
		return returnInfo;
	}

	public void setReturnInfo(String returnInfo) {
		this.returnInfo = returnInfo;
	}
}
