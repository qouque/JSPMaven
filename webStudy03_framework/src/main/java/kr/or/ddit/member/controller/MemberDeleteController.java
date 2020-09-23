package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.core.tools.picocli.CommandLine.Command;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.HttpMethod;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.resolvers.RequestParameter;
import kr.or.ddit.validate.CommonValidator;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.vo.MemberVO;

@CommandHandler
public class MemberDeleteController  {
	
	IMemberService service = MemberServiceImpl.getInstance();
	
	@URIMapping(value="/leaveApp.do", method=HttpMethod.POST)
	public String doPost(
			@RequestParameter(name="leavePass", required=true) String leavePass,
			 HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		System.out.println("MemberDeleteController -post통과용");
		HttpSession session = req.getSession(false);
		
//		String leaveId = req.getParameter("leaveId");
//		String leavePass = req.getParameter("leavePass"); //
//		System.out.println("id : " + leaveId + ", pass : " + leavePass);
		MemberVO member = (MemberVO) session.getAttribute("authMember");
		Map<String, StringBuffer> errors = new LinkedHashMap<>();
		member.setMem_pass(leavePass);
//				MemberVO.builder().mem_id(leaveId)
//											.mem_pass(leavePass)
//											.build();
		
		CommonValidator<MemberVO> validator = new CommonValidator<>();
		boolean valid = validator.validate(member, errors, UpdateGroup.class);
		
		String goPage = null;
		String message = null;
		System.out.println(valid);
		if(valid) {
			ServiceResult result = service.removeMember(member);
			System.out.println(result);
			switch (result) {
			case FAILED:
				System.out.println("실패 ㅠㅡㅠ");
				goPage = "/mypage.do";
				message = "실패했찌렁";
				break;
			case NOTEXIST:
				message = "존재하지 않는 아뒤!";
				goPage = "/mypage.do";
				break;
			
			default:
				if(session == null || session.isNew()) {
					resp.sendError(400);
				}else {
					session.removeAttribute("authID");
					session.invalidate();
				}
				goPage = "redirect:/";
//				goPage = "redirect:/login/logout.do";
				break;
			}
		}else {
//			   불통
				goPage = "member/updatePage";
		}
		
		req.setAttribute("message", message);
		return goPage;
		
	}
	
}
