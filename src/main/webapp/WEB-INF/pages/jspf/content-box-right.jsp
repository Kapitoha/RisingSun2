<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="content-box-right-in">
	<h4>Archive</h4>
	<ul>
		<c:if test = "${!empty archive}">
		<c:forEach items = "${archive}" var = "arch">
			<li><a href="/archive/${arch}"><f:formatDate type="date" value="${arch}" pattern="MM-yyyy" /></a></li>
		</c:forEach>
		</c:if>
	</ul>

	<h4>Contact information</h4>
	<address>
		<span>E-mail:</span> nick&#64;
		<!---->
		name.cz<br /> <span><abbr title="Windows Live Messenger">WLM</abbr>:</span>
		nick&#64;
		<!---->
		name.cz<br /> <span>Jabber:</span> nick&#64;
		<!---->
		name.cz
	</address>

</div>