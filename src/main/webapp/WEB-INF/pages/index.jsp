<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="ru">
<head>
<c:import url="jspf/head.jsp" />
</head>
<body>
	<div class="main-container">
		<div class="navbar navbar-default navbar-static-top" style="margin-bottom: 0" role="navigation" id="nav-bar">
			<a class="navbar-brand" id="logo-bar" href="index">
				Rising Sun
			</a>
			<div id="action-bar">
				<div class="btn-group" id="myDropdown">
				<form id="search_form" action="search"></form>
					<button type="button" class="btn btn-default dropdown-toggle fa fa-search"
						data-toggle="dropdown">
						Search <span class="caret"></span>
					</button>
					<ul class="dropdown-menu">
						<li>
							<label for="keyword">Keyword</label>
							<input form="search_form" type="text" name="keyword" id="keyword" class="form-control" title="Enter some keyword or phrase">
						</li>
						<li>
							<label for="author">Author</label>
							<input form="search_form" type="text" name="author" class="form-control" title="Enter author name">
						</li>
						<li>
							<label for="tag">Tags</label>
							<input form="search_form" type="text" name="tag" class="form-control" title="Enter tags through the space or comma.">
						</li>
						<li class="divider"></li>
						<li><input form="search_form" name="composite" type="checkbox" title="If checked, all field will be included into search compositly."> Composite Search </li>
						<li class="divider"></li>
						<li><input form="search_form" type="submit" value="Search"></li>
					</ul>
				
				</div>
				<button type="button"
					class="btn btn-${ page_tag eq 'first_page'? 'primary':'info' } fa fa-home"
					onclick="invoke('index')"> Home</button>
				<button type="button"
					class="btn btn-${ page_tag eq 'archive'? 'primary':'info' } fa fa-archive"
					onclick="invoke('archive')"> Archive</button>
			</div>
		</div>
		<div class="content-container page-wrapper">
			<c:choose>
				<c:when test="${ page_tag eq 'first_page' }">
					<%@include file="jspf/first-page.jsp"%>
				</c:when>
				<c:when test="${ page_tag eq 'archive' }">
					<%@include file="jspf/search.jsp"%>
				</c:when>
				<c:when test="${ page_tag eq 'article_view' }">
					<%@include file="jspf/article-view.jsp"%>
				</c:when>
				<c:when test="${ page_tag eq 'search' }">
					<%@include file="jspf/search.jsp"%>
				</c:when>
			</c:choose>
		</div>
		<!-- /.content-container -->
		<footer>
			<p>Copyright &copy; 2015 Rising Sun</p>
		</footer>
	</div>
	<!-- <script type="text/javascript" src="js/jquery-1.11.1.min.js"></script> -->
	<!-- <script type="text/javascript" src="js/jquery.easing.1.3.js"></script> -->
	<script type="text/javascript" src="js/modernizr.2.5.3.min.js"></script>
	<script type="text/javascript" src="js/jquery.magnific-popup.min.js"></script>
	<script type="text/javascript" src="js/templatemo_script.js"></script>
	<script type="text/javascript" src="js/risingsun.js"></script>
	<!-- jQuery -->
	<script src="bower_components/jquery/dist/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="bower_components/metisMenu/dist/metisMenu.min.js"></script>
	<script type="text/javascript">
		dropdown('#myDropdown .dropdown-menu');
	</script>
	<script type="text/javascript">pagination('.pagination li')</script>

</body>
</html>