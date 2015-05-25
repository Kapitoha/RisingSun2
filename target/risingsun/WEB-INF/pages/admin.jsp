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
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Admin panel</title>
<style>
.menu ul {
	margin: 0;
	list-style: none;
	padding-left: 20px;
	display: none;
}

.menu .title {
	font-size: 18px;
	cursor: pointer;
}

.menu .title::before {
	content: '▶ ';
	font-size: 80%;
	color: green;
}

.menu.open .title::before {
	content: '▼ ';
}

.menu.open ul {
	display: block;
}
</style>
</head>
<body>
	<div id="menu_panel">
		<script type="text/javascript">
			function show(elementId) {
				var menuElem = document.getElementById(elementId);
				var titleElem = menuElem.querySelector('.title');
				titleElem.onclick = function() {
					menuElem.classList.toggle('open');
				}
			}
		</script>
		<div id="admin-actions-user" class="menu">
			<span class="title">Manage Users</span>
			<ul>
				<li>
					<div class="user-actions-item">
						<a href="admin-users">Print Users</a>
					</div>
				</li>
				<li>
					<div class="user-actions-item">
						<a href="admin-user-create">Create User</a>
					</div>
				</li>
			</ul>
		</div>
		<div id="admin-actions-article" class="menu">
			<span class="title">Manage Articles</span>
			<ul>
				<li>
					<div class="user-actions-item">
						<a href="admin-articles-print">Print all articles</a>
					</div>
				</li>
				<li>
					<div class="user-actions-item">
						<a href="admin-articles-first">Print first page</a>
					</div>
				</li>
				<li>
					<div class="user-actions-item">
						<a href="admin-articles-create">Create article</a>
					</div>
				</li>
			</ul>
		</div>
		<script type="text/javascript">
			show('admin-actions-user');
			show('admin-actions-article');
		</script>
	</div>

	<div id="content">
		<p class="info_msg">${ info_msg }</p>
		<p class="error_msg">${ error_msg }</p>
		<c:set var="p_tag" value="${ page_tag }" />
		<c:choose>
			<c:when test="${p_tag eq 'users_show'}">
				<%@include file="jspf/a-user-print.jsp"%>
			</c:when>
			<c:when test="${p_tag eq 'user-edit'}">
				<%@include file="jspf/a-user-edit.jsp"%>
			</c:when>
			<c:when test="${p_tag eq 'articles_print'}">
				<%@include file="jspf/a-article-print.jsp" %>
			</c:when>
		</c:choose>
	</div>

</body>
</html>
