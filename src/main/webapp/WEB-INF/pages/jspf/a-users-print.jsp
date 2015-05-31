<%--
  User: kapitoha
  Date: 23.05.15
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">Users</h1>
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">Users</div>
				<c:choose>
					<c:when test="${empty user_list}">
						<p>There are no any User in database</p>
					</c:when>
					<c:otherwise>
							<!-- /.panel-heading -->
					<div class="panel-body">
						<div class="dataTable_wrapper">
							<table class="table table-striped table-bordered table-hover" id="dataTables-example">
								<thead>
									<tr>
										<th># ID</th>
										<th>Name</th>
										<th>Login</th>
										<th>Status</th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="user" items="${ user_list }">
									<tr class="odd gradeA">
										<td>${ user.id }</td>
										<td>${ user.name }</td>
										<td>${ user.login }</td>
										<td>${ user.status }</td>
										<td align="center" class="col-lg-1"><form action="admin-user-edit" method="post">
											<input type="hidden" name="id" value="${ user.id }"> 
											<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
											<input type="submit" value="Edit">
										</form></td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					<!-- /.panel-body -->
					</c:otherwise>
				</c:choose>
			
		</div>
		<!-- /.panel -->
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->