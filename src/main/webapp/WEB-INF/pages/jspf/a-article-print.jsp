<%--
  User: kapitoha
  Date: 22.05.15
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="coll" uri="/WEB-INF/tag/collection_utils.tld" %>

<c:set var="art_list" value="${ articles_list }"/>

<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Articles</h1>
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">Articles</div>
            <c:choose>
                <c:when test="${empty art_list}">
                    <p>There are no any article in database</p>
                </c:when>
                <c:otherwise>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <div class="dataTable_wrapper">
                            <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead id="article_print_head">
                                <tr>
                                    <th class="col-lg-0" id="a_id">ID</th>
                                    <th class="col-lg-7">Title</th>
                                    <th class="col-lg-1" id="a_author">Author</th>
                                    <th class="col-lg-1" id="a_date">Date</th>
                                    <th class="col-lg-0" id="a_F" title="Is placed on the first page">Main</th>
                                    <th class="col-lg-0" title="Position on the first page">Pos</th>
                                    <th class="col-lg-0" id="a_A" title="Is archived">A</th>
                                    <th class="col-lg-0" id="a_view"></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="article" items="${ art_list }">
                                    <tr class="odd gradeA">
                                        <td>${ article.id }</td>
                                        <td>${ article.title }</td>
                                        <td align="center">${ article.author.name }</td>
                                        <td>
                                            <p align="center"><f:formatDate value="${ article.creationDate }"
                                                                            type="date" pattern="dd/MM/yyyy"/></p>

                                            <p align="center"><f:formatDate value="${ article.creationDate }"
                                                                            type="time" pattern="HH:mm"/></p>
                                        </td>
                                        <td align="center" title="Is placed on the first page">
                                            <p align="center"
                                               style="${ not empty article.firstPage? 'background-color: #a2c044':'' }">${ not
                                                    empty
                                                            article.firstPage }</p>
                                        </td>
                                        <td align="center" title="Position on the first page"><c:out
                                                value="${ not empty article.firstPage? article.firstPage.show_order : ''
												 }"></c:out></td>
                                        <td align="center" title="Is archived">
                                            <p align="center"
                                               style="${ not empty article.archive? 'background-color: #a2c044':'' }">${
                                                not
                                                    empty
                                                            article.archive }</p>
                                        </td>
                                        <td align="center" class="center">
                                            <form action="admin-article-view">
                                                <input type="hidden" name="id" value="${ article.id }">
                                                    <%-- 											<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> --%>
                                                <input type="submit" value="View">
                                            </form>
                                        </td>
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