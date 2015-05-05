<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<form:form class="searching" method="post" action="${pageContext.request.contextPath}/search" commandName="search">
	<fieldset>
		<label></label>
		<div id="picture-input">
			<form:input path="search"
						onfocus="if(this.value==this.defaultValue)this.value=''"
						onblur="if(this.value=='')this.value=this.defaultValue"
						value="Search..." />
		</div>
		<input class="hledat" type="image"
			   src="${pageContext.request.contextPath}/resources/img/search-button.gif" name="" value="Search"
			   alt="Search" />
	</fieldset>
</form:form>


