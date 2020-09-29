package kr.or.ddit.board.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.board.dao.AttatchDAOImpl;
import kr.or.ddit.board.dao.BoardDAOImple;
import kr.or.ddit.board.dao.IAttatchDAO;
import kr.or.ddit.board.dao.IBoardDAO;
import kr.or.ddit.db.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.utils.SecurityUtils;
import kr.or.ddit.vo.AttachVO;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingVO;

public class BoardServiceImpl implements IBoardService {

	IBoardDAO boardDAO = BoardDAOImple.getInstance();
	IAttatchDAO attatchDAO = new AttatchDAOImpl();
	
	private static IBoardService self;
	
	private BoardServiceImpl() {}
	
	private SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getsqlSessionFactory();
	
	public static IBoardService getInstance() {
		if(self == null) {
			self = new BoardServiceImpl();
		}
		return self;
	}
	
	File saveFolder = new File("d:/saveFiles");
	
	@Override
	public ServiceResult createBoard(BoardVO board) {
		ServiceResult result = null;
		boolean flag = false;
		try(
			SqlSession session = sqlSessionFactory.openSession();	
			){
			// 1. Board insert
			String securityPass = SecurityUtils.encryptSha512(board.getBo_pass());
			board.setBo_pass(securityPass);
			int boardInsertCnt = boardDAO.insertBoard(board, session);
			if(boardInsertCnt > 0) {
				// 첨부 파일 존재 여부 확인
				List<AttachVO> attList = board.getAttatchList(); 
				if(attList!=null && attList.size() > 0) {
					// 2. Attatch insert N번
					int attatchCnt = attatchDAO.insertAttatchs(board, session);
					// 3. binary 저장
					for(AttachVO tmp : attList) {
						tmp.saveFile(saveFolder);
					}
					if(attatchCnt > 0) {
						flag = true;
					}
				}
				flag = true;
				
			}else {
				flag = false;
				
			}
			
			if(flag) {
				result = ServiceResult.OK;
				session.commit();
			}else {
				result = ServiceResult.FAILED;
			}
			
		}catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return result;
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
