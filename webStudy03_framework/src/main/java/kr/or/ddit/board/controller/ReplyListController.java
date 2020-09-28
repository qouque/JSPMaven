package kr.or.ddit.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.board.service.IReplyService;
import kr.or.ddit.board.service.ReplyServiceImpl;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.resolvers.RequestParameter;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ReplyVO;
import kr.or.ddit.vo.SearchVO;
import oracle.net.aso.s;

@CommandHandler
public class ReplyListController {
	
	IReplyService service = ReplyServiceImpl.getInstance();
	
	@URIMapping(value="/reply/replyList.do")
	public String replyList(
			@RequestParameter(name="what")
			int bo_no,
			@RequestParameter(name="page", defaultValue="1", required=false)
			int currentPage,
			HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		PagingVO<ReplyVO> pagingVO = new PagingVO<>(4,5);
		ReplyVO searchData = new ReplyVO();
		searchData.setBo_no(bo_no);
		pagingVO.setSearchData(searchData);
		
		int totalRecord = service.readReplyCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		pagingVO.setCurrentPage(currentPage);
		
		List<ReplyVO> replyList = service.readReplyList(pagingVO);
		
		pagingVO.setData(replyList);
		resp.setContentType("aplication/json;charset=UTF-8");
		try(
			PrintWriter out = resp.getWriter();
			){
			ObjectMapper mapper = new ObjectMapper();	
			mapper.writeValue(out, pagingVO);
		}
		
		return null;
	}

}
