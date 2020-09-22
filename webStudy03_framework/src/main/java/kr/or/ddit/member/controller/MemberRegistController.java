package kr.or.ddit.member.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.ddit.annotation.CommandHandler;
import kr.or.ddit.annotation.HttpMethod;
import kr.or.ddit.annotation.URIMapping;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.validate.CommonValidator;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.vo.MemberVO;

@CommandHandler
public class MemberRegistController {
	private IMemberService service = MemberServiceImpl.getInstance();
	
	
	@URIMapping("/registMember.do")
	public String doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return "member/registForm";
		
	}
	
	@URIMapping(value = "/registMember.do", method=HttpMethod.POST)
	public String doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		1. 요청 파라미터 획득
		MemberVO member = new MemberVO();
		req.setAttribute("member", member);
		Map<String, String[]> parameterMap = req.getParameterMap();
		try {
			BeanUtils.populate(member, parameterMap);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(member);
//		2. 검증(DB 스키마 구조 참고)
		Map<String, StringBuffer> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		
		CommonValidator<MemberVO> validator = new CommonValidator<>();
		boolean valid = validator.validate(member, errors, InsertGroup.class);
		
		String goPage = null;
		String message = null;
		System.out.println(valid);
		if(valid) {
	//		3. 통과
	//		4. 통과한 경우, 로직을 이용한 등록
			ServiceResult result = service.registMember(member);
			System.out.println(result);
			switch (result) {
			case PKDUPLICATED:
				goPage = "member/registForm";
				message = "아이디중복, 확인후 다시 넣으세용.";
				break;
			case FAILED:
				goPage = "member/registForm";
				message = "서버 문제로 등록이 완료되지 않았습니당 잠시 후 다시 시도해주세요.";
				break;
	
			default:
				goPage = "redirect:/login/loginForm.jsp";
				break;
			}
			
			
		}else {
//		   불통
			goPage = "member/registForm";
		}
//		  등록 이후의 경우의 수에 대한 처리
		req.setAttribute("message", message);
		return goPage;
		
	}

	
}











