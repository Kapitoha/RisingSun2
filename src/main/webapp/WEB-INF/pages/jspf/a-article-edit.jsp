<%--
  User: kapitoha
  Date: 22.05.15
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="coll" uri="/WEB-INF/tag/collection_utils.tld" %>
<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags" %>

<script src="ckeditor/ckeditor.js"></script>
<c:set var="article" value="${ article_obj }"></c:set>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">Write article</div>
            <form name="edit-form" id="edit-form" role="form" action="admin-article-save" method="post">
                <div class="form-group">
                    <label for="title">Title</label><br> <input type="text"
                                                                name="title" class="form-control"
                                                                value="${ article.getTitle() }"
                                                                maxlength="255">
                </div>
                <label for="editor">Content</label><br>
                <textarea name="editor" id="editor" rows="20" cols="80">${ article.getContentText() }</textarea>
                <script type="text/javascript">
                    CKEDITOR.replace('editor');
                </script>
                <label for="image">Image URL</label><br>
                <input type="url" name="image" class="form-control" value="${ article.getImageUrl() }">
                <button type="button" class="btn btn-info" 
                onclick="window.open(document.getElementsByName('image')[0].value)"
                style="margin: 5px;">View</button>
                <br>
                <label for="tags">Tags (input through the space or comma)</label>
                <input type="text" name="tags" class="form-control" value="${ article.convertTagsToString() }"
                                                                                             maxlength="100">
                                                                                             
                
                <sec:authorize access="hasAnyAuthority('MANAGE_FIRST_PAGE')">
                <div class="row">
                    <div class="article-action col-lg-5 col-md-5 col-sm-5">
                    	<input type="checkbox" id="show_main" name="show_main" ${ not empty article.getFirstPage()? 'checked' : '' }
                    	onchange="uncheck('archived')" /> Is on first page<br>
                        Position on first page <input type="number" min="1" maxlength="5" value="${ not empty article.getFirstPage()?
                        article.getFirstPage().show_order : 1 }" name="position" style="width: 70px;">
                    </div>
                    <div class="article-action col-lg-5 col-md-5 col-sm-5">
                    	<input type="checkbox" id="featured" name="featured" ${ not empty article.getFirstPage() &&
                                article.getFirstPage().featured? 'checked' : '' }
                                onchange="check('show_main'); uncheck('archived')"/> Is featured
                        <br>
                        <input type="color" name="title_color" value="${ article.getTitleColor() }"> Title font color <br>
                        <input type="number" name="title_font_size" value="${ article.getTitleFontSize() }" 
                        	min="1" max="300" style="width: 65px;"> Title font size 
                    </div>
                    <div class="separator"></div>
                </div>
                </sec:authorize>
                <sec:authorize access="hasAnyAuthority('MANAGE_ARCHIVE')">
                    <hr>
                	<div class="row">
	                	<div class="article-action col-lg-12">
		                    <input type="checkbox" id="archived" name="archived" ${ not empty article.archive? 'checked' : '' } 
		                    onclick="uncheck('show_main'); uncheck('featured');"/> Is Archived
	                	</div>
                	</div>
                </sec:authorize>
                <input type="hidden" name="id" value="${ article.id }">
                <input type="hidden" name="${_csrf.parameterName}"
                       value="${_csrf.token}"/>
            </form>
            <div class="panel-footer">
            <form id="cancel" action="admin-articles-print"></form>
                <div align="center" style="margin-bottom: 10px;">
                    <input class="btn btn-primary" form="edit-form" type="submit" value="Save article">
                	<button form="cancel" type="submit" class="btn btn-default">Cancel</button>
                </div>
            
            </div>
        </div>
    </div>
</div>