<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:choose>
    <c:when test="${ empty result_list }">
        <p>Empty</p>
    </c:when>
    <c:otherwise>
        <c:forEach var="article" items="${ result_list }">
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-user"> Author: </i><a
                                href="search?author=${ article.author.id }"
                                style="color: blue;">${ article.author.name }</a>
                            <span><i class="fa fa-calendar"> Date: </i><f:formatDate value="${ article.creationDate }"
                                                                                     type="date"/></span>
                        </div>
                        <div class="panel-body">
                            <h3 align="center"><a href="article-view?id=${article.id}">${ article.title }</a></h3>
                            <p>${ article.getContentText() }</p>
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
        </c:forEach>
    </c:otherwise>
</c:choose>