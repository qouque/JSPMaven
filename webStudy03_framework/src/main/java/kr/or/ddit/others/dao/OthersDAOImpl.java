package kr.or.ddit.others.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.db.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.BuyerVO;

public class OthersDAOImpl implements IOthersDAO {

	private static IOthersDAO self;
	private OthersDAOImpl() {
		// TODO Auto-generated constructor stub
	}
	public static IOthersDAO getInstance() {
		if(self == null) {
			self = new OthersDAOImpl();
		}
		return self;
	}
	
	private SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getsqlSessionFactory();
	
	@Override
	public List<Map<String, Object>> selectLprodGuList() {
		try(
			SqlSession session = sqlSessionFactory.openSession();	
		){
			IOthersDAO mapper = session.getMapper(IOthersDAO.class);
			return mapper.selectLprodGuList();
		}
	}

	@Override
	public List<BuyerVO> selectBuyerList() {
		try(
			SqlSession session = sqlSessionFactory.openSession();	
		){
			IOthersDAO mapper = session.getMapper(IOthersDAO.class);
			return mapper.selectBuyerList();
		}
	}

}
