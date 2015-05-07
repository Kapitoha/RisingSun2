<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" import="javax.servlet.jsp.PageContext" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ru" lang="ru">
<head>
	<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<%@ include file="../pages/jspf/meta.jsp"%>
<title>Rising sun news</title>
</head>

<body>
	<div id="wrapper">

		<!-- Header -->
		<div id="header">
			<%@ include file="../pages/jspf/header.jsp"%>
		</div>

		<!-- menu bar -->
		<div id="menu_pane">
			<%@ include file="../pages/jspf/menubar.jsp"%>
		</div>

		<!-- Content  -->
		<div id="content">

			<!-- Content box -->
			<div id="content-box">

				<!-- Content box left -->
				<div id="content-box-left">

					<div id="content-box-left-in">
						<div class="box">
							<div class="box-top">
								<div class="box-bottom">
									<div class="info-in">

									</div>
							<c:if test = "${!empty searchr}">
								<p>
									<span style="color: #003B69; font-size: medium; font-weight: bold;"> ${searchr}</span>
								</p>
							</c:if>
								</div>
							</div>
						</div>
						<!-- Content box with light blue background -->
						<div class="box">
							<div class="box-top">
								<div class="box-bottom">
									<div id="box-in">
										<div class="info-in">

										</div>
                                      <c:if test = "${!empty allnews}">
                                        <c:forEach items = "${allnews}" var = "news">
										<div class="article">
											<div class="date">
												<p class="day">
													<span><f:formatDate type="date" value="${news.dateCreate}" pattern="dd" />th</span>
												</p>
												<p><f:formatDate type="date" value="${news.dateCreate}" pattern="MM/yyyy" /></p>
											</div>
											<h3>
												<a href="/news/${news.namePage}"> ${news.title}&hellip;</a>
											</h3>

										</div>
                                        </c:forEach>
                                      </c:if>
                                    </div>
								</div>
							</div>
						</div>
						<!-- Content box with light blue background end -->

						<div class="paging" title=""></div>

					</div>
				</div>
				<!-- Content box left end -->

				<!-- Content box right -->
				<div id="content-box-right">
					<%@ include file="../pages/jspf/content-box-right.jsp"%>
				</div>
				<!-- Content box right end -->
				<div class="cleaner">&nbsp;</div>

			</div>
			<!-- Content box end -->

		</div>
		<!-- Content end -->

		<hr class="noscreen" />

		<!-- Footer -->
		<%@ include file="../pages/jspf/footer.jsp"%>
		<!-- Footer end -->

	</div>
</body>
</html>