<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="./includes/imports.jsp"%>
<link rel="stylesheet" type="text/css" href="/bbs/common/css/imageWrite.css">
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<%@include file="./includes/header.jsp"%>
		<main>
		<form action="/bbs/imageWriteExec.do" method="post" enctype="multipart/form-data">
			<div class="form-group">
				<label for="title">Title:</label>
				<input type="text" id="title" name="title">
			</div>
			<div class="form-group">
				<label for="content">Content:</label>
				<textarea class="form-control" rows="5" id="content" name="content"></textarea>
			</div>
			<div class="form-group">
				<label for="content">Images:</label>
				<input type="file" id="files" name="files" multiple>
			</div>
			<div id="fileUpload">
			</div>
			<input class="btn btn-primary" type="submit" value="write">
		</form>
		</main>
	</div>
	<script>
		var fileForm = $("#files");
		var upload = $("#fileUpload");
		var fileList = [];
		
		upload.on("dragover", function(e){
			e.stopPropagation();
			e.preventDefault();
		});
		
		upload.on("drop", function(e){
			e.preventDefault();
			var fileInput = e.originalEvent.dataTransfer.files;
			
			//파일 배열에 추가
			for(var i = 0; i < fileInput.length; i++){
				fileList.push(fileInput[i]);
			}
			
			var html = "";
			
			for(var i = 0; i < fileList.length; i++){
				console.log(fileList[i]);
				html += "<div>" + fileList[i].name + "</div>";
			}
			
			upload.html(html);
			
			//배열을 FileList로 변환 후 폼에 갱신
			var dataTransfer = new DataTransfer();
			
			for(var i = 0; i < fileList.length; i++){
				dataTransfer.items.add(fileList[i]);	
			}
			
			console.log(dataTransfer.files);
			
			fileForm[0].files = dataTransfer.files;
			console.log(fileForm[0].files);
		});
	</script>
</body>
</html>