package kr.or.ddit.member.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.HttpMethod;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.resolvers.ModelData;
import kr.or.ddit.validate.CommonValidator;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.vo.MemberVO;

@CommandHandler
public class MemberUpdateController  {
	
	private IMemberService service = MemberServiceImpl.getInstance();
	
	@URIMapping(value="/myDataUpdate.do")
	public String doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("MemberUpdateController - get통과용");
		MemberVO authMember = (MemberVO) req.getSession().getAttribute("authMember");
		MemberVO member = service.retrieveMember(authMember.getMem_id());
		
		req.setAttribute("member", member);
		String goPage = "member/updatePage";
		return goPage;
	}
	
	@URIMapping(value="/myDataUpdate.do", method=HttpMethod.POST)
	public String doPost(
			@ModelData(name="member")
			MemberVO member,
			HttpSession session,
			HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("MemberUpdateController - post통과용");
//		HttpSession session = req.getSession();
		
		System.out.println(member);
		
		Map<String, StringBuffer> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		
		CommonValidator<MemberVO> validator = new CommonValidator<>();
		boolean valid = validator.validate(member, errors, UpdateGroup.class);
		
		String goPage = null;
		String message = null;
		System.out.println(valid);
		if(valid) {
			ServiceResult result = service.modifyMember(member);
			System.out.println(result);
			switch (result) {
			case FAILED:
				System.out.println("실패 ㅠㅡㅠ");
				goPage = "member/updatePage";
				message = "실패했찌렁";
				break;
			case NOTEXIST:
				message = "존재하지 않는 아뒤!";
				goPage = "member/updatePage";
				break;
			
			default:
				goPage = "redirect:/mypage.do";
				break;
			}
		}else {
//			   불통
				goPage = "member/updatePage";
		}
		
		req.setAttribute("message", message);
		return goPage;
		//System.out.println(member);
	}
	
	
}











