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
		let insertChild = $("#insertChild");
		
		
		pagingArea.on("click", "a", function() {
			
			let page = $(this).data("page");
			replyForm.find("[name='page']").val(page);
			replyForm.submit();
			replyForm.find("[name='page']").val(1);
			
		});
		
		function viewSuccess(resp) {

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
								}).addClass("updateBtn"),		
								$("<input>").attr({
									type:"button",
									value:"삭제"
								}).addClass("deleteBtn")		
							)
						).data("reply", reply)
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
		}
		
		function executeUpdate(resp) {
			let result = resp.result;
			if("OK" == result){
				replyInsertForm.get(0).reset();
				replyForm.submit();
			}else{
				
			}
		}
		
		let replyForm = $("#replyForm").ajaxForm({
		   dataType:  'json', 
		   success:   viewSuccess
		}).submit();
		
		let deleteForm = $("#deleteForm").ajaxForm({
			dataType: 'json',
			success: executeUpdate
		});
		
		let updateForm = $("#updateForm").ajaxForm({
			dataType: 'json',
			success: executeUpdate
		});
		
		let replyInsertForm = $("#replyInsertForm").ajaxForm({
		   dataType:  'json', 
		   success:   executeUpdate
		});
		
		replyTable.on("click", ".deleteBtn", function() {
					let reply = $(this).parents("tr:first").data("reply");
					deleteForm.find("[name='rep_no']").val(reply.rep_no);
					deleteForm.submit();
				})
				  .on("click", ".updateBtn", function() {
					  $('#updateModal').modal('show');
					  let reply = $(this).parents("tr:first").data("reply");
					  deleteForm.submit();
					  
				});
		
	});
	
</script>
</head>
<body>
	<input type="button" value="목록으로" onclick="location.href = '${pageContext.request.contextPath }/board/boardList.do'">
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
	<form id="replyInsertForm" action="${pageContext.request.contextPath}/reply/replyInsert.do" method="post">
		<input type="hidden" class="insertChild" name="bo_no" value="${board.bo_no}" />
		<input type="text" class="insertChild" name="rep_writer" placeholder="작성자" />
		<input type="password" class="insertChild" name="rep_pass" placeholder="비밀번호" />
		<input type="text" name="rep_ip" value="0:0:0:0:0:0:1" readonly="readonly" />
		<br/>
		<textarea rows="" cols="" name="rep_content"  ></textarea>
		<input type="submit" value="전송" />
		<input type="reset" value="취소" />
	</form>
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
	<form id="deleteForm" action="${pageContext.request.contextPath}/reply/replyDelete.do" method="POST">
		<input type="hidden" name="rep_no" />
		
	</form>
</body>
<!-- Modal -->
<div class="modal fade" id="updateModal" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="staticBackdropLabel">Modal title</h5>
      </div>
       <form id="updateForm" action="${pageContext.request.contextPath}/reply/replyUpdate.do" method="post">
      <div class="modal-body">
        	<input type="hidden" name="rep_no" />
	        <input type="text" name="rep_writer" />
			<input type="password" name="rep_pass" />
			<br/>
			<textarea rows="" cols="" name="rep_content"  ></textarea>
		<input type="submit" value="전송" />
      </div>
      <div class="modal-footer">
        <button type="submit" class="btn btn-primary">전송</button>
        <button type="reset" class="btn btn-primary">취소</button>
        <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
      </div>
      </form>
    </div>
  </div>
</div>
</html>












