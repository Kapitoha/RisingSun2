<%--
  User: kapitoha
  Date: 22.05.15
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="coll" uri="/WEB-INF/tag/collection_utils.tld"%>
<div>
	<c:set var="art_list" value="${ articles_list }" />
	<c:choose>
		<c:when test="${ empty art_list }">
			<p>There are no articles in database</p>
		</c:when>
		<c:otherwise>
			<table>
				<tr>
					<td width="20">Id</td>
					<td width="500">Title</td>
					<td width="100">Date of creation</td>
				</tr>
				<c:forEach var="article" items="${ art_list }">
					<tr>
						<td><c:out value="${ article.id }" /></td>
						<td><c:out value="${ article.title }" /></td>
						<td><p><f:formatDate value="${ article.creationDate }" type="date" pattern="dd/MM/yyyy"/></p></td>
						<td><form action="admin-article-view" method="post">
								<input type="hidden" name="id" value="${article.id}" /> 
								<input type="submit" value="view" />
							</form>
							<form action="admin-article-delete" name="delete_article">
								<input type="hidden" name="id" value="${article.id}" />
								<input type="hidden" name="title" value="${article.title}" />
								<input type="submit" value="delete" /> 
							</form></td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>

</div>