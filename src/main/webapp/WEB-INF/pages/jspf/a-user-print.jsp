<%--
  Created by IntelliJ IDEA.
  User: kapitoha
  Date: 06.05.15
  Time: 0:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div>
	<table>
		<tr>
			<td width="50">Id</td>
			<td width="50">Name</td>
			<td width="50">Login</td>
			<td width="50">Access level</td>
		</tr>
		<c:forEach var="user" items="${user_list}">
			<tr>
				<td><c:out value="${user.id}" /></td>
				<td><a href="admin-user-edit?id=${ user.id }"><c:out value="${user.name}" /></a></td>
				<td><c:out value="${user.login}" /></td>
			</tr>
		</c:forEach>
	</table>

</div>
