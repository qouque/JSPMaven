<%@page import="kr.or.ddit.vo.BuyerVO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
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
		let registForm = $("#registForm");
			if("${message}"){
				alert("${message}");
			}
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
<body>
<jsp:useBean id="errors" class="java.util.LinkedHashMap" scope="request"></jsp:useBean>
<jsp:useBean id="prod" class="kr.or.ddit.vo.ProdVO" scope="request"></jsp:useBean>

<form id="registForm" method="post" enctype="multipart/form-data">
	<input type="hidden" name="prod_id" value="${prod.prod_id}"/>
	<table class="table table-bordered">
			<tr>
				<th>상품명</th>
				<td><input type="text" name="prod_name" value = "${prod.prod_name}">
				<span class="error">${error["prod_name"]}</span>
				</td>
			</tr>
				<c:set var="lprodGuMap" value="${lprodGuMap }" />
				<c:choose>
					<c:when test='${"update" ne command }'>
						<tr>
							<th>상품분류</th>
							<td>
							<select name="prod_lgu" class="submitSelect" id="selectLgu">
							<c:choose>
								<c:when test="${not empty lprodGuMap }">
									<option value>상품분류 선택</option>
									<c:forEach var="lprod" items="${lprodGuMap }" >
										<option value="${lprod.lprod_gu }">${lprod.lprod_nm }</option>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<option value>비어있어용</option>
								</c:otherwise>
							</c:choose>
							</select>
							</td>
						</tr>
						<tr>
							<th>거래처</th>
							<td>
							<select name="prod_buyer" class="submitSelect" id="selectBuyer">
								<option value>상품분류를 선택 해주세요</option>
							</select>
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<th>상품분류</th>
							<td>
							<input type="text" value="${prod.lprodVO.lprod_nm}" readonly disabled/>
							</td>
						</tr>
						<tr>
							<th>거래처</th>
							<td>
							<input type="text" value="${prod.buyer.buyer_name}" readonly disabled/>
							</td>
						</tr>	
					</c:otherwise>
				</c:choose>
			
			<tr>
				<th>구매가</th>
				<td><input type="text" name="prod_cost" value = "${prod.prod_cost}">
				<span class="error">${error["prod_cost"]}</span></td>
			</tr>
			<tr>
				<th>판매가</th>
				<td><input type="text" name="prod_price" value = "${prod.prod_price}">
				<span class="error">${error["prod_price"]}</span></td>
			</tr>
			<tr>
				<th>세일가</th>
				<td><input type="text" name="prod_sale" value = "${prod.prod_sale}">
				<span class="error">${error["prod_sale"]}</span></td>
			</tr>
			<tr>
				<th>상품개요</th>
				<td><input type="text" name="prod_outline" value = "${prod.prod_outline}">
				<span class="error">${error["prod_outline"]}</span></td>
			</tr>
			<tr>
				<th>상세정보</th>
				<td><input type="text" name="prod_detail" value = "${prod.prod_detail}">
				<span class="error">${error["prod_detail"]}</span></td>
			</tr>
			<tr>
				<th>상품이미지경로</th>
				<td><input type="file" name="prod_image" value = "${prod.prod_img}">
				<span class="error">${error["prod_img"]}</span></td>
			</tr>
			<tr>
				<th>총재고</th>
				<td><input type="text" name="prod_totalstock" value = "${prod.prod_totalstock}">
				<span class="error">${error["prod_totalstock"]}</span></td>
			</tr>
			<tr>
				<th>적정재고</th>
				<td><input type="text" name="prod_properstock" value = "${prod.prod_properstock}">
				<span class="error">${error["prod_properstock"]}</span></td>
			</tr>
			<tr>
				<th>크기</th>
				<td><input type="text" name="prod_size" value = "${prod.prod_size}">
				<span class="error">${error["prod_size"]}</span></td>
			</tr>
			<tr>
				<th>색상</th>
				<td><input type="text" name="prod_color" value = "${prod.prod_color}">
				<span class="error">${error["prod_color"]}</span></td>
			</tr>
			<tr>
				<th>배송방법</th>
				<td><input type="text" name="prod_delivery" value = "${prod.prod_delivery}">
				<span class="error">${error["prod_delivery"]}</span></td>
			</tr>
			<tr>
				<th>단위</th>
				<td><input type="text" name="prod_unit" value = "${prod.prod_unit}" >
				<span class="error">${error["prod_unit"]}</span></td>
			</tr>
			<tr>
				<th>입고량</th>
				<td><input type="text" name="prod_qtyin" value = "${prod.prod_qtyin}">
				<span class="error">${error["prod_qtyin"]}</span></td>
			</tr>
			<tr>
				<th>판매량</th>
				<td><input type="text" name="prod_qtysale" value = "${prod.prod_qtysale}">
				<span class="error">${error["prod_qtysale"]}</span></td>
			</tr>
			<tr>
				<th>마일리지</th>
				<td><input type="text" name="prod_mileage" value = "${prod.prod_mileage}">
				<span class="error">${error["prod_mileage"]}</span></td>
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