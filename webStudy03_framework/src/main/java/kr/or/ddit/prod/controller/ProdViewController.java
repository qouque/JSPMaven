package kr.or.ddit.prod.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.prod.service.IProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.ProdVO;

@WebServlet("/prod/prodView.do")
public class ProdViewController extends HttpServlet {
	
	IProdService service = ProdServiceImpl.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		 req.setCharacterEncoding("UTF-8");
	      String what = req.getParameter("what");
	      if(StringUtils.isBlank(what)) {
	         resp.sendError(404,"필수 파라미터 누락");
	         return;
	      }
	      else {
	      
	      ProdVO prod = service.retrieveProd(what);
	      req.setAttribute("prod", prod);
	      String goPage = "/WEB-INF/views/prod/prodView.jsp";
	      req.getRequestDispatcher(goPage).forward(req, resp);
	      }
		
	}
}
















