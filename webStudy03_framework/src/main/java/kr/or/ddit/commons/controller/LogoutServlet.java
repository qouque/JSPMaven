package kr.or.ddit.commons.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.core.tools.picocli.CommandLine.Command;

import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.HttpMethod;
import kr.or.ddit.mvc.annotation.URIMapping;

@CommandHandler
public class LogoutServlet {
	
	@URIMapping(value="/login/logout.do", method=HttpMethod.POST)
	public String doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession(false);
		
		if(session == null || session.isNew()) {
			resp.sendError(400);
			return null;
		}else {
			session.removeAttribute("authID");
			session.invalidate();
			
			String goPage = "redirect:/";
			return goPage;
		}
		
	}// doGet 끝,.,,

}
