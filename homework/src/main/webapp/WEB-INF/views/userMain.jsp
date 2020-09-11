<%@page import="kr.or.ddit.vo.UserVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>유저 관리</h2>
<%
	List<UserVO> userList = (List<UserVO>) request.getAttribute("userList");
	List<String> userIdList = (List<String>) request.getAttribute("userIdList");
%>
<form>
	<select>
		<option value>아이디 선택</option>
		<%
			for(String userId : userIdList){
		%>
				<option><%= userId %></option>
		<%
			}
		%>
	</select>
</form>
<br/>
<br/>
<table border="1">
	<thead>
		<tr>
			<th>USERID</th>
			<th>USERNM</th>
			<th>PASS</th>
			<th>REG_DT</th>
			<th>ALIAS</th>
			<th>ADDR1</th>
			<th>ADDR2</th>
			<th>ZIPCODE</th>
			<th>FILENAME</th>
			<th>REALFILENAME</th>
		</tr>
	</thead>
	<tbody>
		<%
			for(UserVO vo : userList){
		%>
				<tr>
					<td><%= vo.getUserid() %></td>
					<td><%= vo.getUsernm() %></td>
					<td><%= vo.getPass() %></td>
					<td><%= vo.getReg_dt() %></td>
					<td><%= vo.getAlias() %></td>
					<td><%= vo.getAddr1() %></td>
					<td><%= vo.getAddr2() %></td>
					<td><%= vo.getZipcode() %></td>
					<td><%= vo.getFilename() %></td>
					<td><%= vo.getRealfilename() %></td>
				</tr>
		<%
			}
		
		%>
	</tbody>
</table>
</body>
</html>