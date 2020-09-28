package kr.or.ddit.board.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.board.dao.BoardDAOImple;
import kr.or.ddit.board.dao.IBoardDAO;
import kr.or.ddit.db.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ReplyVO;

public class BoardServiceImpl implements IBoardService {

	IBoardDAO boardDAO = BoardDAOImple.getInstance();
	
	private static IBoardService self;
	
	private BoardServiceImpl() {}
	
	private SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getsqlSessionFactory();
	
	public static IBoardService getInstance() {
		if(self == null) {
			self = new BoardServiceImpl();
		}
		return self;
	}

	
	@Override
	public BoardVO retrieveBoard(int bo_no) {
		BoardVO board = boardDAO.selectBoard(bo_no);
		if(board != null) {
			try(
				SqlSession session = sqlSessionFactory.openSession();	
			){
				boardDAO.incrementHit(bo_no, session);
				session.commit();
			}
		}
		return board;
	}
	
	@Override
	public List<BoardVO> retrieveBoardList(PagingVO<BoardVO> pagingVO) {
		List<BoardVO> list = boardDAO.selectBoardList(pagingVO);
		return list;
	}

	@Override
	public int retrieveBoardCount() {
		int cnt = boardDAO.selectBoardCount();
		return cnt;
	}

}
