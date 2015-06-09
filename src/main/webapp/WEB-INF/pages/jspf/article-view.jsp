<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="row">
	<c:set var="article" value="${ article_obj }" />
	<div class="post-details">
		<ul>
			<li><i class="fa fa-user"> Author: </i><a href="search?author=${ article.author.id }" style="color: blue;">${ article.author.name }</a></li>
			<li><i class="fa fa-calendar"> Date: </i><f:formatDate value="${ article.creationDate }" type="date"/></li>
			<li>
				<i class="fa fa-tag"> Tags</i>
				<c:forEach var="tag" items="${ article.getTagList() }">
					<a class="label label-default" href="search?tag=${tag.name}" style="color: #fff;">${ tag.name }</a>
				</c:forEach>
			</li>
		</ul>
	</div>
	<div>
		<h2 align="center" style="margin: 30px;">${ article.title }</h2>
		<p>${ article.getContentText() }</p>
	</div>
</div>