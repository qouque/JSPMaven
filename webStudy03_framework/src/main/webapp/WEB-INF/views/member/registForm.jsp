<%@page import="java.util.Objects"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp" />
<style type="text/css">

	#registForm {
		width:700px;
	}
	
</style>
<script type="text/javascript">
	$(function() {
		var registForm = $("#registForm");
		var idInputTag = $("[name='mem_id']");
		var listArea = $('#listArea');
		registForm.validate();
		
		
		var zipDataTable = $("#zipTable").DataTable({
			pageLength:7,
			lengthChange:false,
			select:"single",
			columns:[
				{data:"zipcode"},
				{data:"address"}
			]
		});

		//우편번호 가져오기
		$("#searchForm").on("submit", function(event){
			event.preventDefault();
			$(this).find("tbody").empty();
			// 입력한 동 이름 가져오기
			searchWord = $('#searchWord').val();
			let url = this.action;
			let method = this.method;
			let data = $(this).serialize();
			
			$.ajax({
				url : url,
				method : method,
				data : data,
				dataType : 'json',
				success : function(res) {
					if(res.length>0){
						$(res).each(function(idx, zipData){
							console.log(zipData);
							zipDataTable.row.add(zipData);
						});
					}else{
						
					}
					zipDataTable.draw(false);

					
				},
				error : function(xhr) {
					alert("상태 : " + xhr.status);
				}
			});
			return false;
		});
	
		
		// 검색된 주소 리스트에서 하나를 선택하는 이벤트 - delegate 방식
		$('#listArea').on('click', 'tr', function() {
			
			code = $('td:eq(0)',this).text();
			addr = $('td:eq(1)',this).text();
			
			
			
			$('#zip').val(code);
			$('#add1').val(addr);
			$('#staticBackdrop').modal("hide");
// 			window.close();
		})
		
		$("input").addClass("form-control");
		
		idInputTag.on("change", function() {
			registForm.data("validId", false);
			$(this).next('.idMsg').remove();
		});
		
		registForm.on("submit", function() {
			let valid = $(this).data("validId");
			if(!valid){
				alert("아이디 중복 체크 하세용~")
				valid=false;
			}
			return valid;
			
		});
		
		$("#checkBtn").on("click", function() {
			
			let inputId = idInputTag.val();
			//console.log(mem_id);
			$.ajax({
				url : "<%=request.getContextPath()%>/idCheck.do",
				// inputId 파라미터로 중복 확인
				data : {
					inputId : inputId
				},
				method : "post",
				dataType : "json",
				success : function(resp) {
					if(resp.valid){
						let msgTag = idInputTag.next(".idMsg");
						if(msgTag.length == 0){
							idInputTag.after("<span class='idMsg'>아이디 사용가능</span>");
						}
						
						registForm.data("validId", true);
					}else{
						alert("아이디 중복, 바꾸삼요");
						idInputTag.focus();
					}
				},
				error : function() {

				}
			});
			
		});
	<%
		String message = (String) request.getAttribute("message");
		if(StringUtils.isNotBlank(message)){
			%>
			alert("<%= message %>");
			<%
		}
	%>
	});
</script>
</head>

