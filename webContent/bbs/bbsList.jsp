<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="kr.nao.bbs.BbsEntity"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	ArrayList<BbsEntity> list = (ArrayList<BbsEntity>)request.getAttribute("list");
	int nowpage = ((Integer)request.getAttribute("page")).intValue();        // 현재페이지
	int maxpage = ((Integer)request.getAttribute("maxpage")).intValue();     // 마지막페이지	
	int startpage = ((Integer)request.getAttribute("startpage")).intValue(); // 시작페이지
	int endpage = ((Integer)request.getAttribute("endpage")).intValue();	   // 마지막페이지(페이징)	
	int countPage = ((Integer)request.getAttribute("countPage")).intValue(); // 보여줄 페이지숫자
	int listCount = ((Integer)request.getAttribute("listcount")).intValue(); // 전체게시물수
	int limit = ((Integer)request.getAttribute("limit")).intValue();       // 한 페이지에 보여줄 게시물수
	
	String title = (String)request.getAttribute("TT");
	String type = (String)request.getAttribute("Type");

%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/bootstrap.min.css">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<style type="text/css">
#frmBbs{
	margin-left: auto;
	margin-right: auto;
}
td{
	text-align: center;
}
.divRight{float: right;}
</style>
<script type="text/javascript">
	$(document).ready(function(){
		
	});
	
	function fn_detail(param1) {
// 		var form = document.createElement("form");
// 		form.setAttribute("charset", "UTF-8");
// 		form.setAttribute("method", "post");
// 		form.setAttribute("action", "/bbs/bbsDetail");
		 
// 		var hiddenField = document.createElement("input");
// 		hiddenField.setAttribute("type", "hidden");
// 		hiddenField.setAttribute("name", "bbsNo");
// 		hiddenField.setAttribute("value", param1);
// 		form.appendChild(hiddenField);
		
// 		var hiddenField = document.createElement("input");
// 		hiddenField.setAttribute("type", "hidden");
// 		hiddenField.setAttribute("name", "urlName");
// 		hiddenField.setAttribute("value", "/bbs/bbsDetail.jsp");
// 		form.appendChild(hiddenField);
		
// 		document.body.appendChild(form);
// 		form.submit();
		
		frmBbs.action = "/bbs/bbsDetail?bbsNo="+param1+"&urlName=/bbs/bbsDetail.jsp";
		frmBbs.submit();
	}
	
	function fn_search(){
		var selected = document.getElementById("searchType");
		var selectedVal = selected.options[selected.selectedIndex].value;// 검색조건  선택한 value 값
		var search = document.getElementById("searchTitle").value;   // 검색창 입력값
		if(selectedVal == 0){
			location.href="/bbs/bbsList";
		}else{
			frmBbs.method.value ="post";
			frmBbs.action="/bbs/bbsList";
			frmBbs.searchType.value= selectedVal;
			frmBbs.searchTitle.value= search;
		 	frmBbs.submit();
		}
	} 
	
	function fn_page(param1) {
		var selected = document.getElementById("searchType");
		var selectedVal = selected.options[selected.selectedIndex].value; // 검색조건  선택한 value 값
		var search = document.getElementById("searchTitle").value; // 검색창 입력값
		
		if (selectedVal == 0 && search) {
			frmBbs.action = "/bbs/bbsList";
			frmBbs.page.value = param1;
			frmBbs.submit();
		} else {
			frmBbs.method.value = "post";
			frmBbs.action = "/bbs/bbsList";
			frmBbs.searchType.value = selectedVal;
			frmBbs.searchTitle.value = search;
			frmBbs.page.value = param1;
			frmBbs.submit();
		}
	}
	
	function fn_ajax(){
		var reqUrl = "/bbs/ajax";
		
		$.ajax({
			url		: reqUrl,
			type    : "POST",
			dataType: "json",
			contentType:"application/json; charset:UTF-8",
			success : function(data){
				console.log("data.list:"+data.list);
				console.log("data.paging:"+data.paging);
				
				var list = JSON.parse(data.list);
				console.log("list 파싱:"+list);
				
				var html="";
				html+="<table border='1'>";
				
				$.each(list,function(i,v){
					html+="<tr>";
					html+="<td>"+v.bbsNo+"</td>";
					html+="<td>"+v.title+"</td>";
					html+="<td>"+v.wDate+"</td>";	
					html+="<td>"+v.content+"</td>";
					html+="<td>"+v.hit+"</td>";
					html+="</tr>";
				})
				html+="</table>";
				$('#frmJson').append(html);	
				
				var page = "";
				var paging = data.paging;
				var paging1 = JSON.parse(data.paging);
				
				page += "<div>";
				page += "<span style='color:red;'>" + paging1.totalCount + "</span>";
				page += "</div>";
				
				$('#frmJson').after(page);
				
// 				var html ="";
// 				html+="<table border='1'>";
// 				$.each(data.list,function(i,v){
// 					html+="<tr>";
// 					html+="<td>"+v.bbsNo+"</td>";
// 					html+="<td>"+v.wName+"</td>";
// 					html+="<td>"+v.title+"</td>";	
// 					html+="</tr>";
// 				})
// 				html+="</table>";
// 				$('#frmJson').append(html);	

			}, error :function(xhr,data,error){
	        	console.log("에러!:"+error);
	        	console.log("data:"+ typeof data);
	        	
	        	var str = JSON.stringify(data);
	        	console.log("data변환후:"+ typeof str);
	        }
		})//ajax
	}
	
