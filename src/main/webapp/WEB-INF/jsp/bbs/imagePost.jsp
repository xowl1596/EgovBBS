<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="./includes/imports.jsp" %>
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<%@include file="./includes/header.jsp" %>
		<main>
			<div>
				<div>${post.getWriter()}</div><hr>
				<div>${post.getTitle()}</div><hr>
				<div>
					<c:forEach var="image" items="${images}">
						<img src="/bbs/postImage/${image.post}/${image.seq}.do" width="500px"><br>
					</c:forEach>
				</div>
				<pre>
${post.getContent()}
				</pre>
				<div>
					<form id="commentForm" action="/bbs/comment/write/${post.getId()}.do" method="post">
						<textarea name="content"></textarea>
						<input type="submit" class="btn btn-primary" value="comment">
					</form>
					
					<div id="commentList">
					</div>
				</div>
				<a class="btn btn-primary" href="/bbs/postModify/${post.getId()}.do">Modify</a>
				<a class="btn btn-danger" href="/bbs/postDelete/${post.getId()}.do">Delete</a>
			</div>
		</main>
	</div>
</body>
</html>