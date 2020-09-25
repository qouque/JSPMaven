<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
										$("<td>").html(
											$("<a>").attr("href", "${pageContext.request.contextPath}/prod/prodView.do?what="+prodVO.prod_id).text(prodVO.prod_name)
										),
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
				url : "${pageContext.request.contextPath}/prod/buyerOption.do",
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
<%--
	PagingVO<ProdVO> pagingVO = (PagingVO<ProdVO>)request.getAttribute("pagingVO"); 
--%>
<body>
	<form id="prodListForm" action="${pageContext.request.contextPath}/prod/prodList.do" method="get">
		<input type="hidden" name="page" >
		<select name="prod_lgu" class="submitSelect" id="selectLgu">
			<option value>상품분류 선택</option>
			<c:forEach  items="${lprodGuMap }" var="lprod">
				<option value="">${lprod.lprod_nm }</option>
				
			</c:forEach>
			
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
		<c:set var="dataList" value="${pagingVO.data }" />
		
		<c:choose>
			<c:when test="${not empty dataList }">
				<c:forEach items="${dataList }" var="prod">
					<tr>
						<c:url value="/prod/prodView.do" var="prodViewURL" >
							<c:param name="what" value="${prod.prod_id }"></c:param>
						</c:url>
						<td>${prod.prod_id}</td>
						<td><a href="${prodViewURL }">${prod.prod_name}</a></td>
						<td>${prod.lprodVO.lprod_nm}</td>
						<td>${prod.buyer.buyer_name}</td>
						<td>${prod.prod_cost}</td>
						<td>${prod.prod_price}</td>
						<td>${prod.prod_sale}</td>
						<td>${prod.prod_outline}</td>
						<td>${prod.prod_detail}</td>
						<td>${prod.prod_img}</td>
						<td>${prod.prod_totalstock}</td>
						<td>${prod.prod_insdate}</td>
						<td>${prod.prod_properstock}</td>
						<td>${prod.prod_size}</td>
						<td>${prod.prod_color}</td>
						<td>${prod.prod_delivery}</td>
						<td>${prod.prod_unit}</td>
						<td>${prod.prod_qtyin}</td>
						<td>${prod.prod_qtysale}</td>
						<td>${prod.prod_mileage}</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					 <td colspan='20'>비어있음</td>
				</tr>
			</c:otherwise>
		</c:choose>
			
		</tbody>
	</table>
	<div id="pagingArea">
	${pagingVO.pagingHTML_BS }
	</div>
</body>
</html>