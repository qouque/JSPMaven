package kr.or.ddit.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.annotation.CommandHandler;
import kr.or.ddit.annotation.HttpMethod;
import kr.or.ddit.annotation.URIMapping;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.SearchVO;

@CommandHandler
public class MemberRetrieveController {
	
	IMemberService service = MemberServiceImpl.getInstance();
	
	@URIMapping(value="/member/memberView.do", method=HttpMethod.GET)
	   public String asd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {   
	      req.setCharacterEncoding("UTF-8");
	      String who = req.getParameter("who");
	      if(StringUtils.isBlank("who")) {
	         resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"필수 파라미터 누락");
	         return null;
	      }
	      else {
	      
	      MemberVO member = service.retrieveMember(who);
	      req.setAttribute("member", member);
	      
	      String goPage = "member/memberView";
	      return goPage;
	      }
	      
	   }
	
	@URIMapping(value="/member/memberList.do", method=HttpMethod.GET)
	public String doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String searchWord = req.getParameter("searchWord");
		String pageParam = req.getParameter("page");
		String searchType = req.getParameter("searchType");
		PagingVO<MemberVO> pagingVO = new PagingVO<>(5,3);
		SearchVO searchVO = new SearchVO();
		
		if(StringUtils.isNotBlank(searchWord) && StringUtils.isNotBlank(searchType)) {
			searchVO.setSearchType(searchType);
			searchVO.setSearchWord(searchWord);
			pagingVO.setSearchVO(searchVO);
		}
		
		int totalRecord = service.retrieveMemberCount(pagingVO);
		
		pagingVO.setTotalRecord(totalRecord); // totalPage
		
		int currentPage = 1;
		if(StringUtils.isNotBlank(pageParam) && StringUtils.isNumeric(pageParam)) {
			currentPage = Integer.parseInt(pageParam);
		}
		pagingVO.setCurrentPage(currentPage); // startRow, endRow, startPage, endPage
		
		List<MemberVO> memList = service.retrieveMemberList(pagingVO);
		pagingVO.setData(memList);
		
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
			String goPage = "member/memberList";
			return goPage;
		}
		
		
	}
	
}






















