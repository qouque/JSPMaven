<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>09/includeDesc.jsp</title>
</head>
<body>
<pre>
	
	: include 시점과 include 대상의 차이
	dispather.includem jsp:include
	<jsp:include page="/03/standard.jsp"></jsp:include>
	<%
// 		request.getRequestDispatcher("/webStudy01/webapp/03/standard.jsp").include(request, response);
	
	%>
	동적 include : Runtime, 실행 결과를 인클루드
	정적 include : jsp 에 해당하는 서블릿 소스가 파싱되는 시점, 소스 자체가 인클루드
		include 지시자(하나의 페이지를 대상으로함.) web.xml(jsp-config -> include, 어플리케이션 전체를 대상으로함)
<%-- 	<%@ include file="/webStudy01/webapp/03/standard.jsp" %> --%>
	<%-- test --%>
</pre>
</body>
</html>