<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="su" uri="/WEB-INF/tag/stringUtils.tld"%>
<div>
	<c:choose>
		<c:when test="${ empty result_list }">
			<p>Empty</p>
		</c:when>
		<c:otherwise>
			<%
			    Integer count = 0;
				Integer resultsPerPage = 10;
				Integer list_size = ((List) request.getAttribute("result_list")).size();
				Integer pages_count = Double.valueOf(list_size / resultsPerPage + 1).intValue();
			%>

			<div>
				<c:forEach var="page_num" begin="1" end='<%=pages_count%>'>
					<div class="portfolio-page" id="page-${ page_num }"
						style="${page_num > 1? 'display: none;' : ''}">

						<%
						    for (int i = 0; i < resultsPerPage; i++)
									{
						%>
						<c:set var="elem" value="<%=count++%>" />
						<c:set var="article" value="${ result_list.get(elem) }" />

						<div class="row">
							<div class="col-lg-12">
								<div class="panel panel-default">
									<div class="panel-heading">
										<i class="fa fa-user"> Author: </i><a
											href="search?author=${ article.author.id }"
											style="color: blue;">${ article.author.name }</a> <span><i
											class="fa fa-calendar"> Date: </i> <f:formatDate
												value="${ article.creationDate }" type="date" /></span>
									</div>
									<div class="panel-body">
										<h3 align="center">
											<a href="article-view?id=${article.id}">${ su:highlight(key_word, article.title) }</a>
										</h3>
										<div id="togleText-${article.id}" style="display: none">
											${ su:highlight(key_word, article.getContentText()) }
										</div>
									</div>
									<div>
										<button class="btn btn-success fa fa-arrows-v" type="button"
											id="displayText-${article.id}"
											onclick="toggle('togleText-${article.id}',
                                                'displayText-${article.id}')">
											Show</button>
									</div>
									<div class="panel-footer">
										<i class="fa fa-tag"> Tags: </i>
										<c:forEach var="tag" items="${ article.getTagList() }">
											<a class="label label-default" href="search?tag=${tag.name}"
												style="color: #fff;">${ tag.name }</a>
										</c:forEach>
									</div>
								</div>
							</div>
						</div>
						<%
						    if (count >= list_size)
										break;
									}
						%>

					</div>

				</c:forEach>
				<div class="pagination center-text">
					<ul class="nav">
						<c:forEach var="page" begin="1" end='<%=pages_count%>'>
							<li class="${ page eq 1? 'active':'' }">${ page }</li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</div>