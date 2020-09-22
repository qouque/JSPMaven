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

@WebServlet("/zipSearch.do")
public class ZipSearchDTController extends HttpServlet {

	IZipCodeDAO dao = ZipCodeDAOImpl.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String startPara = req.getParameter("start");
		String lengthPara = req.getParameter("length");
		String searchWord = req.getParameter("search[value]");
		
//		System.out.println("여기입니다 여기!!!!!!!! : " + searchWord);
		SearchVO searchVO = new SearchVO();
		PagingVO<ZipVO> pagingVO = new PagingVO<>();
		if(StringUtils.isNotBlank(searchWord)) {
			searchVO.setSearchWord(searchWord);
			pagingVO.setSearchVO(searchVO);
		}
		int totalRecord = dao.selectTotalCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		int currentPage = 1;
		if(StringUtils.isNotBlank(startPara) && StringUtils.isNotBlank(lengthPara) 
				&& StringUtils.isNumeric(startPara) && StringUtils.isNumeric(lengthPara)) {
			int start = Integer.parseInt(startPara);
			int length = Integer.parseInt(lengthPara);
			currentPage = (start + length)/length;
		}
		
		pagingVO.setCurrentPage(currentPage);
		Map<String, Object> sendData = new LinkedHashMap<>();
		List<ZipVO> zipList = dao.selectByZipcode(pagingVO);
		pagingVO.setData(zipList);
		sendData.put("data", zipList);
//		sendData.put("draw", draw);
		sendData.put("recordsTotal", totalRecord);
		sendData.put("recordsFiltered", totalRecord);
		
		resp.setContentType("aplication/json;charset=UTF-8");
		try(
			PrintWriter out = resp.getWriter();	
		){
			ObjectMapper mapper = new ObjectMapper();
//			mapper.writeValue(out, pagingVO);
			mapper.writeValue(out, sendData);
		}
		
		
		
		
	}
	
}
