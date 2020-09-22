 <%@page import="kr.or.ddit.vo.BuyerVO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp"/>
<script type="text/javascript">
<%
	String message = (String) request.getAttribute("message");
	if(StringUtils.isNotBlank(message)){
		%>
		alert("<%= message %>");
		<%
	}
%>
</script>
</head>
<body>
<jsp:useBean id="errors" class="java.util.LinkedHashMap" scope="request"></jsp:useBean>

<form method="post">
	<table class="table table-bordered">
			<tr>
				<th>상품명</th>
				<td><input type="text" name="prod_name">
				<span class="error"><%= errors.get("prod_name") %></span>
				</td>
			</tr>
			<tr>
				<th>상품분류</th>
				<td>
				<select name="prod_lgu">
					<option value>상품분류 선택</option>
					<%
						List<Map<String, Object>> lprodGuMap =(List<Map<String, Object>>) request.getAttribute("lprodGuMap");
						if(lprodGuMap != null && lprodGuMap.size() >0){
							for(Map<String, Object> map : lprodGuMap){
								for(Entry<String, Object> entry : map.entrySet()){
									%>
									<option value="<%=entry.getKey() %>"><%=entry.getValue() %></option>
									<%
								}
							}
						}else {
							%>
							<option value>비어있어용</option>
							<%
						}
					%>
				</select>
				<span class="error"><%= errors.get("prod_lgu") %></span></td>
			</tr>
			<tr>
				<th>거래처</th>
				<td>
					<select name="prod_buyer">
					<option value>거래처 선택</option>
					<%
						List<BuyerVO> buyerList = (List<BuyerVO>) request.getAttribute("buyerList");
						if(buyerList != null && buyerList.size() > 0){
							for(BuyerVO buyer : buyerList){
								%>
								<option value="<%= buyer.getBuyer_id() %>"><%= buyer.getBuyer_name() %></option>
								<%
							}
							
						}else {
							%>
							<option value>비어있어용</option>
							<%
						}
							
					
					%>
					</select>
				<span class="error"><%= errors.get("prod_buyer") %></span></td>
			</tr>
			<tr>
				<th>구매가</th>
				<td><input type="text" name="prod_cost">
				<span class="error"><%= errors.get("prod_cost") %></span></td>
			</tr>
			<tr>
				<th>판매가</th>
				<td><input type="text" name="prod_price">
				<span class="error"><%= errors.get("prod_price") %></span></td>
			</tr>
			<tr>
				<th>세일가</th>
				<td><input type="text" name="prod_sale">
				<span class="error"><%= errors.get("prod_sale") %></span></td>
			</tr>
			<tr>
				<th>상품개요</th>
				<td><input type="text" name="prod_outline">
				<span class="error"><%= errors.get("prod_outline") %></span></td>
			</tr>
			<tr>
				<th>상세정보</th>
				<td><input type="text" name="prod_detail">
				<span class="error"><%= errors.get("prod_detail") %></span></td>
			</tr>
			<tr>
				<th>상품이미지경로</th>
				<td><input type="text" name="prod_img">
				<span class="error"><%= errors.get("prod_img") %></span></td>
			</tr>
			<tr>
				<th>총재고</th>
				<td><input type="text" name="prod_totalstock">
				<span class="error"><%= errors.get("prod_totalstock") %></span></td>
			</tr>
			<tr>
				<th>적정재고</th>
				<td><input type="text" name="prod_properstock">
				<span class="error"><%= errors.get("prod_properstock") %></span></td>
			</tr>
			<tr>
				<th>크기</th>
				<td><input type="text" name="prod_size">
				<span class="error"><%= errors.get("prod_size") %></span></td>
			</tr>
			<tr>
				<th>색상</th>
				<td><input type="text" name="prod_color">
				<span class="error"><%= errors.get("prod_color") %></span></td>
			</tr>
			<tr>
				<th>배송방법</th>
				<td><input type="text" name="prod_delivery">
				<span class="error"><%= errors.get("prod_delivery") %></span></td>
			</tr>
			<tr>
				<th>단위</th>
				<td><input type="text" name="prod_unit">
				<span class="error"><%= errors.get("prod_unit") %></span></td>
			</tr>
			<tr>
				<th>입고량</th>
				<td><input type="text" name="prod_qtyin">
				<span class="error"><%= errors.get("prod_qtyin") %></span></td>
			</tr>
			<tr>
				<th>판매량</th>
				<td><input type="text" name="prod_qtysale">
				<span class="error"><%= errors.get("prod_qtysale") %></span></td>
			</tr>
			<tr>
				<th>마일리지</th>
				<td><input type="text" name="prod_mileage">
				<span class="error"><%= errors.get("prod_mileage") %></span></td>
			</tr>
			<tr>
				<td colspan="2">
				<input type="submit" value ="전송"/>
				<input type="reset" value="취소">
				</td>
			</tr>
	</table>

</form>
</body>
</html>