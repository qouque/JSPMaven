package kr.or.ddit.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Message;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.Constans;
import kr.or.ddit.board.service.IReplyService;
import kr.or.ddit.board.service.ReplyServiceImpl;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.HttpMethod;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.resolvers.ModelData;
import kr.or.ddit.mvc.annotation.resolvers.RequestParameter;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ReplyVO;
import kr.or.ddit.vo.SearchVO;
import oracle.net.aso.s;

@CommandHandler
public class ReplyController {
	
	IReplyService service = ReplyServiceImpl.getInstance();
	
	@URIMapping(value="/reply/replyUpdate.do", method=HttpMethod.POST)
	public String update(
			@ModelData(name="reply")
			ReplyVO reply,
			HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		ServiceResult serviceResult = service.modifyReply(reply);
		Map<String, ServiceResult> resultMap = Collections.singletonMap("result", serviceResult);
		resp.setContentType("aplication/json;charset=UTF-8");
		try(
			PrintWriter out = resp.getWriter();
			){
			ObjectMapper mapper = new ObjectMapper();	
			mapper.writeValue(out, resultMap);
		}
		
		
		return null;
		
	}
	
	@URIMapping(value="/reply/replyDelete.do", method=HttpMethod.POST)
	public String delete(
			@RequestParameter(name="rep_no")
			int rep_no,
			HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		ServiceResult serviceResult = service.removeReply(rep_no);
		Map<String, ServiceResult> resultMap = Collections.singletonMap("result", serviceResult);
		resp.setContentType("aplication/json;charset=UTF-8");
		try(
			PrintWriter out = resp.getWriter();
			){
			ObjectMapper mapper = new ObjectMapper();	
			mapper.writeValue(out, resultMap);
		}
		
		
		return null;
		
	}
	
	@URIMapping(value="/reply/replyInsert.do", method=HttpMethod.POST)
	public String replyInert(
			@ModelData(name="reply")
			ReplyVO replyVO,
			HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		ServiceResult serviceResult = service.createReply(replyVO);
		Map<String, ServiceResult> resultMap = Collections.singletonMap("result", serviceResult);
		resp.setContentType("aplication/json;charset=UTF-8");
		try(
			PrintWriter out = resp.getWriter();
			){
			ObjectMapper mapper = new ObjectMapper();	
			mapper.writeValue(out, resultMap);
		}
		
		return null;
	}
	
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
