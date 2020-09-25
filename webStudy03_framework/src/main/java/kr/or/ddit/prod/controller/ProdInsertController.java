package kr.or.ddit.prod.controller;

import java.io.File;
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
import kr.or.ddit.filter.wrapper.FileUploadRequestWrapper;
import kr.or.ddit.filter.wrapper.PartWrapper;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.HttpMethod;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.resolvers.ModelData;
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

@CommandHandler
public class ProdInsertController  {
	
	IProdService service = ProdServiceImpl.getInstance();
	IOthersService otherService = OthersServiceImpl.getInstance();
	
	@URIMapping("/prod/prodInsert.do")
	public String doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<Map<String, Object>> lprodGuMap = otherService.retrieveLprodGuList();
		
		List<BuyerVO> buyerList = otherService.retrieveBuyList(null);
		req.setAttribute("lprodGuMap", lprodGuMap);
		req.setAttribute("buyerList", buyerList);
		String goPage = "prod/prodForm";
		return goPage;
	}
	
	@URIMapping(value="/prod/prodInsert.do", method=HttpMethod.POST)
	public String doPost(
			@ModelData(name="prod")
			ProdVO prod,
			HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 검증 전에 prod_img 결정
		
		if(req instanceof FileUploadRequestWrapper) {
			PartWrapper imageFile = ((FileUploadRequestWrapper) req).getPartWrapper("prod_image");
			if(imageFile != null) {
				prod.setProd_image(imageFile);
			}
		}
		
		Map<String, StringBuffer> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		
		CommonValidator<ProdVO> validator = new CommonValidator<>();
		boolean valid = validator.validate(prod, errors, InsertGroup.class);
		
		String goPage = null;
		String message = null;
		System.out.println(valid);
		if(valid) {
			ServiceResult result = service.createProd(prod);
			switch (result) {
			case FAILED:
				goPage = "prod/prodForm";
				message = "서버 문제로 등록이 완료되지 않았습니당 잠시 후 다시 시도해주세요.";
				break;
				
			default:
//				PRG pattner : Post redirect Get
				goPage = "redirect:/prod/prodView.do?what="+prod.getProd_id();
				break;
			}
			
		}else {
			message = "등록실패";
			goPage = "prod/prodForm";
		}
		
		req.setAttribute("message", message);
		return goPage;
	}
	
}


















