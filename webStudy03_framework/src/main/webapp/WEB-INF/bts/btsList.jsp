<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src = "<%= request.getContextPath()%>/js/jquery-3.5.1.min.js"></script>
<script type="text/javascript">

	$(function() {
		var result = $("#result");
		$("#btsSelect").on("change", function(event) {
			let key = $(this).find("option:selected").val();
			let data = {};
			data["member"] = key 
			console.log(data);
<%-- 			location.href = "<%= request.getContextPath() %>/bts/getMemberPage.do?member="+key; --%>
			$.ajax({
				url : "<%=request.getContextPath()%>/bts/getMemberPage.do",
				data : data,
				dataType : "html",
				success : function(resp) {
					result.html(resp);
				},
				error : function() {

				}
			});
			
		})
		
		
		
	})



</script>
</head>
<body>
<select id = "btsSelect">

	<option value>멤버선택</option>
	<%
		Map<String, String[]> btsMap = (Map) getServletContext().getAttribute("btsMap");
		
		Set<String> keySet = btsMap.keySet();
		Iterator<String> it = keySet.iterator();
		String pattern = "<option value = '%s'>%s</option>";
		while(it.hasNext()){
			String mapKey = it.next();
			String[] value =  btsMap.get(mapKey);
			out.println(String.format(pattern, mapKey, value[0]));
		}
	
	%>
	
</select>
<br/>
하나의 멤버를 선택하면, change 이벤트 핸들러 내에서<br/>
"/bts/getMemberPage.do?member=B001" 요청 발생.<br/>
해당 요청의 결과로 멤버의 개인 페이지 랜더링.<br/>
단, bts 폴더아래에 있는 개인페이지의 주소를 클라이언트가 모르도록.<br/>
<div id = "result">

</div>
</body>
</html>












