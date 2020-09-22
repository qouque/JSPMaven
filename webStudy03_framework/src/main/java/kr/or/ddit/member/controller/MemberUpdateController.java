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
import kr.or.ddit.validate.CommonValidator;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.vo.MemberVO;

@WebServlet("/myDataUpdate.do")
public class MemberUpdateController extends HttpServlet {
	
	private IMemberService service = MemberServiceImpl.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("MemberUpdateController - get통과용");
		MemberVO authMember = (MemberVO) req.getSession().getAttribute("authMember");
		MemberVO member = service.retrieveMember(authMember.getMem_id());
		
		req.setAttribute("member", member);
		
		req.getRequestDispatcher("/WEB-INF/views/member/updatePage.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("MemberUpdateController - post통과용");
//		HttpSession session = req.getSession();
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		MemberVO member = (MemberVO) session.getAttribute("authMember");
//				new MemberVO();
		Map<String, String[]> parameterMap = req.getParameterMap();
		try {
			BeanUtils.populate(member, parameterMap);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(member);
		
		Map<String, StringBuffer> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		
		CommonValidator<MemberVO> validator = new CommonValidator<>();
		boolean valid = validator.validate(member, errors, UpdateGroup.class);
		
		String goPage = null;
		String message = null;
		boolean redirect = false;
		System.out.println(valid);
		if(valid) {
			ServiceResult result = service.modifyMember(member);
			System.out.println(result);
			switch (result) {
			case FAILED:
				System.out.println("실패 ㅠㅡㅠ");
				goPage = "/WEB-INF/views/member/updatePage.jsp";
				message = "실패했찌렁";
				break;
			case NOTEXIST:
				message = "존재하지 않는 아뒤!";
				goPage = "/WEB-INF/views/member/updatePage.jsp";
				break;
			
			default:
				goPage = "/mypage.do";
				redirect = true;
				break;
			}
		}else {
//			   불통
				goPage = "/WEB-INF/views/member/updatePage.jsp";
		}
		
		req.setAttribute("message", message);
		if(redirect) {
			resp.sendRedirect(req.getContextPath()+goPage);
		}else {
			req.setAttribute("member", member);
			req.getRequestDispatcher(goPage).forward(req, resp);
		}
		//System.out.println(member);
	}
	
	
}











