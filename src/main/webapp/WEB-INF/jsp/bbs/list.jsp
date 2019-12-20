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
		<%@include file="./includes/header.jsp"%>
		<div class="main">
			<table id="example" class="display">
			<thead>
					<tr>
						<th>id</th>
						<th>title</th>
						<th>content</th>
						<th>writer</th>
					</tr>
				</thead>
			</table>
			<a href="/bbs/write.do" class="btn btn-light">write</a>
		</div>
	</div>

	<script>
		$(document).ready(function() {
			$('#example').DataTable({
				"ajax" :
					"http://localhost:8080/bbs/posts.do",
				"info" : 
					false,
				"ordering" : 
					false,
				"columns" :
					[
						{"data" : "id"},
						{"data" : "title",
						 "render" : function(data, type,row, meta) {
										if (type === 'display') {
											data = '<a href="/bbs/post/' + row.id + '.do">'+ data + '</a>';
										}			
										return data;
									}
						},
						{"data" : "content"}, 
						{"data" : "writer"}
					]
			});
		});
	</script>
</body>
</html>