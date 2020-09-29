package kr.or.ddit.board.service;

import java.util.List;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ReplyVO;

public interface IBoardService {

	public List<BoardVO> retrieveBoardList(PagingVO<BoardVO> pagingVO);
	
	public int retrieveBoardCount();
	
	public BoardVO retrieveBoard(int bo_no);
	
	public ServiceResult createBoard(BoardVO board);
}
