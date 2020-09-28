package kr.or.ddit.board.service;

import java.util.List;

import kr.or.ddit.board.dao.IReplyDAO;
import kr.or.ddit.board.dao.ReplyDAOImpl;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ReplyVO;

public class ReplyServiceImpl implements IReplyService {

	private static IReplyService self;
	
	private ReplyServiceImpl() {}
	
	public static IReplyService getInstance() {
		if(self == null) {
			self = new ReplyServiceImpl();
		}
		return self;
	}
	
	private IReplyDAO dao = ReplyDAOImpl.getInstance();
	
	@Override
	public List<ReplyVO> readReplyList(PagingVO<ReplyVO> pagingVO) {
		List<ReplyVO> list = dao.selectReply(pagingVO);
		return list;
	}

	@Override
	public int readReplyCount(PagingVO<ReplyVO> pagingVO) {
		int cnt = dao.selectReplyCount(pagingVO);
		return cnt;
	}
	
}
