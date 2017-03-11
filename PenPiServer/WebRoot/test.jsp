<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>My JSP 'test.jsp' starting page</title>
  </head>
  
  <body>
    <s:form namespace="/" action="userAction_findAllUser" >
    	<s:submit value="查询所有用户"></s:submit>
    </s:form>
    
    <s:form namespace="/" action="orderAction_saveOrder" >
    	<s:submit value="保存订单"></s:submit>
    </s:form>
    
    <s:form namespace="/" action="orderAction_findOrderByState" >
    	<s:submit value="findOrderByState"></s:submit>
    </s:form>
  </body>
</html>
