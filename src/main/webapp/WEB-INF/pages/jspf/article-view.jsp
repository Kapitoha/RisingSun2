<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:choose>
	<c:when test="${ empty article_obj }">
		<c:redirect url="404"></c:redirect>
	</c:when>
	<c:otherwise>
		<div class="row">
			<c:set var="article" value="${ article_obj }" />
			<div class="post-details">
				<ul>
					<li><i class="fa fa-user"> Author: </i><a
						href="search?author=${ article.getAuthor().getId() }"
						style="color: blue;">${ article.getAuthor().getName() }</a></li>
					<li><i class="fa fa-calendar"> Date: </i> <f:formatDate
							value="${ article.creationDate }" type="date" /></li>
				</ul>
			</div>
			<div>
				<h2 align="center" style="margin: 30px;">${ article.getTitle() }</h2>
				<p>${ article.getContentText() }</p>
			</div>
		</div>
		<div id="view-article-tag-block">
			<hr>
			<i class="fa fa-tag"> Tags</i>
			<c:forEach var="tag" items="${ article.getTagList() }">
				<a class="label label-default" href="search?tag=${tag.getName()}"
					style="color: #fff;">${ tag.getName() }</a>
			</c:forEach>
		</div>
		<div id="disqus_thread"></div>
		<script type="text/javascript">
			/* * * CONFIGURATION VARIABLES * * */
			var disqus_shortname = 'kattoha';

			/* * * DON'T EDIT BELOW THIS LINE * * */
			(function() {
				var dsq = document.createElement('script');
				dsq.type = 'text/javascript';
				dsq.async = true;
				dsq.src = '//' + disqus_shortname + '.disqus.com/embed.js';
				(document.getElementsByTagName('head')[0] || document
						.getElementsByTagName('body')[0]).appendChild(dsq);
			})();
		</script>
		<noscript>
			Please enable JavaScript to view the <a
				href="https://disqus.com/?ref_noscript" rel="nofollow">comments
				powered by Disqus.</a>
		</noscript>

	</c:otherwise>
</c:choose>
