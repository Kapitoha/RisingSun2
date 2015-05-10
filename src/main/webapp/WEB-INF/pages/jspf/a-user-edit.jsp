<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div>
	<form action="admin-user-save" method="post">
		<label for="name">User Name </label> <input type="text"
			value="${user.name }" name="name" /> <label for="name">User
			Login </label> <input type="text" value="${ user.login }" name="login" /> <label
			for="name">User Password </label> <input type="text"
			value="${ user.password }" name="login" /> <input type="submit"
			value="Save" />
	</form>
	<form action="admin-users">
		<input type="submit" value="Back to Users">
	</form>

</div>