<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<form:form class="searching" method="post" action="search" commandName="search">
	<%--<fieldset>--%>
		<%--<label></label>--%>
		<%--<div id="picture-input">--%>
			<%--<form:input path="path"--%>
						<%--onfocus="if(this.value==this.defaultValue)this.value=''"--%>
						<%--onblur="if(this.value=='')this.value=this.defaultValue"--%>
						<%--value="Search&hellip;" />--%>
		<%--</div>--%>
		<%--<input class="hledat" type="image"--%>
				<%--<c:set var="contextPath" value="${pageContext.request.contextPath}"/>--%>
			   <%--src="${contextPath}/resources/img/search-button.gif" name="" value="Search"--%>
			   <%--alt="Search" />--%>
	<%--</fieldset>--%>
</form:form>