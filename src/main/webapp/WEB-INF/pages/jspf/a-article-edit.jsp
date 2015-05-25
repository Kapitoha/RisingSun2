<%--
  User: kapitoha
  Date: 22.05.15
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="coll" uri="/WEB-INF/tag/collection_utils.tld"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<sec:authorize
	access="hasAnyRole('EDIT_ARTICLE_MASTER', 'EDIT_ARTICLE_AUTHOR')">
	<script src="ckeditor/ckeditor.js"></script>
	<c:set var="article" value="${ article_obj }"></c:set>
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">Write article</div>
				<form role="form" action="admin-article-save" method="post">
					<div class="form-group">
						<label for="title">Title</label><br> <input type="text"
							name="title" class="form-control" value="${ article.title }"
							maxlength="255">
					</div>
					<label for="editor">Content</label><br>
					<textarea name="editor" id="editor" rows="20" cols="80">${ article.getContentText() }</textarea>
					<script type="text/javascript">
						CKEDITOR.replace('editor');
					</script>
					<c:choose>
						<c:when test="${ empty first_page }">
							<p>
								<input type="checkbox" name="show_main" />Show on first page
							</p>
						</c:when>
						<c:otherwise>
							<p>
								<input type="checkbox" name="show_main" checked />Show on first
								page
							</p>
						</c:otherwise>
					</c:choose>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
					<div>
						<input type="submit" value="save">
					</div>
				</form>
			</div>
		</div>
	</div>
</sec:authorize>