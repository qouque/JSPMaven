package kr.or.ddit.board.service;

import java.util.List;

import kr.or.ddit.board.dao.IReplyDAO;
import kr.or.ddit.board.dao.ReplyDAOImpl;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.utils.SecurityUtils;
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
	public ServiceResult modifyReply(ReplyVO replyVO) {
		ServiceResult result = ServiceResult.FAILED;
		int rowcnt = dao.updateReply(replyVO);
		if(rowcnt > 0)
			result = ServiceResult.OK;
		return result;
	}
	
	@Override
	public ServiceResult removeReply(int rep_no) {
		ServiceResult result = ServiceResult.FAILED;
		int rowcnt = dao.deleteReply(rep_no);
		if(rowcnt > 0)
			result = ServiceResult.OK;
		return result;
	}
	
	@Override
	public ServiceResult createReply(ReplyVO replyVO) {
		ServiceResult serviceResult = null;
		String sercurityPass =  SecurityUtils.encryptSha512(replyVO.getRep_pass());
		replyVO.setRep_pass(sercurityPass);
		
		int rowcnt = dao.insertReply(replyVO);
		if(rowcnt > 0) {
			serviceResult = ServiceResult.OK;
		}else {
			serviceResult = ServiceResult.FAILED;
		}
		return serviceResult;
	}
	
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
