<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="./includes/imports.jsp" %>
<link rel="stylesheet" type="text/css" href="/bbs/common/css/imageList.css">
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<%@include file="./includes/header.jsp"%>
		<div class="main">
			<nav>
				<ul class="nav">
					<li class="nav-item">
						<a class="nav-link" href="/">Posts</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="/bbs/imageList.do">Images</a>
					</li>
				</ul>
			</nav>
			<a href="/bbs/imageWrite.do" class="btn btn-light">write</a>
			<div class="album row">
				<c:forEach var="post" items="${posts}" >
					<div class="col-md-4">
						<div class="card mb-4 box-shadow">
							<img class="card-img-top" src="/bbs/thumbnail/${post.id}.do">
							<div class="card-body">
								<a href="/bbs/imagePost/${post.id}.do"><c:out value="${post.title}"/></a>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>

	<script>
	</script>
</body>
</html>