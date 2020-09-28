package kr.or.ddit.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.db.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ReplyVO;

public class BoardDAOImple implements IBoardDAO {

	private static IBoardDAO self;
	
	private SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getsqlSessionFactory();
	
	private BoardDAOImple() {}
	
	public static IBoardDAO getInstance() {
		if(self == null) {
			self = new BoardDAOImple();
		}
		return self;
	}
	
	@Override
	public BoardVO selectBoard(int bo_no) {
		try(
			SqlSession session = sqlSessionFactory.openSession();	
		){
			IBoardDAO mapper = session.getMapper(IBoardDAO.class);
			return mapper.selectBoard(bo_no);
		}
	}
	
	@Override
	public int incrementHit(int bo_no, SqlSession session) {
		
		return session.update("kr.or.ddit.board.dao.IBoardDAO.incrementHit", bo_no);
	}
	
	@Override
	public List<BoardVO> selectBoardList(PagingVO<BoardVO> pagingVO) {
		try(
			SqlSession session = sqlSessionFactory.openSession();	
		){
			IBoardDAO mapper = session.getMapper(IBoardDAO.class);
			return mapper.selectBoardList(pagingVO);
		}
	}

	@Override
	public int selectBoardCount() {
		try(
			SqlSession session = sqlSessionFactory.openSession();	
		){
			IBoardDAO mapper = session.getMapper(IBoardDAO.class);
			return mapper.selectBoardCount();
		}
		
	}

}
