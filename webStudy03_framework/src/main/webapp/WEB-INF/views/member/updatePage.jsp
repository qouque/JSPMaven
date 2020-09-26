<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp" />
<script type="text/javascript">
	$(function() {
		var registForm = $("#updateForm");
		var listArea = $('#listArea');
		registForm.validate();
		
		$("#cancelBtn").on("click", function() {
			location.href="${pageContext.request.contextPath}/mypage.do";
		})
		
		//우편번호 가져오기
		$("#searchBtn").on("click",function(){
// 			event.preventDefault();
			listArea.empty();
			// 입력한 동 이름 가져오기
			dongval = $('#dong').val();
// 			let url = this.action;
// 			let method = this.method;
// 			let data = $(this).serialize();
			
			$.ajax({
				url : "${pageContext.request.contextPath}/searchZip.do",
				type : 'post',
				data : {"dong" : dongval},
				dataType : 'json',
				success : function(res) {
					
// 					$(res).each(function(idx, zipData){
// 						console.log(zipData);
// 						zipDataTable.row.add(zipData);
// 					});
					code = "<table border='1' id='zipTable' class='display'>";
					code += "<thead><tr><td>우편번호</td><td>주소</td><td>번지</td></tr></thead>";
						
					$.each(res, function(i, v) {
						code += "<tr class = 'ziptr'><td>"+v.zipcode+"</td><td>"+v.sido+" "+v.gugun+" "+v.dong+"</td><td>"+v.bunji+"</td></tr>";
					})
					code += "</table>";
					$('#listArea').html(code);
					
					$('#zipTable').dataTable({
						"pagingType": "full_numbers"
					});
					
				},
				error : function(xhr) {
					alert("상태 : " + xhr.status);
				}
			});
// 			return false;
		});
	
		
		// 검색된 주소 리스트에서 하나를 선택하는 이벤트 - delegate 방식
		$('#listArea').on('click', '.ziptr', function() {
			
			code = $('td:eq(0)',this).text();
			addr = $('td:eq(1)',this).text();
			
			
			
			$('#zip').val(code);
			$('#add1').val(addr);
			$('#exampleModal').modal("hide");
// 			window.close();
		})
		
		if("${message}"){
			alert("${message}");
		}
	});

</script>
</head>
<body>
<jsp:useBean id="member" class="kr.or.ddit.vo.MemberVO" scope="request"></jsp:useBean>
<jsp:useBean id="errors" class="java.util.LinkedHashMap" scope="request"></jsp:useBean>
<form action="${pageContext.request.contextPath}/myDataUpdate.do" id="updateForm" method="post" enctype="multipart/form-data">
		
		<table class="table table-bordered">
			<tr>
				<th>아이디</th>
				<td><input type="text" name="mem_id" value ="${member.mem_id}" maxLength="15" readonly required/>
					<span class="error">${errors["mem_id"]}</span>
				</td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td><input type="text" name="mem_pass"  maxLength="15" required/>
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
				<th>사진</th>
				<td><input type="file" name="mem_image" value ="" /></td>
			</tr>
			<tr>
				<th>우편번호</th>
				<td><input id="zip" type="text" name="mem_zip" value ="${member.mem_zip}" maxLength="7" readonly required/>
				<button type="button"  class="btn btn-primary" data-toggle="modal" data-target="#exampleModal" id="searchZip">
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
				<td colspan="2"><input type="submit" value="전송" />
				 <input id="cancelBtn"	type="button" value="취소" /></td>
			</tr>
		</table>
	</form>
<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">우편번호검색</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
<%--       	<form action="<%= request.getContextPath() %>/searchZip.do" method="post" id="searchForm"> --%>
			<div class = "box">
				동입력 <input type="text" id = "dong" name="dong"> 
				<input id="searchBtn" type = "button" value = "확인">
			</div>
<!--       	</form> -->
		<div id = "listArea">
<!-- 			<table id="zipTable"> -->
<!-- 				<thead> -->
<!-- 					<tr> -->
<!-- 						<th>우편번호</th> -->
<!-- 						<th>주소</th> -->
<!-- 						<th>번지</th> -->
<!-- 					</tr> -->
<!-- 				</thead> -->
<!-- 				<tbody> -->
				
<!-- 				</tbody> -->
<!-- 			</table> -->
		</div>
      </div>
      <div class="modal-footer">
      </div>
    </div>
  </div>
</div>
</body>
</html>