<%--
  Created by IntelliJ IDEA.
  User: kapitoha
  Date: 06.05.15
  Time: 0:33
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring"
	uri="http://www.springframework.org/tags/form"%>
<div>
	<c:choose>
		<c:when test="${empty user_list}">
			<p>There are no any User in database</p>
		</c:when>
		<c:otherwise>
			<table>
				<tr>
					<td width="100">Id</td>
					<td width="100">Name</td>
					<td width="100">Login</td>
					<td width="100">Status</td>
				</tr>
				<c:forEach var="user" items="${user_list}">
					<tr>
						<td><c:out value="${user.id}" /></td>
						<td><c:out value="${user.name}" /></td>
						<td><c:out value="${user.login}" /></td>
						<td><c:out value="${user.status}" /></td>
						<td><form action="admin-user-edit" method="post">
								<%-- 						<input type="hidden" name="user" value="${ user }"> --%>
								<input type="hidden" name="id" value="${ user.id }"> <input
									type="submit" value="Edit">
							</form></td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>


</div>
