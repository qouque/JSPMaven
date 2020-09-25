<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>14/elDesc.jsp</title>
</head>
<body>
<h4>EL(Expression Language)</h4>
<pre>
	속성데이터를 출력할 목적으로 사용하는 스크립트 언어.
	1. 객체의 멤버에 대한 접근
		${member.mem_imgBase64 }
	2. 연산자('=' 연산자 미지원)
		+(산술연산) : ${3+4 }, ${3+"4" }, ${"3" + "4" }, \${"ㅁ" +3 }, \${"ㅁ" + "ㅠ" }
		!(not), ==(eq), !=(ne), >(gt), <(lt), >=(ge), <=(le)
		&&(and), ||(or)
		
		<c:set var="test" value='<%=Arrays.asList("t", "f") %>'></c:set>
		${empty test?"없음":"있음"}
		${alpha+3 }, ${3/alpha }
		
	3. 집합 객체 사용
	<%
		String[] array = new String[]{"a", "b"};
		pageContext.setAttribute("array", array);
		pageContext.setAttribute("list", Arrays.asList(array));
		Map<String, Object> map = new HashMap<>();
		map.put("key1", "value1");
		map.put("key-2", "value2");
		pageContext.setAttribute("map", map);
		Set<String> set = new HashSet<>();
		set.add("value1");
		set.add("value2");
		pageContext.setAttribute("set", set);
	%>
	${array[7] }, ${list[1] }
	${map.get("key1") }, ${map.key1 }, ${map["key1"] }
	${map.get("key-2") }, ${map.key-2 }, ${map["key-2"] }
	${set }
	
	4. EL 기본객체
		1) scope 객체 : pageScope, requestScope, sessionScope, applicationScope
			${pageScope.test }, ${pageScope["test"] }
		2) parameter 객체 : param(Map&lt;String, String&gt;), paramValues(Map&lt;String, String[]&gt;)
			${param.param1 }, ${paramValues.param1 }
		3) header 객체 : header, headerValues
			${header.accept }, ${header["accept"] }
			${header.user-agent }, ${header["user-agent"] }
		4) cookie 객체 : cookie(Map%lt;String, Cookie%gt;)
			${cookie.JSESSIONID.value }, ${cookie["JSESSIONID"]["value"] }
		5) context parameter 객체 : initParam
			${initParam.contentsPath }
		6) pageContext
			${pageContext.request.contextPath }
</pre>
<%
	pageContext.setAttribute("sameAttr", "페이지 속성");
	request.setAttribute("sameAttr", "요청 속성");
	session.setAttribute("sameAttr", "세션 속성");
// 	session.removeAttribute("sameAttr");
	application.setAttribute("sameAttr", "어플리케이션 속성");

	String sameAttr = "변수값";
%>
<pre>
표현식 : <%= sameAttr %>
EL : ${requestScope.sameAttr }
</pre>
</body>
</html>