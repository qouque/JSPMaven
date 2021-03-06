package kr.or.ddit.board.service;

import java.util.List;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ReplyVO;

public interface IReplyService {
	
	public List<ReplyVO> readReplyList(PagingVO<ReplyVO> pagingVO);
	
	public int readReplyCount(PagingVO<ReplyVO> pagingVO);
	
	public ServiceResult createReply(ReplyVO replyVO);
	
	public ServiceResult removeReply(int rep_no);
	
	public ServiceResult modifyReply(ReplyVO replyVO);
}
