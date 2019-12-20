<%@page import="com.egovstudy.bbs.vo.Member"%>
<header class="blog-header py-3">
	<div class="row">
		<div class="col-4"></div>
		<div class="col-4 text-center">
			<a class="blog-header-logo text-dark" href="/">Simple BBS</a>
		</div>
		<div class="col-4 text-right">
			<%
				Member member = (Member) session.getAttribute("member");
				if (member != null) {
			%>
				Hello, <%=member.getName()%>
				<a href="/bbs/mypage.do" class="btn btn-secondary">my page</a>
				<a href="/bbs/logout.do" class="btn btn-danger">logout</a>
			<%
				} else {
			%>
				<a class="btn btn-primary" href="/bbs/login.do">login</a>
				<a class="btn btn-light" href="/bbs/register.do">register</a>
			<%
				}
			%>
		</div>
	</div>

</header>