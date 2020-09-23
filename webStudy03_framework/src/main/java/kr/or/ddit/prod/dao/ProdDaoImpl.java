package kr.or.ddit.prod.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import kr.or.ddit.db.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;

public class ProdDaoImpl implements IProdDAO {

	private static IProdDAO self;
	
	private ProdDaoImpl() {
		super();
	}
	
	public static IProdDAO getInstance() {
		if(self == null) {
			self = new ProdDaoImpl();
		}
		
		return self;
	}
	
	private SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getsqlSessionFactory();
	
	@Override
	public ProdVO selectProd(String prod_id) {
		try(
			SqlSession sessoin = sqlSessionFactory.openSession();	
			){
			IProdDAO mapper = sessoin.getMapper(IProdDAO.class);
			return mapper.selectProd(prod_id);
		}
	}

	@Override
	public int insertProd(ProdVO prod) {
		try(
			SqlSession session = sqlSessionFactory.openSession();	
			){
			IProdDAO mapper = session.getMapper(IProdDAO.class);
			int rowcnt = mapper.insertProd(prod);
			session.commit();
			return rowcnt;
		}
	}

	@Override
	public List<ProdVO> selectProdList(PagingVO<ProdVO> pagingVO) {
		try(
			SqlSession session = sqlSessionFactory.openSession();	
				){
			IProdDAO mapper = session.getMapper(IProdDAO.class);
			return mapper.selectProdList(pagingVO);
		}
	}

	@Override
	public int selectProdCount(PagingVO<ProdVO> pagingVO) {
		try(
			SqlSession session = sqlSessionFactory.openSession();	
				){
			IProdDAO mapper = session.getMapper(IProdDAO.class);
			
			return mapper.selectProdCount(pagingVO);
		}
	}
}

