<jsp:useBean id="member" class="kr.or.ddit.vo.MemberVO" scope="request"></jsp:useBean>
<jsp:useBean id="errors" class="java.util.LinkedHashMap" scope="request"></jsp:useBean>
<body>
	<form id="registForm" method="post" enctype="multipart/form-data">
		<table class="table table-bordered">
			<tr>
				<th>아이디</th>
				<td><input type="text" name="mem_id" value ="${member.mem_id}" maxLength="15" required/>
				<button type="button" class="btn btn-primary" id="checkBtn">아이디 중복체크</button>
					<span class="error">${erros["mem_id"]}<%= errors.get("mem_id") %></span>
				</td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td><input type="text" name="mem_pass" value ="${member.mem_pass}" maxLength="15" required/>
					<span class="error">${errors["mem_pass"]}</span>	
				</td>
			</tr>
			<tr>
				<th>회원명</th>
				<td><input type="text" name="mem_name" value ="${member.mem_name}" maxLength="20" required/>
					<span class="error">${errors["mem_name"]}</span>
					</td>
			</tr>
			<tr>
				<th>주민번호1</th>
				<td><input type="text" name="mem_regno1" value ="${member.mem_regno1}" maxLength="6" required/>
				<span class="error">${errors["mem_regno1"]}</span>
				</td>
			</tr>
			<tr>
				<th>주민번호2</th>
				<td><input type="text" name="mem_regno2" value ="${member.mem_regno2}" maxLength="7" required/>
				<span class="error">${errors["mem_regno2"]}</span>
				</td>
			</tr>
			<tr>
				<th>생일</th>
				<td><input type="date" name="mem_bir" value ="${member.mem_bir}" placeholder="1999-01-01" pattern="yy-MM-dd" required/>
				<span class="error">${errors["mem_bir"]}</span>
				</td>
			</tr>
			<tr>
				<th>우편번호</th>
				<td><input id="zip" type="text" name="mem_zip" value ="${member.mem_zip}" maxLength="7" readonly required/>
				<button type="button"  class="btn btn-primary" data-toggle="modal" data-target="#staticBackdrop" id="searchZip">
				우편번호 검색</button>
				<span class="error">${errors["mem_zip"]}</span>
				</td>
			</tr>
			<tr>
				<th>주소1</th>
				<td><input id="add1" type="text" name="mem_add1" value ="${member.mem_add1}" maxLength="100" readonly required/>
					
					<span class="error">${errors["mem_add1"]}</span>
				</td>
			</tr>
			<tr>
				<th>주소2</th>
				<td><input type="text" name="mem_add2" value ="${member.mem_add2}" maxLength="80" required/>
				<span class="error">${errors["mem_add2"]}</span>
				</td>
			</tr>
			<tr>
				<th>집전번</th>
				<td><input type="text" name="mem_hometel" value ="${member.mem_hometel}" maxLength="14" required/>
				<span class="error">${errors["mem_hometel"]}</span>
				</td>
			</tr>
			<tr>
				<th>회사전번</th>
				<td><input type="text" name="mem_comtel" value ="${member.mem_comtel}" maxLength="14" required/>
				<span class="error">${errors["mem_comtel"]}</span>
				</td>
			</tr>
			<tr>
				<th>휴대폰</th>
				<td><input type="text" name="mem_hp" value ="${member.mem_hp}" maxLength="15" />
				<span class="error">${errors["mem_hp"]}</span>
				</td>
			</tr>
			<tr>
				<th>이메일</th>
				<td><input type="text" name="mem_mail" value ="${member.mem_mail}" maxLength="40" required/>
				<span class="error">${errors["mem_mail"]}</span>
				</td>
			</tr>
			<tr>
				<th>직업</th>
				<td><input type="text" name="mem_job" value ="${member.mem_job}" maxLength="40" /></td>
			</tr>
			<tr>
				<th>취미</th>
				<td><input type="text" name="mem_like" value ="${member.mem_like}" maxLength="40" /></td>
			</tr>
			<tr>
				<th>기념일</th>
				<td><input type="text" name="mem_memorial" value ="${member.mem_memorial}" maxLength="40" /></td>
			</tr>
			<tr>
				<th>기념일자</th>
				<td><input type="date" name="mem_memorialday" value ="${member.mem_memorialday}" placeholder="1999-01-01" pattern="yy-MM-dd"/></td>
			</tr>
			<tr>
				<th>사진</th>
				<td><input type="file" name="mem_image" value ="" /></td>
			</tr>
			
			<tr>
				<td colspan="2"><input type="submit" value="전송" /> <input
					type="reset" value="취소" /></td>
			</tr>
		</table>
	</form>
<!-- Modal -->
<div class="modal fade" id="staticBackdrop" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="staticBackdropLabel">우편번호검색</h5>
      </div>
      <div class="modal-body">
         <form action="${pageContext.request.contextPath}/searchZip.do" method="get" id="searchForm">
			<div class = "box">
				동입력 <input type="text" id = "searchWord" name="searchWord"> 
				<input id="searchBtn" type = "submit" value = "확인">
			</div>
      	</form>
		<div id = "listArea">
			<table id="zipTable">
				<thead>
					<tr>
						<th>우편번호</th>
						<th>주소</th>
					</tr>
				</thead>
				<tbody>
				
				</tbody>
			</table>
		</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
        <button type="button" class="btn btn-primary">확인</button>
      </div>
    </div>
  </div>
</div>

</body>
</html>













