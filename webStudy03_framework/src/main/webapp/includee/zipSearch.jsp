<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp" />
<style type="text/css">
	h4{
		text-align: center;
	}
	div{
		text-align: center;
	}
	table{
		margin-left: auto;
		margin-right: auto;
	
	}
</style>
<script type="text/javascript">
$(function() {
	$('input:button').on('click',function(){
		
		// 입력한 동 이름 가져오기
		dongval = $('#dong').val();
		
		$.ajax({
			url : '<%= request.getContextPath() %>/searchZip.do',
			type : 'post',
			data : {'dong' : dongval},
			success : function(res) {
				
				code = "<table border='1'>";
				code += "<tr><td>우편번호</td><td>주소</td><td>번지</td></tr>";
					
				$.each(res, function(i, v) {
					code += "<tr class = 'ziptr'><td>"+v.zipcode+"</td><td>"+v.sido+" "+v.gugun+" "+v.dong+"</td><td>"+v.bunji+"</td></tr>";
				})
				code += "</table>";
				$('#result1').html(code);
			},
			error : function(xhr) {
				alert("상태 : " + xhr.status);
			},
			dataType : 'json'
		});
	});
	
	
	// 검색된 주소 리스트에서 하나를 선택하는 이벤트 - delegate 방식
	$('#result1').on('click', '.ziptr', function() {
		
		code = $('td:eq(0)',this).text();
		addr = $('td:eq(1)',this).text();
		
		
		
		$('#zip', opener.document).val(code);
		$('#add1', opener.document).val(addr);
		window.close();
	})
})
</script>
</head>
<body>
<h4>우편번호검색</h4>
<div class = "box">
		동입력 <input type="text" id = "dong"> 
		<input type = "button" value = "확인">
		<div id = "result1"></div>
	</div>
</body>
</html>