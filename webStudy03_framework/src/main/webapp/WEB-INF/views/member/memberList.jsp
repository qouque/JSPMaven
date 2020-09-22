<%@page import="java.util.List"%>
<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@page import="kr.or.ddit.vo.PagingVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp"/>
<script type="text/javascript">
	$(function() {
		
		let memberListTable = $("#memberListTable");
		let pagingArea = $("#pagingArea");
		
		pagingArea.on("click", "a", function() {
			
			let page = $(this).data("page");
			memberListForm.find("[name='page']").val(page);
			memberListForm.submit();
			memberListForm.find("[name='page']").val(1);
			
		});
		
		$("#memberListTable>tbody").on("click", 'a', function() {
			let who = $(this).data("who");
			$.ajax({
				url : "<%=request.getContextPath() %>/member/memberView.do",
				data : {
					who:who
				},
				method : "get",
				dataType : "html",
				success : function(resp) {
					$("#memberViewModal").find("#whoArea").text(who);
					$("#memberViewModal").find(".modal-body").html(resp);
					$("#memberViewModal").modal("show");
				},
				error : function() {

				}
			});
			
		});
		
		
		let memberListForm = $("#memberListForm").on("submit", function(event) {
			event.preventDefault();
			let url = this.action?this.action:location.href;
			let method = this.method?this.method:"get";
			let data = $(this).serialize(); // query string
			
			
			$.ajax({
				url : url,
				data : data,
				method : method,
				dataType : "json",
				success : function(resp) {
					memberListTable.find("tbody").empty();
					let memList = resp.data;
					let trTags = [];
					if(memList.length > 0){
						$(memList).each(function(idx, memberVO) {
							trTags.push(memberVO.memberInfo);
						})
					}else{
						trTags.push($("<tr>").html($("<td colspan='18'>").text("검색된 회원 없음")));
					}
					memberListTable.find("tbody").html(trTags);
					pagingArea.html(resp.pagingHTML_BS);
				},
				error : function() {

				}
			});
			
			
			return false;
		});
		//.submit()
		
	});
	


</script>
</head>
<body>
	<%
	PagingVO<MemberVO> pagingVO = (PagingVO<MemberVO>) request.getAttribute("pagingVO");
	
	%>
	<form id="memberListForm" action="<%= request.getContextPath() %>/member/memberList.do" method="get">
		<input type="hidden" name="page">
		<select name = "searchType">
			<option value="all">전체</option>
			<option value="name">이름</option>
			<option value="address">지역</option>
		</select>
		<input type="text" name="searchWord" placeholder="아이디를 입력하세용">
		<input type="submit" value="검색">
	
	</form>
	<table id="memberListTable" border="1" class="table table-sm">
		<thead>
		<tr class="table-info">
			<th>아이디</th>
			<th>이름</th>
			<th>비밀번호</th>
			<th>주민번호</th>
			<th>생일</th>
			<th>우편번호</th>
			<th>주소</th>
			<th>상세주소</th>
			<th>집전화</th>
			<th>회사번호</th>
			<th>핸드폰번호</th>
			<th>이메일</th>
			<th>직업</th>
			<th>취미</th>
			<th>기념일</th>
			<th>기념일날짜</th>
			<th>마일리지</th>
			<th>탈퇴여부</th>
		</tr>
		</thead>
		<tbody>
			<%
			List<MemberVO> list = pagingVO.getData();
			if(list != null && list.size() > 0){
				for(MemberVO member : list){
					%>
					<%= member.getMemberInfo() %>
					<%
				}
			}else{
				%>
				<tr>
					<td colspan='18'>검색 조건에 맞는 회원이 없음.</td>
				</tr>
				<%
			}
			
			%>
		</tbody>
	</table>
	<div id="pagingArea">
	  <%= pagingVO.getPagingHTML_BS() %>
	</div>

<div class="modal fade" id="memberViewModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg modal-dialog-scrollable">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel"><span id="whoArea"></span>의 상세정보</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
</body>
</html>















