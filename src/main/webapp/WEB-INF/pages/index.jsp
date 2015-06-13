<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html lang="ru">
<head>
    <c:import url="jspf/head.jsp"/>
</head>
<body>
<div class="main-container">
    <!-- Import navbar -->
    <c:import url="jspf/navbar.jsp" charEncoding="utf8" />

    <!-- main content -->
    <div class="content-container page-wrapper">
        <c:choose>
            <c:when test="${ page_tag eq 'first_page' }">
                <c:import url="jspf/first-page.jsp" />
            </c:when>
            <c:when test="${ page_tag eq 'archive' }">
                <c:import url="jspf/search.jsp" />
            </c:when>
            <c:when test="${ page_tag eq 'article_view' }">
                <c:import url="jspf/article-view.jsp" />
            </c:when>
            <c:when test="${ page_tag eq 'search' }">
                <c:import url="jspf/search.jsp" />
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