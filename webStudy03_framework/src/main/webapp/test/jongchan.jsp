<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">

	#text{
		font-size: 150px;
	}

</style>
<script type="text/javascript" src=""></script>
<script type="text/javascript" src ="../js/jquery-3.5.1.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#jcb1").on("click", function() {
			let text = "칭챙총찬 일어나";
			$("#text").text(text);
		});
		$("#jcb2").on("click", function() {
			let text = "칭챙총찬 앉아";
			$("#text").text(text);
		});
		$("#jcb3").on("click", function() {
			let text = "칭챙총찬 그만해";
			$("#text").text(text);
		});
		$("#jcb4").on("click", function() {
			let text = "칭챙총찬 쓰읍!! 기다려";
			$("#text").text(text);
		});
		$("#jcb5").on("click", function() {
			let text = "칭챙총찬 조용히해!!";
			$("#text").text(text);
		});
		$("#he1").on("click", function() {
			let text = "헌이형 안녕";
			$("#text").text(text);
		});
	})

</script>
</head>
<body>
종찬 : 
<input id="jcb1" type="button" value="일어나" />
<input id="jcb2" type="button" value="앉아" />
<input id="jcb3" type="button" value="그만해" />
<input id="jcb4" type="button" value="기다려" />
<input id="jcb5" type="button" value="조용히해" />
<br/><br/>
헌이 : 
<input id="he1" type="button" value="헌이형 안녕" />
<br/><br/>
<span id = "text"></span>
</body>
</html>