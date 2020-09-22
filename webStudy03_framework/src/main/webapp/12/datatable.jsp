<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>12/datatable.jsp</title>
<link rel="stylesheet" href="//cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css">
<jsp:include page="/includee/preScript.jsp"/>
<script type="text/javascript" src="//cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>

</head>
<body>
<table id = "zipTable" border="1">
	<thead>
		<tr>
			<th>우편번호</th>
			<th>주소</th>
		</tr>
	</thead>
	<tbody>
	
	</tbody>
</table>

<script type="text/javascript">
	$("#zipTable").DataTable({
		processing : true,
        serverSide : true,
		ajax : "<%= request.getContextPath()%>/zipSearch.do",
		columns : [
            { "data": "zipcode" },
            { "data": "address" }
        ]
	});
</script>
</body>
</html>


















