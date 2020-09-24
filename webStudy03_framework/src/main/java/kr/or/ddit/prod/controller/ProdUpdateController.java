package kr.or.ddit.prod.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.Constans;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.HttpMethod;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.resolvers.ModelData;
import kr.or.ddit.mvc.annotation.resolvers.RequestParameter;
import kr.or.ddit.others.service.IOthersService;
import kr.or.ddit.others.service.OthersServiceImpl;
import kr.or.ddit.prod.service.IProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.ProdVO;

@CommandHandler
public class ProdUpdateController {
	
	IProdService service = ProdServiceImpl.getInstance();
	IOthersService otherService = OthersServiceImpl.getInstance();
	
	@URIMapping(value="/prod/prodUpdate.do")
	public String updateProd(
			@RequestParameter(name="what")
			String what,
			HttpServletRequest req, HttpServletResponse resp) {
		
		ProdVO prod = service.retrieveProd(what);
		req.setAttribute("prod", prod);
		String goPage = "prod/prodForm";
		
		List<BuyerVO> buyerList = otherService.retrieveBuyList(prod.getProd_lgu());
		req.setAttribute("buyerList", buyerList);
		req.setAttribute("command", "update");
		return goPage;
		
	}
	
	@URIMapping(value="/prod/prodUpdate.do", method=HttpMethod.POST)
	public String updateProdPost(
			@ModelData(name="prod")
			ProdVO prod,
			HttpServletRequest req, HttpServletResponse resp) {
		ServiceResult result = service.modifyProd(prod);
		String goPage = null;
		String message = null;
		switch (result) {
		case OK:
			goPage = Constans.REDIRECT+"/prod/prodView.do?what="+prod.getProd_id();
			break;
		case FAILED:
			goPage = "prod/prodForm";
			message = "수정하는 과정에서 서버 오류임!! ㅠㅡㅠ";
			break;
			
		default:
			
			break;
		}
		req.setAttribute("message", message);
		return goPage;
	}
	
}













