<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
				<pre>
${post.getContent()}
				</pre>
				<div>
					<form id="commentForm" action="/bbs/comment/write/${post.getId()}.do" method="post">
						<textarea name="content"></textarea>
						<input type="submit" class="btn btn-primary" value="comment">
					</form>
					
					<div id="commentList">
						<div>Comments</div><hr>
					</div>
				</div>
				<a class="btn btn-primary" href="/bbs/postModify/${post.getId()}.do">Modify</a>
				<a class="btn btn-danger" href="/bbs/postDelete/${post.getId()}.do">Delete</a>
			</div>
		</main>
	</div>
	<script>
	$.ajax({
		url : '/bbs/comment/list/' + ${post.getId()} + '.do',
		type : 'get',
		dataType : "json",
		success : function(response) {
			console.log(response);
			for(var i = 0; i < response.length; i++){
				$('#commentList').append(response[i].writer + " | " + response[i].content + "<br>");
			}
		},
		fail : function(error) {
			alert('!!!Error!!!');
		},
		always : function(response) {}
	});
	</script>
</body>
</html>