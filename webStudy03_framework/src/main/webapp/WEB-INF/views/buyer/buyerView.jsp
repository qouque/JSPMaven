<%@page import="kr.or.ddit.vo.ProdVO"%>
<%@page import="java.util.List"%>
<%@page import="kr.or.ddit.vo.BuyerVO"%>
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
	BuyerVO buyer = (BuyerVO) request.getAttribute("buyer");

%>
<body>
	<table class="table table-bordered">
		<tr>
			<th>거래처코드</th>
			<td><%= buyer.getBuyer_id()%></td>
		</tr>
		<tr>
			<th>거래처명</th>
			<td><%= buyer.getBuyer_name()%></td>
		</tr>
		<tr>
			<th>거래처분류</th>
			<td><%=buyer.getLprod_nm()%></td>
		</tr>
		<tr>
			<th>은행</th>
			<td><%=buyer.getBuyer_bank()%></td>
		</tr>
		<tr>
			<th>계좌</th>
			<td><%=buyer.getBuyer_bankno()%></td>
		</tr>
		<tr>
			<th>계좌주</th>
			<td><%=buyer.getBuyer_bankname()%></td>
		</tr>
		<tr>
			<th>우편번호</th>
			<td><%=buyer.getBuyer_zip()%></td>
		</tr>
		<tr>
			<th>주소1</th>
			<td><%=buyer.getBuyer_add1()%></td>
		</tr>
		<tr>
			<th>주소2</th>
			<td><%=buyer.getBuyer_add2()%></td>
		</tr>
		<tr>
			<th>전화번호</th>
			<td><%=buyer.getBuyer_comtel()%></td>
		</tr>
		<tr>
			<th>팩스번호</th>
			<td><%=buyer.getBuyer_fax()%></td>
		</tr>
		<tr>
			<th>이메일</th>
			<td><%= buyer.getBuyer_mail()%></td>
		</tr>
		<tr>
			<th>담당자</th>
			<td><%= buyer.getBuyer_charger()%></td>
		</tr>
		<tr>
			<th>내선번호</th>
			<td><%= buyer.getBuyer_telext()%></td>
		</tr>
		<tr>
			<th>거래 물품 목록</th>
			<td>
				<table class="table table-bordered">
					<thead class="thead-dark">
						<tr>
							<th>상품명</th>
							<th>구매가</th>
							<th>판매가</th>
							<th>세일가</th>
							<th>상품개요</th>
							<th>마일리지</th>
						</tr>
					</thead>
					<tbody>
						<%
							List<ProdVO> prodList = buyer.getProd();
							if(prodList!=null && prodList.size()>0){
								for(ProdVO prod : prodList){
									
						%>
								<tr>
									<td><%= prod.getProd_name() %></td>
									<td><%= prod.getProd_cost() %></td>
									<td><%= prod.getProd_price() %></td>
									<td><%= prod.getProd_sale() %></td>
									<td><%= prod.getProd_outline() %></td>
									<td><%= prod.getProd_mileage() %></td>
								</tr>
						<%
								}
							}else {
						%>
								<tr>
									<td colspan="6">거래물품 없어용</td>
								</tr>
						<%
							}
						
						%>
					</tbody>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>