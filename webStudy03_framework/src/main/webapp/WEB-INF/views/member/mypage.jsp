<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- table 태그를 이용하여, 현재 로그인된 유저의 모든 정보를 출력. -->
	<%
		MemberVO authMember = (MemberVO) request.getAttribute("authMember");
	%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member/mypage.jsp</title>
<jsp:include page="/includee/preScript.jsp" />
<style type="text/css">
	.warn{
		color: red;
	}
	
</style>
<script type="text/javascript">
	$(function() {
		$("#updateBtn").on("click", function() {
			location.href="<%=request.getContextPath() %>/myDataUpdate.do";
		});
		
		$("#homeBtn").on("click", function() {
			location.href="<%=request.getContextPath() %>/index.do";
		});
		
		var leaveInput = $("[name='leavePass']");
		
		$("#leaveForm").on("submit", function() {
			let memPass = <%= authMember.getMem_pass() %>
			let chkPass = leaveInput.val();
			$(".warn").remove();
			
			if(!chkPass){
				leaveInput.after($("<span>").addClass('warn').text("비밀번호를 입력하세요."));
				return false;
			}else{
				if(chkPass != memPass){
					leaveInput.after($("<span>").addClass('warn').text("비밀번호가 일치하지 않습니다."));
					return false;
				}
			}
			
		});
		
		
		
		
	});

</script>
</head>
<body>
	
	<table class="table table-bordered">
		<tr>
			<th class="col">아이디</th>
			<td class="col"><%=authMember.getMem_id()%></td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td><%=authMember.getMem_pass()%></td>
		</tr>
		<tr>
			<th>회원명</th>
			<td><%=authMember.getMem_name()%></td>
		</tr>
		<tr>
			<th>주민번호1</th>
			<td><%=authMember.getMem_regno1()%></td>
		</tr>
		<tr>
			<th>주민번호2</th>
			<td><%=authMember.getMem_regno2()%></td>
		</tr>
		<tr>
			<th>생일</th>
			<td><%=authMember.getMem_bir()%></td>
		</tr>
		<tr>
			<th>우편번호</th>
			<td><%=authMember.getMem_zip()%></td>
		</tr>
		<tr>
			<th>주소1</th>
			<td><%=authMember.getMem_add1()%></td>
		</tr>
		<tr>
			<th>주소2</th>
			<td><%=authMember.getMem_add2()%></td>
		</tr>
		<tr>
			<th>집전번</th>
			<td><%=authMember.getMem_hometel()%></td>
		</tr>
		<tr>
			<th>회사전번</th>
			<td><%=authMember.getMem_comtel()%></td>
		</tr>
		<tr>
			<th>휴대폰</th>
			<td><%=authMember.getMem_hp()%></td>
		</tr>
		<tr>
			<th>이메일</th>
			<td><%=authMember.getMem_mail()%></td>
		</tr>
		<tr>
			<th>직업</th>
			<td><%=authMember.getMem_job()%></td>
		</tr>
		<tr>
			<th>취미</th>
			<td><%=authMember.getMem_like()%></td>
		</tr>
		<tr>
			<th>기념일</th>
			<td><%=authMember.getMem_memorial()%></td>
		</tr>
		<tr>
			<th>기념일자</th>
			<td><%=authMember.getMem_memorialday()%></td>
		</tr>
		<tr>
			<th>마일리지</th>
			<td><%=authMember.getMem_mileage()%></td>
		</tr>
		<tr>
			<th>탈퇴여부</th>
			<td><%=authMember.getMem_delete()%></td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="button" id="updateBtn" class="btn btn-primary" value="수정하기" /> 
				<input type="button" class="btn btn-primary" data-toggle="modal"
							 data-target="#leaveModal" value="탈퇴하기" /> 
				<input type="button" id="homeBtn" class="btn btn-primary" value="홈으로가기" /> 
			</td>
		</tr>
	</table>
<!-- Modal -->
<div class="modal fade" id="leaveModal" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="staticBackdropLabel">회원탈퇴</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <form id="leaveForm" action="<%=request.getContextPath()%>/leaveApp.do" method="post">
      <div class="modal-body">
     
      	회원 아이디 : <input type="text" name="leaveId" value="${authMember.mem_id}" readonly /> <br/>
		비밀번호 입력 : <input type="password" name="leavePass" />
      
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
        <button id="leaveBtn" type="submit" class="btn btn-primary">확인</button>
      </div>
      </form>
    </div>
  </div>
</div>
</body>
</html>