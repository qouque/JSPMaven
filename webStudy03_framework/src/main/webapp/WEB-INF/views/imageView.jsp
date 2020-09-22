<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
<%
String jsonImageName = (String) request.getAttribute("imageCookie");
%>

	$(function(){
		
		var imageRendering = function(option, value) {
			let clzName = $(option).attr("class");
			let innerTag = null;
			if(clzName.startsWith('image')){
				innerTag = imgPtrn.replace("%S", value);
			}else if (clzName.startsWith('video')){
				innerTag = videoPtrn.replace("%S", value);
			}
			if(innerTag){
				resultArea.append(innerTag);
			}
		}
		
		var imgPtrn = "<img src ='<%= request.getContextPath() %>/image/image.do?image=%S'/>";
		var videoPtrn = "<video src = '<%= request.getContextPath() %>/image/image.do?image=%S' controls='auto'/>";
		
		var resultArea = $("#resultArea");
		var select = $("select").on("change", function() {
// 			let array = $(this).val();
// 			console.log(array);
			let options = $(this).find("option:selected");
			console.log(options);
			resultArea.empty();
			
			$(options).each(function(idx, option) {
				let value = $(this).text();
				setTimeout(function() {
					imageRendering(option, value);
				}, 600);
			});
			
				
		});
		
		<%
			if(StringUtils.isNotBlank(jsonImageName)){
				%>
				let json = '<%= jsonImageName %>';
				let objs = JSON.parse(json);
				console.log(objs);
				select.val(objs);
				select.trigger("change");
				<%
			}
		%>
		
	});
	

</script>
<%--
			Cookie[] cookies = request.getCookies();
			Cookie imgCookie = null;
			boolean chk = false;
			if(cookies != null){
				for(Cookie tmp : cookies){
					if("imageCookie".equals(tmp.getName())){
						imgCookie = tmp;
						chk = true;
						break;
					}
				}
				
			}
--%>

	<select multiple="multiple" size = "10">
		<option>파일선택</option>
		<%
			
			String[] listFiles = (String[]) request.getAttribute("listFiles");
			for(String file : listFiles){
				String mime = application.getMimeType(file);
				String clz = StringUtils.startsWith(mime, "image/")?"image":
								StringUtils.startsWith(mime, "video/")?"video":null;
				String selected = "";
// 				if(chk){
// 					if(imgCookie.getValue().equals(file)){
// 						selected = "selected";
// 					}
// 				}
				%>
				<option class="<%= clz %>" <%= selected %>><%= file %></option>
				<%
			}
		%>
	</select>
	<div id="resultArea">
	<%
// 		if(chk){
	%>
<%-- 		<img src ='<%= request.getContextPath() %>/image/image.do?image=<%= imgCookie.getValue() %>'/> --%>
	<%
// 		}
	%>
	</div>











