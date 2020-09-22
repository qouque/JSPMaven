


$.fn.searchForm = function() {
	
	this.on('click',function(){
		listArea.empty();
		// 입력한 동 이름 가져오기
		dongval = $('#dong').val();
		
		$.ajax({
			url : '<%= request.getContextPath() %>/searchZip.do',
			type : 'post',
			data : {'dong' : dongval},
			success : function(res) {
				
				code = "<table border='1' id='zipTable' class='display'>";
				code += "<thead><tr><td>우편번호</td><td>주소</td><td>번지</td></tr></thead>";
					
				$.each(res, function(i, v) {
					code += "<tr class = 'ziptr'><td>"+v.zipcode+"</td><td>"+v.sido+" "+v.gugun+" "+v.dong+"</td><td>"+v.bunji+"</td></tr>";
				})
				code += "</table>";
				$('#result1').html(code);
				
				$('#zipTable').dataTable({
					"pagingType": "full_numbers"
				});
				
			},
			error : function(xhr) {
				alert("상태 : " + xhr.status);
			},
			dataType : 'json'
		});
	});
	
	
	return this;
}