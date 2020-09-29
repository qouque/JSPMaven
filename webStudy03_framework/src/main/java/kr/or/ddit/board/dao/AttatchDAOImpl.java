package kr.or.ddit.board.dao;

import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.vo.BoardVO;

public class AttatchDAOImpl implements IAttatchDAO {
	
	@Override
	public int insertAttatchs(BoardVO board, SqlSession session) {
		return session.insert("kr.or.ddit.board.dao.IAttatchDAO.insertAttatchs", board);
	}
}
