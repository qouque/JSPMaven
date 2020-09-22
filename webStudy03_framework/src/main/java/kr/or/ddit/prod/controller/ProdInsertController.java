package kr.or.ddit.prod.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.others.service.IOthersService;
import kr.or.ddit.others.service.OthersServiceImpl;
import kr.or.ddit.prod.service.IProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.validate.CommonValidator;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.ProdVO;

@WebServlet("/prod/prodInsert.do")
public class ProdInsertController extends HttpServlet {
	
	IProdService service = ProdServiceImpl.getInstance();
	IOthersService otherService = OthersServiceImpl.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<Map<String, Object>> lprodGuMap = otherService.retrieveLprodGuList();
		
		List<BuyerVO> buyerList = otherService.retrieveBuyList();
		req.setAttribute("lprodGuMap", lprodGuMap);
		req.setAttribute("buyerList", buyerList);
		req.getRequestDispatcher("/WEB-INF/views/prod/prodForm.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		ProdVO prod = new ProdVO();
		Map<String, String[]> paramMap = req.getParameterMap();
		try {
			BeanUtils.populate(prod, paramMap);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, StringBuffer> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		
		CommonValidator<ProdVO> validator = new CommonValidator<>();
		boolean valid = validator.validate(prod, errors, InsertGroup.class);
		
		String goPage = null;
		String message = null;
		boolean redirect = false;
		System.out.println(valid);
		if(valid) {
			ServiceResult result = service.createProd(prod);
			switch (result) {
			case FAILED:
				goPage = "/WEB-INF/views/prod/prodForm.jsp";
				message = "서버 문제로 등록이 완료되지 않았습니당 잠시 후 다시 시도해주세요.";
				break;
				
			default:
//				PRG pattner : Post redirect Get
				goPage = "/prod/prodView.do?what="+prod.getProd_id();
				redirect = true;
				break;
			}
			
		}else {
			message = "등록실패";
			goPage = "/WEB-INF/views/prod/prodForm.jsp";
		}
		
		req.setAttribute("message", message);
		if(redirect) {
			resp.sendRedirect(req.getContextPath()+goPage);
		}else {
			
			req.getRequestDispatcher(goPage).forward(req, resp);
		}
	}
	
}


















