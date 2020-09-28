package kr.or.ddit.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.URIMapping;
import kr.or.ddit.mvc.annotation.resolvers.RequestParameter;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.SearchVO;

@CommandHandler
public class BoardReadController {
	
	IBoardService service = BoardServiceImpl.getInstance();
	
	@URIMapping("/board/boardView.do")
	public String boardViewGet(
			@RequestParameter(name="what")
			int bo_no,
			HttpServletRequest req, HttpServletResponse resp
			) {
		BoardVO board = service.retrieveBoard(bo_no);
		
		String goPage = "board/boardView";
		req.setAttribute("board", board);
		return goPage;
	}
	
	@URIMapping("/board/boardList.do")
	public String boardListGet(@RequestParameter(name="page", required=false, defaultValue="1") int currentPage,
					@RequestParameter(name="searchType", required=false) String searchType,
					@RequestParameter(name="searchWord", required=false) String searchWord,
					HttpServletRequest req,
					HttpServletResponse resp) throws ServletException, IOException {
		
		PagingVO<BoardVO> pagingVO = new PagingVO<>(10,5);
		SearchVO searchVO = new SearchVO();
		searchVO.setSearchType(searchType);
		searchVO.setSearchWord(searchWord);
		System.out.println(searchVO);
		pagingVO.setSearchVO(searchVO);
		int totalRecord = service.retrieveBoardCount();
		
		pagingVO.setTotalRecord(totalRecord); // totalPage
		pagingVO.setCurrentPage(currentPage); // startRow, endRow, startPage, endPage
		
		List<BoardVO> boardList = service.retrieveBoardList(pagingVO);
		pagingVO.setData(boardList);
		
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
			String goPage = "board/boardList";
			return goPage;
		}
	}
	
	
}














