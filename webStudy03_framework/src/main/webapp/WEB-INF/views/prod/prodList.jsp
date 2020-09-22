<%@page import="kr.or.ddit.vo.ProdVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp"/>
</head>
<%
	List<ProdVO> prodList = (List<ProdVO>) request.getAttribute("prodList");
%>
<body>
	<table class="table table-sm">
		<thead class="thead-dark">
			<tr>
				<th>상품코드</th>
				<th>상품이름</th>
				<th>상품분류</th>
				<th>구매처</th>
				<th>구매가</th>
				<th>판매가</th>
				<th>세일가</th>
				<th>상품개요</th>
				<th>상세정보</th>
				<th>이미지경로</th>
				<th>총재고</th>
				<th>입고일</th>
				<th>적정재고</th>
				<th>크기</th>
				<th>색상</th>
				<th>배송방법</th>
				<th>단위</th>
				<th>입고량</th>
				<th>판매량</th>
				<th>마일리지</th>
			</tr>
		</thead>
		<tbody>
			<%
				for(ProdVO prod : prodList){
				%>
					<tr>
						<td><%=prod.getProd_id()%></td>
						<td><%=prod.getProd_name()%></td>
						<td><%=prod.getProd_lgu()%></td>
						<td><%=prod.getProd_buyer()%></td>
						<td><%=prod.getProd_cost()%></td>
						<td><%=prod.getProd_price()%></td>
						<td><%=prod.getProd_sale()%></td>
						<td><%=prod.getProd_outline()%></td>
						<td><%=prod.getProd_detail()%></td>
						<td><%=prod.getProd_img()%></td>
						<td><%=prod.getProd_totalstock()%></td>
						<td><%=prod.getProd_insdate()%></td>
						<td><%=prod.getProd_properstock()%></td>
						<td><%=prod.getProd_size()%></td>
						<td><%=prod.getProd_color()%></td>
						<td><%=prod.getProd_delivery()%></td>
						<td><%=prod.getProd_unit()%></td>
						<td><%=prod.getProd_qtyin()%></td>
						<td><%= prod.getProd_qtysale()%></td>
						<td><%= prod.getProd_mileage()%></td>
					</tr>
				<%
				}
			%>
		</tbody>
	</table>
</body>
</html>