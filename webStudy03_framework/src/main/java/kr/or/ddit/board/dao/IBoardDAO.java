package kr.or.ddit.board.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ReplyVO;

public interface IBoardDAO {


	public List<BoardVO> selectBoardList(PagingVO<BoardVO> pagingVO);
	
	public int selectBoardCount();
	
	public BoardVO selectBoard(int bo_no);
	
	public int incrementHit(int bo_no, SqlSession session);
	
	public int insertBoard(BoardVO board, SqlSession session);
	
	
}
