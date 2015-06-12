<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="article" value="${ article_obj }" />
<script type="text/javascript">
	function invoke(url)
	{
		document.location.href = url;
	}
</script>
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<c:out value=""></c:out>
				<span id="article_view_author">Author: ${ article.author.name }</span>
				<span id="article_view_date"><f:formatDate
						value="${ article.creationDate }" type="date" pattern="dd/MM/yyyy" /></span>
			</div>
			<div class="panel-body">
				<H2>${ article.title }</h2>
				${ article.getContentText() }
			</div>
			<div class="panel-footer">
				<div id="tags"></div>
				<div id="btns">
				<form name="edit_form" action="admin-article-edit" method="post">
					<input type="hidden" name="id" value="${ article.id }">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form>
				<form name="delete_form" action="admin-article-delete" method="post">
					<input type="hidden" name="id" value="${ article.id }">
					<input type="hidden" name="title" value="${ article.title }">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form>
				<sec:authorize access="hasAnyAuthority('EDIT_ARTICLE_MASTER', 'EDIT_ARTICLE_AUTHOR')">
					<button class="btn btn-warning" type="button" onclick="document.forms['edit_form'].submit();">Edit article</button>
				</sec:authorize>
				<sec:authorize access="hasAnyAuthority('DELETE_ARTICLE_MASTER', 'DELETE_ARTICLE_AUTHOR')">
					<button class="btn btn-danger" type="button" onclick="document.forms['delete_form'].submit();" title="Delete article">Del.</button>
				</sec:authorize>
				</div>
			</div>
		</div>
	</div>
</div>
