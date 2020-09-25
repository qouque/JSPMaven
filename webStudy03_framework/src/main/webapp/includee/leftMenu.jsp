<%@page import="java.util.Map.Entry"%>
<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@page import="java.util.Map"%>
<%@page import="kr.or.ddit.vo.MenuVO"%>
<%@page import="kr.or.ddit.servlet05.Model2PageModuleServlet.IncludePage"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(function() {
		var menuForm = $("#menuForm");
		$(".menuA").on("click", function(event) {
			event.preventDefault();
			
			let service = $(this).data("service");
			let action = $(this).data("action");
			
			menuForm.find("[name = 'service']").val(service);
			menuForm.attr("action", action);
			
			menuForm.submit();
			return false;
			
		});
		
	});
</script>
<form id="menuForm">
	<input type="hidden" name="service" />
</form>
<ul>
	<%
		IncludePage[] types = IncludePage.values();
		for(IncludePage tmp : types){
			MenuVO menu = tmp.getMenuVO();
			%>
			<li><a href="#" class = "menuA" data-action="<%= request.getContextPath() %><%= menu.getMenuURI() %>" data-service = "<%= menu.getMenuId() %>"><%= menu.getMenuText() %></a></li>
			<%
		}
		Map<String, MemberVO> userList = (Map<String, MemberVO>)application.getAttribute("userList");
		%>
			<li>현재 접속중인 사람</li>
		<% 
		if(!userList.isEmpty()){
			for(Entry<String, MemberVO> user : userList.entrySet()){
				%>
				<li><%= user.getValue().getMem_name() %></li>
				<%
			}
			
		}else {
			%>
			<li>없어용</li>
			<%
		}
		
	%>
</ul>












