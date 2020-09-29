<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp"/>
<style type="text/css">
	


</style>
<script type="text/javascript">
	$(function() {
		
		let boardListForm = $("#boardListForm");
		let pagingArea = $("#pagingArea");
		let boardListTable = $("#boardListTable");
	
		pagingArea.on("click", "a", function() {
				
			let page = $(this).data("page");
			boardListForm.find("[name='page']").val(page);
			boardListForm.submit();
			boardListForm.find("[name='page']").val(1);
			
		});
		
		boardListForm.on("submit", function(event) {
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
					let boardList = resp.data
					let trTags = [];
					console.log(boardList);
					
					if(boardList.length > 0){
						$(boardList).each(function(idx, board) {
							trTags.push(
								$("<tr>").append(
									$("<td>").text(board.rnum),
									$("<td>").html(
											$("<a>").attr("href", 
													"${pageContext.request.contextPath }/board/boardView.do?what="+board.bo_no)
											.text(board.bo_title+"(글번호 : "+ board.bo_no +")")),
									$("<td>").text(board.bo_writer),
									$("<td>").text(board.bo_date),
									$("<td>").text(board.bo_hit)
								)
							);
						});
					}else {
						trTags.push(
							$("<tr>").append(
								$("<td colspan='4'>").text("검색된 게시판 없음")
							)
						);
					}
					console.log(trTags);
					boardListTable.find("tbody").html(trTags);
					pagingArea.html(resp.pagingHTML_BS);
				},
				error : function() {

				}
			});
			return false;
		});
		
		
	});
	

</script>
</head>
<body>
	<table id="boardListTable" class="table">
		<thead class="thead-dark">
		<tr>
			<th>글번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일자</th>
			<th>조회수</th>
		</tr>
		</thead>
		<tbody>
			<c:set var="list" value="${pagingVO.data }" />
				<c:forEach var="board" items="${list }">
					<tr>
						<td>${board.rnum }</td>
						<td><a href="${pageContext.request.contextPath }/board/boardView.do?what=${board.bo_no }">
						${board.bo_title }(글번호 : ${board.bo_no })</a></td>
						<td>${board.bo_writer }</td>
						<td>${board.bo_date }</td>
						<td>${board.bo_hit }</td>
					</tr>
				</c:forEach>
		</tbody>
	</table>
	<div id="pagingArea">
		${pagingVO.pagingHTML_BS }
		<input type="button" value="게시글 작성" onclick="location.href = '${pageContext.request.contextPath }/board/boardInsert.do'">
	</div>
	<form id="boardListForm" action="${pageContext.request.contextPath }/board/boardList.do" method="get">
		<input type="hidden" name="page" />
		<select name="searchType">
			<option value>선택</option>
			<option value="title">제목</option>
			<option value="content">글내용</option>
			<option value="writer">작성자</option>
			<option value="all">전체</option>
		</select>
		<input type="text" name="searchWord" />
		<input type="submit" value="검색" />
	</form>
</body>
</html>