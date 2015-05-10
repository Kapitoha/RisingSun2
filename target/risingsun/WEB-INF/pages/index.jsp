<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" import="javax.servlet.jsp.PageContext" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ru" lang="ru">
<head>
	<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
	<script src="http://jtruncate.googlecode.com/svn/trunk/jquery.jtruncate.js" type="text/javascript"></script>

	<%@ include file="../pages/jspf/meta.jsp"%>
<title>Rising sun| Homepage</title>
</head>

<body>

<script type="text/javascript">
			$(document).ready(function() {
				$('.textarticle').jTruncate({
					length: 200, /* The number of characters to display before truncating. */
					minTrail: 0, /* The minimum number of "extra" characters required to truncate. This option allows you to prevent truncation of a section of text that is only a few characters longer than the specified length. */
					moreText: "Read More", // The text to use for the "more" link.
					lessText: "Read Less", // The text to use for the "less" link.
					ellipsisText: "..." // The text to append to the truncated portion.
				});
			});
</script>


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

						<!-- Content box with light blue background -->
						<div class="box">
							<div class="box-top">
								<div class="box-bottom">
									<div id="box-in">
                                      <c:if test = "${!empty allnews}">
                                        <c:forEach items = "${allnews}" var = "news">
										<div class="article">
											<div class="date">
												<p class="day">
													<span><f:formatDate type="date" value="${news[0].dateCreate}" pattern="dd" />th</span>
												</p>
												<p><f:formatDate type="date" value="${news[0].dateCreate}" pattern="MM/yyyy" /></p>
											</div>
											<h3>
												<a href="news/${news[0].namePage}"> ${news[0].title}&hellip;</a>
											</h3>
											<div class="info">
												<div class="info-in">
													<p>
														 Author: ${news[1].name}
													</p>
												</div>
											</div>
											<div class="textarticle">
												<p>
														${news[0].article}
												</p>
											</div>
											<p class="continue">
												[<a href="news/${news[0].namePage}">Continue&hellip;</a>]
											</p>



										</div>
											<p>


											</p>
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