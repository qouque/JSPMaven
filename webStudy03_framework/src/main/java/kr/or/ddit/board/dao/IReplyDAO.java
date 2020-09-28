package kr.or.ddit.board.dao;

import java.util.List;

import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ReplyVO;

public interface IReplyDAO {
	
	public List<ReplyVO> selectReply(PagingVO<ReplyVO> pagingVO);
	
	public int selectReplyCount(PagingVO<ReplyVO> pagingVO);
}
