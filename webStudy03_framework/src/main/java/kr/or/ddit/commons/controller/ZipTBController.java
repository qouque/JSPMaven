package kr.or.ddit.commons.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.commons.dao.IZipCodeDAO;
import kr.or.ddit.commons.dao.ZipCodeDAOImpl;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.SearchVO;
import kr.or.ddit.vo.ZipVO;

@WebServlet("/searchZip2.do")
public class ZipTBController extends HttpServlet {
	
	IZipCodeDAO dao = ZipCodeDAOImpl.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String pagePara = req.getParameter("page");
		String searchWord = req.getParameter("searchWord");
		SearchVO searchVO = new SearchVO();
		PagingVO<ZipVO> pagingVO = new PagingVO<>();
		if(StringUtils.isNotBlank(searchWord)) {
			searchVO.setSearchWord(searchWord);
			
			pagingVO.setSearchVO(searchVO);
		}
		int totalRecord = dao.selectTotalCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		int currentPage = 1;
		
		if(StringUtils.isNotBlank(pagePara) && StringUtils.isNumeric(pagePara)) {
			currentPage = Integer.parseInt(pagePara);
		}
		pagingVO.setCurrentPage(currentPage);
		
		List<ZipVO> zipList = dao.selectByZipcode(pagingVO);
		pagingVO.setData(zipList);
		
		resp.setContentType("aplication/json;charset=UTF-8");
		try(
			PrintWriter out = resp.getWriter();	
		){
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(out, pagingVO);
		}
		
		
		
	}
	
}













