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

import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.HttpMethod;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.resolvers.RequestParameter;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.SearchVO;

@CommandHandler
public class MemberRetrieveController {
	
	IMemberService service = MemberServiceImpl.getInstance();
	
	@URIMapping(value="/member/memberView.do", method=HttpMethod.GET)
	   public String asd(@RequestParameter(name="who",required=true) String who, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {   
	      
	      
	      MemberVO member = service.retrieveMember(who);
	      req.setAttribute("member", member);
	      
	      String goPage = "member/memberView";
	      return goPage;
	      
	   }
	
	@URIMapping(value="/member/memberList.do", method=HttpMethod.GET)
	public String doGet(@RequestParameter(name="page", required=false, defaultValue="1") int currentPage,
						@RequestParameter(name="searchType", required=false) String searchType,
						@RequestParameter(name="searchWord", required=false) String searchWord,
			HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		System.out.println(currentPage);
		PagingVO<MemberVO> pagingVO = new PagingVO<>(5,3);
		SearchVO searchVO = new SearchVO(searchType, searchWord);
		pagingVO.setSearchVO(searchVO);
	
		int totalRecord = service.retrieveMemberCount(pagingVO);
		
		pagingVO.setTotalRecord(totalRecord); // totalPage
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






















