<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/bootstrap.min.css">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<style type="text/css">

	table{
		margin-left: auto;
		margin-right: auto;
	}
	
	.bottom{
		padding: 50px,20px;
	}
	

</style>
<script type="text/javascript">
$(document).ready(function() {
});

	function fn_insertCheck(){
		
// 		 frmBbs.method.value ="post";
// 		 frmBbs.action="/bbs/bbsList";
// 		 frmBbs.searchType.value= selectedVal;
// 		 frmBbs.searchTitle.value= search;
// 		 frmBbs.submit();
		 
// 		 var selected = document.getElementById("searchType");
// 		 var selectedVal = selected.options[selected.selectedIndex].value;// 검색조건  선택한 value 값
// 		 var search = document.getElementById("searchTitle").value;
		 
		 var title = document.getElementById("title");
		 var resister = document.getElementById("wName");
		 var content = document.getElementById("content");
		
		 if(title.value==""){
			 alert("제목을 입력해주세요");
			 title.focus();
			 return false;
		 }
		 if(resister.value==""){
			 alert("작성자를  입력해주세요");
			 resister.focus();
			 return false;
		 }
		 if(content.value ==""){
			alert("내용을 입력해주세요");
			content.focus();
			return false;
		 }
		 if(title.value.length>20){
			 alert("제목은 20자 까지등록됩니다.")
			var titleVal  = title.value.substring(1,10);
			title.value = titleVal;
			title.focus();
			return false;
		 }
		 if(resister.value.length>10){
			 alert("작성자는 10자 까지등록됩니다.")
			var resisterVal  = resister.value.substring(1,9);
			resister.value = resisterVal;
			resister.focus();
			return false;
		 }
		 if(content.value.length>100){
			alert("내용은 100자 까지등록됩니다.")
			var contentVal  = content.value.substring(1,99);
			content.value = contentVal;
			content.focus();
			return false;
		 }
		 
		 
		 frmBbs.method.value ="post";
		 frmBbs.action="/bbs/bbsInsert";
		 frmBbs.submit();
}//fn_insertCheck()


	var i =1;
	
	var a = {};
	
	
	function fn_filePlus(){//파일등록열 추가
			
		var html="";
		++i;
		html +=  "<span id='delete"+i+"'  style='display:block'>"
		html += "<input type='file' name='fileName"+i+"' id='fileName"+i+"' > ";
		html += "<input type='button' value='삭제' onclick='javascript:fn_deleteRow("+i+");'/>";
		html += "</span> ";
		$("#fileLine").append(html);
	
}
	
	
	function fn_deleteRow(num){//파일등록열 삭제
	  	$("#delete"+num).remove();
	}

</script>
</head>
<body>
	<form  method="post" enctype="multipart/form-data" name="frmBbs">
		<table class="table-bordered" id="table">
			<colgroup>
				<col width="20%">
				<col width="120%">
			</colgroup>
			<tr>
				<th>*제목</th>
				<td><input type="text" id="title" name="title"  ></td>
			</tr>
			<tr>
				<th>작성자</th>			
				<td><input type="text" id="wName" name="wName" ></td>
			</tr>
			<tr>
				<th>*내용</th>
				<td><textarea  rows="30" id="content" name="content"></textarea></td>
			</tr>
			<tr >
				<th>*첨부파일 :</th>
				<td id="fileLine" >
					<span>
					<input type="file" name="fileName1" id="fileName1" >
					<input type="button" value="파일선택 추가" onclick="javascript:fn_filePlus();">
					</span>
				</td>
			</tr>
		</table>
		<div class="bottom">
				<input type="button" value="등록" onclick="javascript:fn_insertCheck()">
				<input type="reset" value="폼 초기화">
				<input type="button" onclick="location.href='/bbs/bbsList'" value="목록 보기"/>
		</div>
	</form>
</body>
</html>