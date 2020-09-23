<%@page import="kr.or.ddit.vo.BuyerVO"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%@page import="kr.or.ddit.vo.PagingVO"%>
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
<script type="text/javascript">
	$(function() {
		
		let prodListTable = $("#prodListTable");
		let pagingArea = $("#pagingArea");
		
		pagingArea.on("click", "a", function() {
			
			let page = $(this).data("page");
			prodListForm.find("[name='page']").val(page);
			prodListForm.submit();
			prodListForm.find("[name='page']").val(1);
			
		});
		
		let prodListForm = $("#prodListForm").on("submit", function(event) {
			event.preventDefault();
			let url = this.action?this.action:location.href;
			let method = this.method?this.method:"get";
			let data = $(this).serialize(); // query string
			console.log(data);
			
			$.ajax({
				url : url,
				data : data,
				method : method,
				dataType : "json",
				success : function(resp) {
					prodListTable.find("tbody").empty();
					let prodList = resp.data;
					let trTags = [];
					if(prodList.length > 0){
						$(prodList).each(function(idx, prodVO) {
							trTags.push(
								$("<tr>").append(
										$("<td>").text(prodVO.prod_id),
										$("<td>").text(prodVO.prod_name),
										$("<td>").text(prodVO.lprodVO.lprod_nm),
										$("<td>").text(prodVO.buyer.buyer_name),
										$("<td>").text(prodVO.prod_cost),
										$("<td>").text(prodVO.prod_price),
										$("<td>").text(prodVO.prod_sale),
										$("<td>").text(prodVO.prod_outline),
										$("<td>").text(prodVO.prod_detail),
										$("<td>").text(prodVO.prod_img),
										$("<td>").text(prodVO.prod_totalstock),
										$("<td>").text(prodVO.prod_insdate),
										$("<td>").text(prodVO.prod_properstock),
										$("<td>").text(prodVO.prod_size),
										$("<td>").text(prodVO.prod_color),
										$("<td>").text(prodVO.prod_delivery),
										$("<td>").text(prodVO.prod_unit),
										$("<td>").text(prodVO.prod_qtyin),
										$("<td>").text(prodVO.prod_qtysale),
										$("<td>").text(prodVO.prod_mileage)	
								)
							);
						})
					}else{
						trTags.push($("<tr>").html($("<td colspan='20'>").text("검색된 상품 없음")));
					}
					prodListTable.find("tbody").html(trTags);
					pagingArea.html(resp.pagingHTML_BS);
				},
				error : function() {

				}
			});
			
			
			return false;
		});
		//.submit()
		
// 		$(".submitSelect").on("change", function() {
// 			prodListForm.submit();
// 		});
		let selectBuyer = $("#selectBuyer");
		$("#selectLgu").on("change", function() {
			let prod_lgu = $(this).val();
			
			$.ajax({
				url : "<%= request.getContextPath() %>/prod/buyerOption.do",
				data : {
					prod_lgu : prod_lgu
				},
				method : "get",
				dataType : "json",
				success : function(resp) {
					let options = [];
					options.push(
						$("<option value>").text('거래처 선택')	
					);
					if(resp!=null || resp.length > 0){
						$(resp).each(function(idx, buyerVO) {
							options.push(
								$("<option>").val(buyerVO.buyer_id).text(buyerVO.buyer_name)
							);
						});
					}else {
					options.push(
							$("<option value>").text('결과 없음')
						);
					}
					selectBuyer.html(options);
				},
				error : function() {

				}
			});
			
		});
		
	});


</script>
</head>
<%
	PagingVO<ProdVO> pagingVO = (PagingVO<ProdVO>)request.getAttribute("pagingVO"); 
%>
<body>
	<form id="prodListForm" action="<%= request.getContextPath() %>/prod/prodList.do" method="get">
		<input type="hidden" name="page" >
		<select name="prod_lgu" class="submitSelect" id="selectLgu">
			<option value>상품분류 선택</option>
			<%
				List<Map<String, Object>> lprodGuMap =(List<Map<String, Object>>) request.getAttribute("lprodGuMap");
				if(lprodGuMap != null && lprodGuMap.size() >0){
					for(Map<String, Object> lprod : lprodGuMap){
						%>
						<option value="<%=lprod.get("lprod_gu") %>"><%=lprod.get("lprod_nm") %></option>
						<%
					}
				}else {
					%>
					<option value>비어있어용</option>
					<%
				}
			%>
		</select>
		<select name="prod_buyer" class="submitSelect" id="selectBuyer">
			<option value>상품분류를 선택 해주세요</option>
			
		</select>
		<input type="text" name="prod_name"/>
		<input type="submit" value = "검색"/>
	</form>
	<table id="prodListTable" class="table table-sm">
		<thead class="thead-dark">
			<tr>
				<th>상품코드</th>
				<th>상품이름</th>
				<th>상품분류</th>
				<th>거래처</th>
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
				List<ProdVO> prodList = pagingVO.getData();
				for(ProdVO prod : prodList){
				%>
					<tr>
						<td><%=prod.getProd_id()%></td>
						<td><%=prod.getProd_name()%></td>
						<td><%=prod.getLprodVO().getLprod_nm()%></td>
						<td><%=prod.getBuyer().getBuyer_name()%></td>
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
	<div id="pagingArea">
	  <%= pagingVO.getPagingHTML_BS() %>
	</div>
</body>
</html>