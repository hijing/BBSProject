<%@page import="kr.nao.bbs.BbsEntity"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<link rel="stylesheet" href="css/bootstrap.min.css">
<title>Insert title here</title>
</head>
	<%
	
	 		BbsEntity dto = new BbsEntity();
	
			dto = (BbsEntity)request.getAttribute("dto");
	
	
	%>
<script type="text/javascript">
$(document).ready(function(){
	
});

	function fn_updateCheck(){
		
		 var title = document.getElementById("title");
		 var content = document.getElementById("content");
		
		 if(title.value==""){
			 alert("제목을 입력해주세요");
			 title.focus();
			 return false;
		 }
		 if(content.value ==""){
			alert("내용을 입력해주세요");
			content.focus();
			return false;
		 }
		 if(title.value.length>20){
			 alert("제목은 20자 까지등록됩니다.")
			var titleVal  = title.value.substring(1,20);
			title.value = titleVal;
			title.focus();
			return false;
		 }
		 if(content.value.length>100){
			alert("내용은 100자 까지등록됩니다.")
			var contentVal  = content.value.substring(1,99);
			content.value = contentVal;
			content.focus();
			return false;
		 }
		 
		 frmBbs.action="/bbs/bbsUpdate";
		 frmBbs.submit();
		 
		 
	}



</script>	
<body>
	<form  method="post" enctype="multipart/form-data" name="frmBbs">
	<input type="hidden" id="bbsNo" name="bbsNo" value="<%=dto.getBbsNo()%>">
		<table class="table-bordered" >
			<tr>
				<th width="70">제목</th>
				<td width="400"><input type="text" id="title" name="title" value="<%=dto.getTitle() %>"></</td>
			</tr>
			<tr>
				<th width="70">작성자</th>
				<td><%=dto.getwName()%></td>
				<th width="70">작성일자</th>
				<td><%=dto.getwDate() %></td>
			</tr>
			<tr>
				<th width="70">첨부파일</th>
				<td width="400">
					<%if( dto.getFileName()!= null ){  %>
					<input type="file" name="fileName"><%=dto.getFileName() %>
					<%}else{ %>
					<input type="file" name="fileName">
					<%} %>
				</td>
			</tr>
			<tr>
				<th width="70">내용</th>
				<td width="400"><textarea name="content" id="content" rows="30" value="<%=dto.getContent() %>" ><%=dto.getContent() %></textarea></td>
			</tr>
		</table>
			<div style="float: right;">
				<input type="button" value="완료" onclick="javascript:fn_updateCheck()">
				<input type="reset"  value="폼 초기화">
				<input type="button" onclick="location.href='/bbs/bbsList'" value="목록 보기">
			</div>
	</form>
</body>
</html>