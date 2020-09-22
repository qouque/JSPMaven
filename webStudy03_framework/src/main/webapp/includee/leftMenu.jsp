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
	
	%>
</ul>
