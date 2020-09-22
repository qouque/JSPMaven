<%@page import="kr.or.ddit.vo.DataBase_PropertiesVO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>10/jdbcDesc.jsp</title>
<script type="text/javascript" src = "<%= request.getContextPath() %>/js/jquery-3.5.1.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("[name=property_name]").val("${param.property_name}");
		
		$("#nameSelecter").on("change", function() {
			$("#propForm").submit();
		})
		
		$("#propForm").on("submit", function(event) {
			event.preventDefault();
			
			let url = this.action?this.action:"";
			let method = $(this).attr("method")?this.method:"get";
			let data = $(this).serialize(); // query string 생성
			console.log(data);
			
			$.ajax({
				url : url,
				data : data,
				method : method,
				dataType : "json",
				success : function(resp) {
					let trTags = [];
					
					if(resp.length > 0){
						$(resp).each(function(idenx, dbProp){
							let tr = $("<tr>");
							tr.append(
								$("<td>").text(dbProp.property_Name),	
								$("<td>").text(dbProp.property_Value),	
								$("<td>").text(dbProp.description)
							);
							trTags.push(tr);
						});
						
					}else{
						let tr = $("<tr>").attr({
							colspan : "3"
						}).append($("<td>").text("검색 결과가 없음."));
						trTags.push(tr);
					}
					$("#listArea").html(trTags);
				},
				error : function(errorResp) {
					
				}
			});	
			
			return false;
		});
	});

</script>
</head>
<body>
<h4>JDBC(Java DataBase Connectivity)</h4>
<pre>
	Facade Pattern : Facade 객체를 통해 실객체에 명령을 전달하는 구조.
	
	JDBC 단계
	
	1. 드라이버를 빌드패스에 추가.(porm.xml)
	2. 드라이버 로딩(드라이더 클래스를 로딩)
	3. Connectoin 생성
	4. 쿼리 객체 생성
		1) Statement
		2) PreparedStatement
		3) CallableStatement
	5. 쿼리 실행
		1) ResultSet executeQuery(sql)
		2) int  executeUpdate(sql)
	6. 결과 집합 핸들링
	7. 자원을 release
	<%
		List<DataBase_PropertiesVO> list = (List<DataBase_PropertiesVO>) request.getAttribute("propList");
		DataBase_PropertiesVO paramVO = (DataBase_PropertiesVO) request.getAttribute("param");
		List<String> propNames = (List<String>) request.getAttribute("propNames");
	%>

</pre>
<form id="propForm">
	프로퍼티명 :
	<select id="nameSelecter" name = "property_name">
		<option value>프로퍼티명선택</option>
		<%
			
			for(String name : propNames){
				%>
				<option><%= name %></option>
				<%
			}
		
		%>
	</select>
<%-- 	 <input type="text" name="property_name" value ="${param.property_name}" /> <br/> --%>
	프로퍼티값 : <input type="text" name="property_value" value ="${param.property_value}" />
	<input type="submit" value = "검색" /> 
</form>
<table border = "1">
	<thead>
		<tr>
			<th>PROPERTY_NAME</th>
			<th>PROPERTY_VALUE</th>
			<th>DESCRIPTION</th>
		</tr>
	</thead>
	<tbody id = "listArea">
		<%
			String pattern = "<td> %s </td>";
			for(DataBase_PropertiesVO dbpVO : list){
				out.println("<tr>");
				out.println(String.format(pattern, dbpVO.getProperty_Name()));
				out.println(String.format(pattern, dbpVO.getProperty_Value()));
				out.println(String.format(pattern, dbpVO.getDescription()));
				out.println("</tr>");
			}
		
		%>
	</tbody>
</table>

</body>
</html>






















