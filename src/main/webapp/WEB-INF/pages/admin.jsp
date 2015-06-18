<%--
  User: kapitoha
  Date: 22.05.15
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="coll" uri="/WEB-INF/tag/collection_utils.tld"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">
<!-- Import head -->
<%@include file="jspf/admin-head.jsp"%>
<sec:authentication var="principalname" property="name"/>


<body>

	<div id="wrapper">

		<!-- Navigation -->
		<%@include file="jspf/admin-navbar.jsp"%>

		<!-- Page Content -->
		<div id="page-wrapper">
			<p class="info_msg">${ info_msg }</p>
			<p class="error_msg">${ error_msg }</p>
			<c:set var="p_tag" value="${ page_tag }" />
			<c:choose>
				<c:when test="${p_tag eq 'users_show'}">
					<%@include file="jspf/a-users-print.jsp" %>
				</c:when>
				<c:when test="${p_tag eq 'user-edit'}">
					<%@include file="jspf/a-user-edit.jsp" %>
				</c:when>
				<c:when test="${p_tag eq 'articles_print'}">
					<%@include file="jspf/a-article-print.jsp" %>
				</c:when>
				<c:when test="${p_tag eq 'articles_edit'}">
					<%@include file="jspf/a-article-edit.jsp" %>
				</c:when>
				<c:when test="${p_tag eq 'article_view'}">
					<%@include file="jspf/a-article-view.jsp" %>
				</c:when>
			</c:choose>
		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->

	<!-- jQuery -->
	<script src="bower_components/jquery/dist/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="bower_components/metisMenu/dist/metisMenu.min.js"></script>
	
	<!-- DataTables JavaScript -->
    <script src="bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    <script src="bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="js/sb-admin-2.js"></script>
	
    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
    $(document).ready(function() {
        $('#dataTables-example').DataTable({
                responsive: true
        });
    });
    </script>

</body>

</html>
