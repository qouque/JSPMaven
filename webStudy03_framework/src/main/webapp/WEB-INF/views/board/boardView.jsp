<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp"/>
<script type="text/javascript">
	$(function() {
		let replyTable = $("#replyTable");
		let pagingArea = $("#pagingArea");
		
		pagingArea.on("click", "a", function() {
			
			let page = $(this).data("page");
			replyForm.find("[name='page']").val(page);
			replyForm.submit();
			replyForm.find("[name='page']").val(1);
			
		});
		
		
		let replyForm = $("#replyForm").on("submit", function(event) {
			event.preventDefault();
			
			let url = this.action?this.action:"";
			let method = this.method?this.method:"get";
			let data = $(this).serialize();
			
			$.ajax({
				url : url,
				data : data,
				method : method,
				dataType : "json",
				success : function(resp) {
					
					let trTags=[];
					let list = resp.data;
					if(list.length > 0){
						$(list).each(function(idx, reply) {
							trTags.push(
								$("<tr>").append(
									$("<td>").text(reply.rep_content),	
									$("<td>").text(reply.rep_writer+"("+reply.rep_ip+")"),	
									$("<td>").text(reply.rep_date),	
									$("<td>").append(
										$("<input>").attr({
											type:"button",
											value:"수정"
										}),		
										$("<input>").attr({
											type:"button",
											value:"삭제"
										})		
									)
								)
							);
						});
						
					}else{
						trTags.push(
							$("<tr>").append(
								$("<td colspan='4'>").text("댓글이없어용 ㅠㅠ")
							)
						);
					}
					
					replyTable.html(trTags);
					pagingArea.html(resp.pagingHTML_BS);
				},
				error : function() {

				}
			});
			return false;
		}).submit();
		
		
		
	});
	
</script>
</head>
<body>
	<table class="table">
		<tr>
			<td>제목 : ${board.bo_title}</td>
			<td>작성자 : ${board.bo_writer}</td>
			<td>IP : ${board.bo_ip}</td>
		</tr>
		<tr>
			<c:set value="${board.attatchList}" var="attatchList" />
			<c:choose>
				<c:when test="${not empty attatchList}">
					<td>
					<c:forEach var="attatch" items="${attatchList }">
						${attatch.att_filename }<br/>
					</c:forEach>
					</td>
				</c:when>
				<c:otherwise>
					<td>첨부파일 없음.</td>
				</c:otherwise>
			</c:choose>
			<td>작성일자 : ${board.bo_date}</td>
			<td>조회수 : ${board.bo_hit}</td>
		</tr>
		<tr>
			<td colspan="3">${board.bo_content}</td>
		</tr>
	</table>
	<h2>댓글리스트</h2>
	<div id="replyDiv">
		<table id="replyTable" class="table">
		
		</table>
		<div id="pagingArea">
			
		</div>
	</div>
	<form id="replyForm" action="${pageContext.request.contextPath}/reply/replyList.do" method="get">
		<input type="hidden" name="what" value="${board.bo_no}" />
		<input type="hidden" name="page" />
	</form>
</body>
</html>


