<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<c:set var="article" value="${ article_obj }" />
<script type="text/javascript">
	function invoke(url) {
		document.location.href = url;
	}
</script>
<div class="row">
	<div class="col-lg-12">
		<c:choose>
			<c:when test="${ empty article }">
				<c:redirect url="404"></c:redirect>
			</c:when>
			<c:otherwise>
			<div class="panel panel-primary">
				<div class="panel-heading">
					<c:out value=""></c:out>
					<span id="article_view_author">Author: ${ article.getAuthor().getName() }</span>
					<span id="article_view_date"><f:formatDate
							value="${ article.creationDate }" type="date" pattern="dd/MM/yyyy" /></span>
				</div>
				<div class="panel-body">
					<H2>${ article.getTitle() }</h2>
					${ article.getContentText() }
				</div>
				<div class="panel-footer">
				<div class="row">
					<div class="col-lg-2">
						<form action="admin-articles-print">
							<button class="btn btn-info" type="submit">Back</button>
						</form>
					</div>
					<div id="btns" class="col-lg-4" style="float: right;" align="right">
						<form name="edit_form" action="admin-article-edit" method="post">
							<input type="hidden" name="id" value="${ article.id }"> <input
								type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
						</form>
						<form name="delete_form" action="admin-article-delete"
							method="post">
							<input type="hidden" name="id" value="${ article.id }"> <input
								type="hidden" name="title" value="${ article.getTitle() }">
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
						</form>
						<sec:authorize
							access="hasAnyAuthority('EDIT_ARTICLE_MASTER', 'EDIT_ARTICLE_AUTHOR') ">
							<sec:authorize var="author_edit"
								access="hasAuthority('EDIT_ARTICLE_AUTHOR')" />
							<sec:authorize var="master_edit"
								access="hasAuthority('EDIT_ARTICLE_MASTER')" />
							<sec:authentication property="name" var="principalname" />
							<c:if
								test="${ master_edit or (author_edit and (principalname eq article.author.login)) }">
								<button form="form-edit-${ article.getId() }" type="button"
									class="btn btn-warning fa fa-edit"
									onclick="document.forms['edit_form'].submit();"
									style="margin: 1px;" title="Edit article"> Edit article</button>
							</c:if>
						</sec:authorize>
						<sec:authorize
							access="hasAnyAuthority('DELETE_ARTICLE_MASTER', 'DELETE_ARTICLE_AUTHOR') ">
							<sec:authorize var="author_del"
								access="hasAuthority('DELETE_ARTICLE_AUTHOR')" />
							<sec:authorize var="master_del"
								access="hasAuthority('DELETE_ARTICLE_MASTER')" />
							<sec:authentication property="name" var="principalname" />
							<c:if
								test="${ master_del or (author_del and (principalname eq article.author.login)) }">
								<button form="form-edit-${ article.getId() }" type="button"
									class="btn btn-danger fa fa-times"
									onclick="document.forms['delete_form'].submit();"
									style="margin: 1px;" title="Edit article"> Delete article</button>
							</c:if>
						</sec:authorize>
					</div>
				</div>
				</div>
			</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>
