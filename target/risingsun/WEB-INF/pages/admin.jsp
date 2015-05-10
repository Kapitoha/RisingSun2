<%--
  Created by IntelliJ IDEA.
  User: kapitoha
  Date: 06.05.15
  Time: 0:09
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title></title>
</head>
<body>
<div id="admin-actions">
  <ul>
    <li>
      <div class="user-actions-item">
        <a href="#">Manage Users</a>
      </div>
    </li>
    <li>
      <div class="user-actions-item">
        <a href="#">Manage Articles</a>
      </div>
    </li>
  </ul>
</div>
<div id="content">
<c:choose>
  <c:when test="${not empty user_list}">
    <%@include file="jspf/a-user-print.jsp"%>
  </c:when>
</c:choose>
</div>

</body>
</html>
