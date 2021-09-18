<%@page import="java.util.ArrayList"%>
<%@page import="kr.nao.bbs.BbsFileEntity"%>
<%@page import="java.util.List"%>
<%@page import="kr.nao.bbs.BbsEntity"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/bootstrap.min.css">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	
});

	function fn_updateForm(param1){
		
		$("#bbsNo").val(param1);
		
		var url = "/bbs/bbsUpdate";
		$("#frmBbs").attr("action",url);
		$("#frmBbs").attr("target","_self");
		$("#frmBbs").submit();
		
	}
	
	function fn_delete(param1){
		
		
// 		var url = "/bbs/bbsDelete";
// 		$("#frmBbs").attr("action",url);
// 		$("#frmBbs").attr("target","_self");
// 		$("#frmBbs").submit();
		
		var result = confirm("정말 삭제 하시겠습니까?"); 
		if(result){
			
		 var form = document.createElement("form");
		 form.setAttribute("charset", "UTF-8");
		 form.setAttribute("method", "post");
		 form.setAttribute("action", "/bbs/bbsDelete");
		 
		 var hiddenField = document.createElement("input");
		 hiddenField.setAttribute("type", "hidden");
		 hiddenField.setAttribute("name", "bbsNo");
		 hiddenField.setAttribute("value", param1);
		 form.appendChild(hiddenField);
		 
		 document.body.appendChild(form);
		 form.submit();
		}else{
			
		}
	}

	function fn_download(param1){
		
// 		frmBbs.method.value ="post";
// 		frmBbs.action="/bbs/bbsList";
//		frmMain.boardSeq.value = pId;
//      frmMain.boardCountUp.value = "true";	
// 		frmBbs.submit();


		frmBbs.method.value ="post";
		frmBbs.action="/bbs/fileDown";
		frmBbs.bbsNo.value = param1;
		frmBbs.submit();
		
		
	}
	
	function Url(no){
		$.ajax({
			url		: "/bbs/u/shortUrl?bbsNo="+no,
			type    : "POST",
			dataType: "json",
			contentType:"application/json; charset:UTF-8",
			success:function(data){
				
			},
			error:function(error){
				
			}
	});
	}
</script>
<%
		BbsEntity dto = new BbsEntity();
		List<BbsFileEntity> fileList = new ArrayList<BbsFileEntity>();
		
		dto = (BbsEntity)request.getAttribute("dto");
	
		fileList = (ArrayList<BbsFileEntity>)request.getAttribute("fileList");

		
%>
</head>
<body>
	<form action="/bbs/bbsDetail" method="post" name="frmBbs">
	<input type="hidden" name="bbsNo" id="bbsNo" value="<%=dto.getBbsNo()%>">
	<input type="hidden" name="urlName" id="urlName" value="/bbs/bbsUpdate.jsp">
		<table class="table-bordered">
			<tr>
				<th width="70">제목</th>
				<td width="400"><%=dto.getTitle() %></td>
			</tr>
			<tr>
				<th width="70">작성자</th>
				<td><%=dto.getwName() %></td>
				<th width="70">작성일자</th>
				<td><%=dto.getwDate() %></td>
			</tr>
			<tr>
				<th width="70">내용</th>
				<td width="400"><textarea rows="30" readonly="readonly"><%=dto.getContent() %></textarea></td>
			</tr>
			<tr>
				<th width="70">첨부파일</th>
				<td width="400">
					<%if(fileList.size()>0){ 
						for(int i =0; i <fileList.size();i++){
					%>
					<span style="display: block;"><a href="#" onclick="javascript:fn_download(<%=dto.getBbsNo()%>)"><%=fileList.get(i).getoName()%></a></span>
					<%
					     }	
					  }else{
						%>
					<span>등록된 파일이 없습니다</span>
					<%} %>	
				</td>
			</tr>
		</table>
			<div style="float: right;">
				<input type="button" onclick="javascript:Url('<%=dto.getBbsNo() %>');" value="단축URL">
				<input type="submit" value="수정" />
				<input type="button" onclick="javascript:fn_delete('<%=dto.getBbsNo() %>')" value="삭제">
				<input type="button" onclick="location.href='/bbs/bbsList'" value="목록 보기">
			</div>
	</form>
</body>
</html>