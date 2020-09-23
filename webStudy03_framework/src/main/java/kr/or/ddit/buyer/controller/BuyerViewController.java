package kr.or.ddit.buyer.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.buyer.service.BuyerServiceImpl;
import kr.or.ddit.buyer.service.IBuyerService;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.resolvers.RequestParameter;
import kr.or.ddit.vo.BuyerVO;

@CommandHandler
public class BuyerViewController extends HttpServlet {
	
	IBuyerService service = BuyerServiceImpl.getInstance();
	
	@URIMapping(value="/buyer/buyerView.do")
	public String doGet(
			@RequestParameter(name="what")
			String what,
			HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		String what = req.getParameter("what");
		
		
		BuyerVO buyer = service.retrieveBuyer(what);
		String goPage = "buyer/buyerView";
		req.setAttribute("buyer", buyer);
		return goPage;
		
	}
}
