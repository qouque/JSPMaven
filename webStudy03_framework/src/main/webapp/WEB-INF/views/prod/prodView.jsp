
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp" />
</head>
<body>
	<!-- 한건의 상품과 상품을 구매자 목록을 함께 UI 구성. -->
	<%--

	ProdVO prod = (ProdVO)request.getAttribute("prod");

--%>
	<table class="table table-bordered">
		<tr>
			<td colspan="2"><input id="goList" type="button" value="목록으로" />
				<input type="button" value="수정하기"
				onclick="location.href='<%=request.getContextPath()%>/prod/prodUpdate.do?what=${prod.prod_id}';" />
			</td>
		</tr>
		<tr>
			<th>상품코드</th>
			<td>${prod.prod_id}</td>
		</tr>
		<tr>
			<th>상품명</th>
			<td>${prod.prod_name}</td>
		</tr>
		<tr>
			<th>상품분류</th>
			<td>${prod.lprodVO.lprod_nm}</td>
		</tr>
		<tr>
			<th>거래처</th>
			<td>
				<table>
					<thead>
						<tr>
							<th>거래처명</th>
							<th>거래처소재지</th>
							<th>거래처담당자명</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><a
								href="<%= request.getContextPath() %>/buyer/buyerView.do?what=${prod.buyer.buyer_id}">${prod.buyer.buyer_name}</a></td>
							<td>${prod.buyer.buyer_add1}</td>
							<td>${prod.buyer.buyer_charger}</td>
						</tr>
					</tbody>
				</table>
			</td>
		</tr>
		<tr>
			<th>구매가</th>
			<td>${prod.prod_cost}</td>
		</tr>
		<tr>
			<th>판매가</th>
			<td>${prod.prod_price}</td>
		</tr>
		<tr>
			<th>세일가</th>
			<td>${prod.prod_sale}</td>
		</tr>
		<tr>
			<th>상품개요</th>
			<td>${prod.prod_outline}</td>
		</tr>
		<tr>
			<th>상세정보</th>
			<td>${prod.prod_detail}</td>
		</tr>
		<tr>
			<th>상품이미지경로</th>
			<td><img alt=""
				src="<%=request.getContextPath()%>/prodImages/${prod.prod_img}"></td>
		</tr>
		<tr>
			<th>총재고</th>
			<td>${prod.prod_totalstock}</td>
		</tr>
		<tr>
			<th>입고일</th>
			<td>${prod.prod_insdate}</td>
		</tr>
		<tr>
			<th>적정재고</th>
			<td>${prod.prod_properstock}</td>
		</tr>
		<tr>
			<th>크기</th>
			<td>${prod.prod_size}</td>
		</tr>
		<tr>
			<th>색상</th>
			<td>${prod.prod_color}</td>
		</tr>
		<tr>
			<th>배송방법</th>
			<td>${prod.prod_delivery}</td>
		</tr>
		<tr>
			<th>단위</th>
			<td>${prod.prod_unit}</td>
		</tr>
		<tr>
			<th>입고량</th>
			<td>${prod.prod_qtyin}</td>
		</tr>
		<tr>
			<th>판매량</th>
			<td>${prod.prod_qtysale}</td>
		</tr>
		<tr>
			<th>마일리지</th>
			<td>${prod.prod_mileage}</td>
		</tr>
		<tr>
			<th>구매자목록</th>
			<td>
				<table class="table table-bordered">
					<thead class="thead-dark">
						<tr>
							<th>회원아이디</th>
							<th>회원명</th>
							<th>휴대폰</th>
							<th>주소</th>
							<th>이메일</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty prod.memList }">
							<c:set var="memberList" value="${prod.memList }"/>
							<c:forEach var="member" items="${memberList }" >
								<tr>
									<td>${member.mem_id}</td>
									<td><a href="${pageContext.request.contextPath }/member/memberView.do?who=${member.mem_id}">${member.mem_name}</a></td>
									<td>${member.mem_hp}</td>
									<td>${member.mem_add1}</td>
									<td>${member.mem_mail}</td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${empty prod.memList }">
							<tr>
								<td colspan="5">구매자가 없어용</td>
							</tr>
						</c:if>
						
					</tbody>
				</table>
			</td>
		</tr>

	</table>
	<script type="text/javascript">

	$("#goList").on("click", function() {
		location.href = "<%=request.getContextPath()%>/prod/prodList.do";
		});
	</script>

</body>
</html>