<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ page import="java.util.List,org.server.domain.*,org.server.servive.impl.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Admin Console</title>
<meta name="menu" content="user" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/styles/tablesorter/style.css'/>" />
<script type="text/javascript"
	src="<c:url value='/scripts/jquery.tablesorter.js'/>"></script>
</head>

<body>

	<h1>Users</h1>
	<%
		List<Order> orderList = new OrderServiceImpl().findAllOrder();
	%>
	<table id="tableList" class="tablesorter" cellspacing="1">
		<thead>
			<tr>
				<th>id</th>
				<th>start_place</th>
				<th>end_place</th>
				<th>name</th>
				<th>phone_number</th>
				<th>charges</th>
				<th>remark</th>
				<th>state</th>
				<th>date</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="order" items="${orderList}">
				<tr>
					
					<td><c:out value="${order.id}" /></td>
					<td><c:out value="${order.start_place}" /></td>
					<td><c:out value="${order.end_place}" /></td>
					<td><c:out value="${order.name}" /></td>
					<td><c:out value="${order.phone_number}" /></td>
					<td><c:out value="${order.charges}" /></td>
					<td><c:out value="${order.remark}" /></td>
					<td><c:out value="${order.state}" /></td>
					<td><c:out value="${order.date}" /></td>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<script type="text/javascript">
		//         
		$(function() {
			$('#tableList').tablesorter();
			//$('#tableList').tablesorter( {sortList: [[0,0], [1,0]]} );
			//$('table tr:nth-child(odd)').addClass('odd');
			$('table tr:nth-child(even)').addClass('even');
		});
		//
	</script>

</body>
</html>
