<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div>
<form action="admin-user-save" method="post">
	<label for="name" >
	<input type="text" value="${user.name }"name="name"/>
	</label>
	<input type="text" value="${ user.login }"name="login"/>
	<input type="submit" value="Save"/>
	<input type="button" formaction="admin-users" value="back">
</form>

</div>