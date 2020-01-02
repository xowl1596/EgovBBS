<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="./includes/imports.jsp"%>
<link rel="stylesheet" type="text/css" href="/bbs/common/css/loginForm.css">
<title>Insert title here</title>
</head>
<body>
	<%
		String loginResult = (String) session.getAttribute("loginResult");
		if(loginResult != null){
			out.print("<script>");
			out.print("alert(\"invalid ID or PW\")");
			out.print("</script>");
		}
		session.removeAttribute("loginResult");
	%>
	<div class="container">
		<%@include file="./includes/header.jsp"%>
		<div class="loginFormContainer">
			<form class="form" action="/bbs/loginExec.do" method="post">
				<div class="form-group">
					<label for="id">ID : </label>
					<input type="text" class="form-control" id="id" name="id" required autofocus>
				</div>
				
				<div class="form-group">
					<label for="pw">Password : </label>
					<input type="password" class="form-control" id="pw" name="pw" required>
				</div>

				<input type="submit" value="login">
				need account? <a href="/bbs/register.do">click here to Registration</a>
			</form>
		</div>
	</div>
</body>
</html>