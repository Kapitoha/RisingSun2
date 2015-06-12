<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<header>
    <h1 class="center-text">News</h1>
    <h5 class="center-text">
        This is first page of a news portal developed by Rising Sun
    </h5>
</header>
<c:choose>
    <c:when test="${empty firstPages}">
        <p>There are no articles</p>
    </c:when>
    <c:otherwise>
        <div id="page-content" class="center-text">
            <c:forEach var="page" items="${firstPages}">
                <c:if test="${ page.featured }">
                    <c:set var="article" value="${ page.article }"/>
                    <div class="portfolio-group">
                        <div class="portfolio-item">
                            <div id="face">
                                <img src="${ article.imageUrl }" alt="">

                                <h3>${article.title}</h3>
                            </div>
                            <div class="detail"
                                 onclick="invoke('article-view?id=${article.id}');"
                                 title="Click to view full version">
                                <div>${article.getContentText(true)}</div>
                            </div>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </div>
        <hr>
        <div class="row" id="news">
            <c:forEach var="page" items="${firstPages}">
                <c:set var="article" value="${ page.article }"/>
                    <div class="thumbnail col-lg-3" id="news-item"
                         style="background-color: #EEE6FA; ${page.featured?'border: 2px solid red' : ''}">
                        <img id="ico" alt="" src="${article.imageUrl }" onclick="invoke('article-view?id=${article.id}');"
                             style="cursor: pointer;">
                        <c:if test="${page.featured}">
                            <img id="top-news" src="img/star.gif" alt="Top news">
                        </c:if>
                        <div class="caption">
                            <h4>
                                <a href="article-view?id=${article.id}">${ article.title }</a>
                            </h4>
                        </div>
                        <div class="about" style="bottom: 0; position: absolute;">
                            <ul>
                                <li><i class="fa fa-user"></i><a href="search?author=${article.author.id}"
                                                                 style="color: blue;">${ article.author.name }</a></li>
                                <li><i class="fa fa-calendar"></i> <f:formatDate value="${ article.creationDate }"
                                                                                 type="date"/></li>
                                <c:if test="${ not empty article.getTagList() }">
                                    <li>
                                    	<i class="fa fa-tag"></i> 
                                    	<c:forEach var="tag" items="${ article.getTagList() }">
	                                        <a class="label label-default" href="search?tag=${tag.name}"
	                                           style="color: #fff;">${ tag.name}
	                                        </a>
                                    	</c:forEach>
                                    </li>
                                </c:if>
                            </ul>
                        </div>
                    </div>
<!--                 <div class="col-lg-3" id="news-item"> -->
<!--                 </div> -->
            </c:forEach>
        </div>
    </c:otherwise>
</c:choose>
