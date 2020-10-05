<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp"/>
<script src="${pageContext.request.contextPath }/js/ckeditor/ckeditor.js"></script>
</head>
<body>
	<form action="${pageContext.request.contextPath }/board/boardInsert.do" method="post" enctype="multipart/form-data">
		<table class="table">
			<tr>
				<td>제목</td>
				<td>
				<input type="text" name="bo_title" />
				</td>
			</tr>
			<tr>
				<td>작성자</td>
				<td>
				<input type="text" name="bo_writer" />
				</td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td>
				<input type="password" name="bo_pass" />
				</td>
			</tr>
			<tr>
				<td>첨부파일</td>
				<td>
				<input type="file" name="bo_file" />
				</td>
			</tr>
			<tr>
				<td>아이피</td>
				<td>
				<input type="text" name="bo_ip" value="0:0:0:0:0:0:1" readonly="readonly" />
				</td>
			</tr>
			<tr>
				<td  colspan="2">내용</td>
			</tr>
			<tr>
				<td  colspan="2">
				<textarea cols="" rows="" name="bo_content" ></textarea>
				
				</td>
			</tr>
			<tr>
				<td colspan="2">
				<input type="submit" value="전송" >
				<input type="reset" value="취소" >
				<input type="button" value="목록으로" onclick="location.href = '${pageContext.request.contextPath }/board/boardList.do'">
				</td>
			</tr>
		</table>
	</form>
	<script>
		CKEDITOR.replace( 'bo_content', {
			filebrowserImageUploadUrl : "${pageContext.request.contextPath}/board/imageUpload.do",
			height: 500,
			resize_enabled : false,
			uiColor: '#ECC1FF'
		} );
	</script>
</body>
</html>