<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html lang="en">
<head>
    <c:import url="jspf/head.jsp"/>
</head>
<body>
<div class="main-container">
    <nav class="main-nav">
        <div id="logo" class="left">
            <a href="index">Rising Sun</a>
        </div>
        <ul class="nav right center-text">
            <li class="btn"><a href="index">Home</a></li>
            <li class="btn"><a href="archive">Archive</a></li>
        </ul>
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-user">
                <li><a href="#"><i class="fa fa-user fa-fw"></i></a>
                </li>
                <li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
                </li>
                <li class="divider"></li>
                <li><a href="logout"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                </li>
            </ul>
            <!-- /.dropdown-user -->
        </li>
    </nav>
    <div class="content-container">
        <c:choose>
            <c:when test="${ page_tag eq 'first_page' }">
                <%@include file="jspf/first-page.jsp" %>
            </c:when>
            <c:when test="${ page_tag eq 'archive' }">
                <%@include file="jspf/search.jsp" %>
            </c:when>
            <c:when test="${ page_tag eq 'article_view' }">
                <%@include file="jspf/article-view.jsp" %>
            </c:when>
            <c:when test="${ page_tag eq 'search' }">
                <%@include file="jspf/search.jsp" %>
            </c:when>
        </c:choose>
    </div>
    <!-- /.content-container -->
    <footer>
        <p>Copyright &copy; 2015 Rising Sun</p>

        <div class="social right">
            <a href="#"><i class="fa fa-facebook"></i></a> <a href="#"><i
                class="fa fa-twitter"></i></a> <a href="#"><i
                class="fa fa-google-plus"></i></a> <a href="#"><i
                class="fa fa-dribbble"></i></a> <a href="#"><i
                class="fa fa-instagram"></i></a> <a href="#"><i
                class="fa fa-linkedin"></i></a>
        </div>
    </footer>
</div>
<script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="js/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="js/modernizr.2.5.3.min.js"></script>
<script type="text/javascript" src="js/jquery.magnific-popup.min.js"></script>
<script type="text/javascript" src="js/templatemo_script.js"></script>
<!-- jQuery -->
<script src="bower_components/jquery/dist/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="bower_components/metisMenu/dist/metisMenu.min.js"></script>

</body>
</html>