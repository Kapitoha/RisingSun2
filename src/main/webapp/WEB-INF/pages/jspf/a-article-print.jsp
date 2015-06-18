<%--
  User: kapitoha
  Date: 22.05.15
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="coll" uri="/WEB-INF/tag/collection_utils.tld" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
                                    <th class="col-lg-0" title="Position on the first page">Pos.</th>
                                    <th class="col-lg-0" title="Is featured">Featured</th>
                                    <th class="col-lg-0" id="a_A" title="Is archived">Archived</th>
                                    <th class="col-lg-0" id="a_view"></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="article" items="${ art_list }">
                                    <tr class="odd gradeA">
                                        <td>${ article.id }</td>
                                        <td>${ article.getTitle() }</td>
                                        <td align="center">${ article.getAuthor().getName() }</td>
                                        <td>
                                            <p align="center"><f:formatDate value="${ article.creationDate }"
                                                                            type="date" pattern="dd/MM/yyyy"/></p>

                                            <p align="center"><f:formatDate value="${ article.creationDate }"
                                                                            type="time" pattern="HH:mm"/></p>
                                        </td>
                                        <td align="center" title="Is placed on the first page">
                                            <p align="center"
                                               style="${ not empty article.getFirstPage()? 'background-color: #a2c044':'' }">${ not
                                                    empty
                                                            article.getFirstPage() }</p>
                                        </td>
                                        <td align="center" title="Position on the first page"><c:out
                                                value="${ not empty article.getFirstPage()? article.getFirstPage().show_order : ''
												 }"></c:out></td>
										<td align="center" title="Is featured">
											<p align="center"
                                               style="${ article.getFirstPage().isFeatured()? 'background-color: #a2c044':'' }">${ not
                                                    empty
                                                            article.getFirstPage() }</p>
										</td>
                                        <td align="center" title="Is archived">
                                            <p align="center"
                                               style="${ not empty article.archive? 'background-color: #a2c044':'' }">${
                                                not
                                                    empty
                                                            article.archive }</p>
                                        </td>
                                        <td align="center" class="center">
                                            <form id="form-fiew-${ article.getId() }" action="admin-article-view">
                                                <input type="hidden" name="id" value="${ article.id }">
                                                    <%-- 											<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> --%>
                                            </form>
                                            <form id="form-edit-${ article.getId() }" action="admin-article-edit" method="post">
                                            	<input type="hidden" name="id" value="${ article.id }">
                                            	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                            </form>
                                                <button form="form-fiew-${ article.getId() }" class="btn btn-info fa fa-eye" type="submit" 
                                                	title="View article" style="margin: 1px;">
                                                </button>
                                                <sec:authorize access="hasAnyAuthority('EDIT_ARTICLE_MASTER', 'EDIT_ARTICLE_AUTHOR') ">
                                                	<sec:authorize var="author_edit" access="hasAuthority('EDIT_ARTICLE_AUTHOR')"/>
                                                	<sec:authorize var="master_edit" access="hasAuthority('EDIT_ARTICLE_MASTER')"/>
                                                	<sec:authentication property="name" var="principalname"/>
                                                	<c:if test="${ master_edit or (author_edit and (principalname eq article.author.login)) }">
		                                                <button form="form-edit-${ article.getId() }" 
			                                                type="button" class="btn btn-warning fa fa-edit" 
			                                                onclick="document.forms['form-edit-${ article.getId() }'].submit();" 
			                                                style="margin: 1px;"
			                                                title="Edit article">
		                                                </button>
                                                	</c:if>
                                                </sec:authorize>
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