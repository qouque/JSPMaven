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

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.validate.CommonValidator;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.vo.MemberVO;

@WebServlet("/leaveApp.do")
public class MemberDeleteController extends HttpServlet {
	
	IMemberService service = MemberServiceImpl.getInstance();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("MemberDeleteController -post통과용");
		HttpSession session = req.getSession(false);
		
//		String leaveId = req.getParameter("leaveId");
		String leavePass = req.getParameter("leavePass");
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
		boolean redirect = false;
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
				goPage = "/";
//				goPage = "/login/logout.do";
				redirect = true;
				break;
			}
		}else {
//			   불통
				goPage = "/WEB-INF/views/member/updatePage.jsp";
		}
		
		req.setAttribute("message", message);
		if(redirect) {
//			resp.sendRedirect(req.getContextPath()+ "/index.do");
//			resp.sendRedirect(req.getContextPath()+ goPage);
//			HttpSession session = req.getSession(false);
			
			if(session == null || session.isNew()) {
				resp.sendError(400);
			}else {
				session.removeAttribute("authID");
				session.invalidate();
				
				resp.sendRedirect(req.getContextPath() + "/");
			}
		}else {
			req.getRequestDispatcher(goPage).forward(req, resp);
		}
		
		
	}
	
}
