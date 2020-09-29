package kr.or.ddit.board.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.db.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ReplyVO;

public class ReplyDAOImpl implements IReplyDAO {

	private SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getsqlSessionFactory();
	
	private static IReplyDAO self;
	
	private ReplyDAOImpl() {}
	
	public static IReplyDAO getInstance() {
		if(self == null) {
			self = new ReplyDAOImpl();
		}
		return self;
	}
	
	@Override
	public int deleteReply(int rep_no) {
		try(
			SqlSession session = sqlSessionFactory.openSession(true);	
				){
			IReplyDAO mapper = session.getMapper(IReplyDAO.class);
			return mapper.deleteReply(rep_no);
		}
	}
	
	@Override
	public int updateReply(ReplyVO replyVO) {
		try(
			SqlSession session = sqlSessionFactory.openSession(true);	
				){
			IReplyDAO mapper = session.getMapper(IReplyDAO.class);
			return mapper.updateReply(replyVO);
		}
	}
	
	@Override
	public int insertReply(ReplyVO replyVO) {
		try(
			SqlSession session = sqlSessionFactory.openSession(true);	
			){
			IReplyDAO mapper = session.getMapper(IReplyDAO.class);
			return mapper.insertReply(replyVO);
		}
	}
	
	@Override
	public int selectReplyCount(PagingVO<ReplyVO> pagingVO) {
		try(
			SqlSession session = sqlSessionFactory.openSession();	
			){
			IReplyDAO mapper = session.getMapper(IReplyDAO.class);
			return mapper.selectReplyCount(pagingVO);
		}
		
	}
	
	@Override
	public List<ReplyVO> selectReply(PagingVO<ReplyVO> pagingVO) {
		try(
			SqlSession session = sqlSessionFactory.openSession();	
		){
			IReplyDAO mapper = session.getMapper(IReplyDAO.class);
			return mapper.selectReply(pagingVO);
		}
	}
}
