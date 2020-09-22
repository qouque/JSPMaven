package kr.or.ddit.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.annotation.CommandHandler;
import kr.or.ddit.annotation.HttpMethod;
import kr.or.ddit.annotation.URIMapping;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.CustomException;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;

@CommandHandler
public class IdCheckController {
	
	private IMemberService service = MemberServiceImpl.getInstance();
	
	@URIMapping(value="/idCheck.do", method=HttpMethod.POST)
	public String doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String inputId = req.getParameter("inputId");
		System.out.println(inputId);
		// 2. 필수파라미터 누락 확인
		if(StringUtils.isBlank(inputId)) {
			// 불통
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "필수 파라미터 누락");
			return null;
		}
		// 3. 통과
		// 통과시 아이디 중복여부 체크
		boolean validId = false;
		try {
			service.retrieveMember(inputId);
		}catch (CustomException e) {
			validId = true;
		}
		Map<String, Object> resultMap = new LinkedHashMap<>();
		resultMap.put("valid", validId);
		if(!validId) resultMap.put("useId", "추천아이디 : s10102");
		ObjectMapper mapper = new ObjectMapper();
		
		resp.setContentType("application/json;charset=UTF-8");
		// json으로 바꾸자
		try(
			PrintWriter out = resp.getWriter();
		){
			mapper.writeValue(out, resultMap);
		}
		
		return null;
		
		
		
	}
	
}






















