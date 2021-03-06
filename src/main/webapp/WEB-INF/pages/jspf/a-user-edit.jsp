<%--
  User: kapitoha
  Date: 23.05.15
--%>
<%@page import="com.springapp.mvc.domain.Status"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="coll" uri="/WEB-INF/tag/collection_utils.tld"%>
<div>
	<c:set var="user_obj" value="${ user }" />
	<c:set var="userRightsList" value="${ user_obj.accessList }" />

	<form id="resetForm" action="admin-users"></form>
	<form id="saveForm" role="form" action="admin-user-save" method="post">
		<div class="col-lg-3">
			<input type="hidden" name="id" value="${ user_obj.id }">
			<div class="form-group">
				<label for="name">User Name </label> <input type="text"
					value="${user_obj.getName() }" name="name" class="form-control"
					placeholder="Enter name" maxlength="24">
			</div>
			<div class="form-group">
				<label for="login">User Login </label> <input type="text"
					value="${user_obj.getLogin() }" name="login" class="form-control"
					placeholder="Enter login" maxlength="24">
			</div>
			<div class="form-group">
				<label for="name">User Password </label> <input type="text"
					value="${user_obj.getPassword() }" name="password" class="form-control"
					placeholder="Enter password" maxlength="24">
			</div>
			<div class="form-group">
				<label for="status">Status </label> <select name="status"
					class="form-group">
					<c:forEach var="status_elem" items="${ status_list }">
						<option ${ user_obj.getStatus() eq status_elem? 'selected' : '' }
							value="${ status_elem }">
							<c:out value="${ status_elem }"></c:out>
						</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="col-lg-4">
			<!-- Rules list -->
			<label>Access rights:</label>
			<ul>
				<c:set var="field" value="id" />
				<c:forEach var="rule" items="${ rules_list }">
					<li><input name="access_right" type="checkbox"
						value="${ rule.id }"
						${ coll:containsByField(userRightsList, field, rule.id)? 'checked' : '' }>${
						rule.getDescription() }
					</li>

				</c:forEach>
			</ul>
		</div>
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
	<br>
	<div class="col-lg-10">
		<input form="saveForm" type="hidden" name="user" value="${user_obj}">
		<input form="saveForm" type="submit" value="Save" /> <input
			form="resetForm" type="submit" value="Cancel">
	</div>

</div>