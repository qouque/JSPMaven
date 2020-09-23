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

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.CustomException;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.HttpMethod;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.resolvers.RequestParameter;

@CommandHandler
public class IdCheckController {
	
	private IMemberService service = MemberServiceImpl.getInstance();
	
	@URIMapping(value="/idCheck.do", method=HttpMethod.POST)
	public String doPost(
			@RequestParameter(name="inputId")
			String inputId,
			HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
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






















