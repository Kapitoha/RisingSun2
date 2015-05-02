
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
  <title>My news</title>
</head>
<body>
<div>
  <h1>My news</h1>
  <c:if test = "${!empty allnews}">
    <table>
      <tr>
        <th>Title</th>
        <th>Text</th>
        <th>Date</th>
      </tr>
      <c:forEach items = "${allnews}" var = "news">
        <tr>
          <td>${news.title}</td>
          <td>${news.article}<td>
          <td>${news.dateCreate}<td>
        </tr>
      </c:forEach>
    </table>
  </c:if>
</div>
</body>
</html>
