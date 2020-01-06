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
		<form action="/bbs/imagePostModifyExec.do" method="post" enctype="multipart/form-data">
			<input type="hidden" name="id" value="${post.id}">
			<div class="form-group">
				<label for="title">Title:</label>
				<input type="text" id="title" name="title" value="${post.title}">
			</div>
			<div class="form-group">
				<label for="content">Content:</label>
				<textarea class="form-control" rows="5" id="content" name="content">${post.content}</textarea>
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

		$.ajax({
			url : '/bbs/postImage/'+${post.id}+'.do',
			type : 'get',
			success : function(response) {
				for(var key in response){
					var dataURL = "data:image/jpg;base64," + response[key];
					$('body').append("<img src='" + dataURL + "'>")
					
					var byteStr = atob(dataURL.split(',')[1]);
					var mimeStr = dataURL.split(',')[0].split(':')[1].split(';')[0];
					var arrBuff = new ArrayBuffer(byteStr.length);
					var intArr = new Uint8Array(arrBuff);
					
					for(var i = 0; i < byteStr.length; i++){
						intArr[i] = byteStr.charCodeAt(i);
					}
					
					var blob = new Blob([arrBuff], {type : mimeStr});
					var file = new File([blob], "image"+i+".jpg");
					
					fileList.push(file);
				}
				
				refreshFileForm();
				
				fileForm[0].files = createFileList(fileList);
			},
			fail : function(error) {
				console.log('!!!Error!!!');
			},
			always : function(response) {
				console.log("???");
			}
		});
		
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
			
			refreshFileForm();
			 
			 //배열을 FileList로 변환 후 폼에 갱신		
			fileForm[0].files = createFileList(fileList);
			console.log(fileForm[0].files);
		});
		
		function createFileList(files){
			var dataTransfer = new DataTransfer();
			
			for(var i = 0; i < files.length; i++){
				dataTransfer.items.add(files[i]);	
			}
			
			return dataTransfer.files;
		}
		
		function refreshFileForm(){
			var html = "";
			
			for(var i = 0; i < fileList.length; i++){
				console.log(fileList[i]);
				html += "<div data-index=" + i + ">" + fileList[i].name + "<a class='up' href='#'>+</a>  <a class='down'  href='#'>-</a> <a class='delete' href='#'>x</a></div>";
			}
			
			upload.html(html);
			
			$(".up").on("click", function(e){
				e.stopPropagation();
				e.preventDefault();

				var index = $(this).parent().data('index');
				
				if(index <= 0){
					return;
				}

				fileListSwap(index, index - 1);				
				refreshFileForm();
				
				fileForm[0].files = createFileList(fileList);
				console.log(fileForm[0].files);
			});
			
			$(".down").on("click", function(e){
				e.stopPropagation();
				e.preventDefault();

				var index = $(this).parent().data('index');

				if(index >= fileList.length - 1){
					return;
				}
				
				fileListSwap(index, index + 1);
				refreshFileForm();
				
				fileForm[0].files = createFileList(fileList);
				console.log(fileForm[0].files);
			});
			
			$(".delete").on("click", function(e){
				e.stopPropagation();
				e.preventDefault();

				var index = $(this).parent().data('index');

				fileList.splice(index, 1);
				refreshFileForm();
				
				fileForm[0].files = createFileList(fileList);
				console.log(fileForm[0].files);
			});
		}
		
		function fileListSwap(i, j){
			var temp = fileList[i];
			fileList[i] = fileList[j];
			fileList[j] = temp;
		}
	</script>
</body>
