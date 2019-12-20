<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="./includes/imports.jsp" %>
<link rel="stylesheet" type="text/css" href="/bbs/common/css/myPage.css">
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<%@include file="./includes/header.jsp" %>
		<%
			if(member == null){
				out.print("<script>");
				out.print("alert('로그인 후 이용 가능합니다.');");
				out.print("location.href='/'");
				out.print("</script>");
			} else{
		 %>
		<form action="/bbs/memberModifyExec.do" method="post">
			<div class="myPageWrapper">
				<table class="table">
					<tr><td>ID</td><td><%=member.getId() %></td></tr>
					<tr><td>Password</td><td><input type="password" name="pw"></td></tr>
					<tr><td>Name</td><td><input type="text" name="name" value="<%=member.getName() %>"></td></tr>
					<tr><td>tel</td><td><input type="text" name="tel" value="<%=member.getTel() %>"></td></tr>
				</table>
				<input type="submit" class="btn btn-primary" value="confirm">
			</div>
		</form>
		<%
			}
		%>
	</div>
</body>
</html>