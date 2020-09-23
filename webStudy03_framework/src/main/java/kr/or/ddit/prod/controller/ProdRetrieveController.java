package kr.or.ddit.prod.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.resolvers.ModelData;
import kr.or.ddit.mvc.annotation.resolvers.RequestParameter;
import kr.or.ddit.others.service.IOthersService;
import kr.or.ddit.others.service.OthersServiceImpl;
import kr.or.ddit.prod.service.IProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;
import kr.or.ddit.vo.SearchVO;

@CommandHandler
public class ProdRetrieveController {
	
	IProdService service = ProdServiceImpl.getInstance();
	IOthersService otherService = OthersServiceImpl.getInstance();
	
	@URIMapping("/prod/buyerOption.do")
	public String buyList(
			@RequestParameter(name="prod_lgu", required=false)
			String prod_lgu,
			HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		
		List<BuyerVO> buyerList = otherService.retrieveBuyList(prod_lgu);
		resp.setContentType("aplication/json;charset=UTF-8");
		try(
				PrintWriter out = resp.getWriter();
				){
			ObjectMapper mapper = new ObjectMapper();
			
			mapper.writeValue(out, buyerList);
		}
		
		return null;
	}
	
	@URIMapping("/prod/prodList.do")
	public String list(@RequestParameter(name="page", required=false, defaultValue="1") int currentPage,
						@ModelData(name="searchData") ProdVO searchData,
							HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		PagingVO<ProdVO> pagingVO = new PagingVO<>(10,5);
//		SearchVO searchVO = new SearchVO(searchType, searchWord);
//		pagingVO.setSearchVO(searchVO);
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!prod : " +searchData);
		pagingVO.setSearchData(searchData);
		
		List<Map<String, Object>> lprodGuMap = otherService.retrieveLprodGuList();
		
		
		req.setAttribute("lprodGuMap", lprodGuMap);
		
		int totalRecord = service.retrieveProdCount(pagingVO);
		System.out.println(totalRecord);
		pagingVO.setTotalRecord(totalRecord); // totalPage
		pagingVO.setCurrentPage(currentPage); // startRow, endRow, startPage, endPage
		
		List<ProdVO> prodList = service.retrieveProdList(pagingVO);
		pagingVO.setData(prodList);
		
		String accept = req.getHeader("Accept");
		if(StringUtils.containsIgnoreCase(accept, "json")) {
			
			resp.setContentType("aplication/json;charset=UTF-8");
			try(
					PrintWriter out = resp.getWriter();
					){
				ObjectMapper mapper = new ObjectMapper();
				
				mapper.writeValue(out, pagingVO);
			}
			return null;
		}else {
			req.setAttribute("pagingVO", pagingVO);
			String goPage = "prod/prodList";
			return goPage;
		}
		
		
	}
	
	@URIMapping("/prod/prodView.do")
	public String doGet(
			@RequestParameter(name="what")
			String what,
			HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	      ProdVO prod = service.retrieveProd(what);
	      req.setAttribute("prod", prod);
	      String goPage = "prod/prodView";
	      return goPage;
	      
		
	}
}
