</script>
</head>
<body>
	<div style="margin-left: auto; margin-right: auto; width=50%; border=0; cellpadding=0; cellspacing=0;">
		<div>자유 게시판</div>
			<div class="divRight">
				<select id="searchType" name="searchType">
<%
				if(type == null){
%>
					<option value="0" selected="selected">검색조건</option>
					<option value="1">제목</option>
					<option value="2">작성자</option>
<%
				}else{
					if (type.equals("0")) {
%>
					<option value="0" selected="selected">검색조건</option>
					<option value="1">제목</option>
					<option value="2">작성자</option>
<%
					}
					if (type.equals("1")) {
%>
					<option value="0">검색조건</option>
					<option value="1" selected="selected">제목</option>
					<option value="2">작성자</option>
<%
					}
					if (type.equals("2")) {
%>
					<option value="0">검색조건</option>
					<option value="1">제목</option>
					<option value="2" selected="selected">작성자</option>
<%
					}
				}
%>
			</select>
<%
		if(title != null){
%>
			<input type="text" id="searchTitle" name="searchTitle"  value="<%=title %>"  placeholder="검색어을 입력해주세요"  style="width: 50" />
<%
		}else{
%>
			<input type="text" id="searchTitle" name="searchTitle"  value=""  placeholder="검색어을 입력해주세요"  style="width: 50" />
<%
		}
%>
			<input type="button" onclick="javascript:fn_search()" value="검색" class="btn btn-success active" >
		</div>
	
		<form method="post" name="frmBbs" id="frmBbs">
			<input type="hidden" id="page" name="page" value="1">
			<input type="hidden" id="bbsno" name="bbsno" value="">
			<input type="hidden" id="searchType" name="searchType" value="0">
			<input type="hidden" id="searchTitle" name="searchTitle">
			
			<table class="table-striped">
				<colgroup>
				<col width="5%">
				<col width="50%">
				<col width="15%"> 
				<col width="25%">
				<col width="5%">
			</colgroup>
			<thead>
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>등록일</th>
					<th>조회수</th>
				</tr>
			</thead>
			<tbody>
<% 
				if(list.size() > 0){ 
					for(int i = 0; i < list.size(); i++){	
						BbsEntity dto = new BbsEntity();
						dto = list.get(i);
%>
				<tr>
					<td><%=listCount - ((nowpage-1) * limit + i) %></td>
					<td><a href="javascript:fn_detail('<%=dto.getBbsNo() %>')"><%=dto.getTitle() %></a></td>
					<td><%=dto.getwName() %></td>
					<td><%=dto.getwDate() %></td>
					<td><%=dto.getHit() %></td>
				</tr>
<%
					}
				}else{
%>
				<tr>
					<td colspan="5">해당되는 정보가 없습니다.</td>
				</tr>
<%
				}
%>
			</tbody>
		</table>
<!-- 				<tr align=center height=20> -->
<!-- 				<td colspan=7 style=font-family:Tahoma;font-size:10pt;> -->
			<div>
<%
				if(nowpage <= 1){

				}else{
%>
					<a href="#" onclick="javascript:fn_page(<%=nowpage-1%>)">[이전]</a>&nbsp;
<%
				}
				for(int iCount =startpage; iCount <=endpage; iCount++){// page 를 넣었어야함
					if(iCount==nowpage){
%>
						<span style="color: red; ">[<%=iCount %>]</span>
<%
					}else{
%>
						<a href="#" onclick="javascript:fn_page(<%=iCount%>)">[<%=iCount %>]</a>&nbsp;
<%
					}
				}
				
				if(nowpage < maxpage){
%>
					<a href="#" onclick="javascript:fn_page(<%=nowpage+1%>)">[다음]</a>
<%
				}
%>	
			</div>
<!-- 				</td> -->
<!-- 			</tr> -->
		</form>
		
		<div class="divRight">
			<input type="button" class="btn btn-success active" value="글쓰기" onclick="location.href='/bbs/bbs/insertForm.jsp'" />
<%
			if(type != null && title != null){
%>
			<input type="button" class="btn btn-success active" value="전체 조회 화면 " onclick="location.href='/bbs/bbsList'" />
<%
			}
%>
			<input type="button" value="ajax" onclick="javascript:fn_ajax();">
		</div>
	</div>	
	<div id="frmJson"></div>	
</body>
</html>