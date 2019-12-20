<%@page import="com.egovstudy.vo.TestVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="write.do" method="post">
		<input type="text" name="attr">
		<input type="submit" value="submit">
	</form>
	<hr>
	<c:forEach items="${vo}" var="item">
		<input value="${item.getAttr() }" id="${item.getId()}">
		<a href="#" onclick="update(${item.getId()});">update</a>
		<a href="delete.do?id=${item.getId()}">remove</a><br>
	</c:forEach>
	<script>
		function update(id){
			var form = document.createElement("form");
			form.action="update.do";
			form.method="post";
			
			var idInput = document.createElement("input");
			var attrInput = document.createElement("input");
			idInput.name="id";
			idInput.value = id;
			attrInput.name="attr";
			attrInput.value=document.getElementById(id).value;
			
			form.appendChild(idInput);
			form.appendChild(attrInput);
			document.body.appendChild(form);
			form.submit();
		}
	</script>
</body>
</html>