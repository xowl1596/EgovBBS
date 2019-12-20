<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="./includes/imports.jsp"%>
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<%@include file="./includes/header.jsp"%>
		<main>
		<form action="/bbs/postModifyExec.do" method="post">
			<input type="hidden" name="id" value="${post.getId()}">
			<div class="form-group">
				<label for="title">Title:</label>
				<input type="text" id="title" name="title" value="${post.getTitle()}">
			</div>
			<div class="form-group">
				<label for="content">Content:</label>
				<textarea class="form-control" rows="5" id="content" name="content">${post.getContent()}</textarea>
			</div>
			<input class="btn btn-primary" type="submit" value="write">
		</form>
		</main>
	</div>
</body>
</html>