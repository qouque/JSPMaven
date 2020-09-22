package kr.or.ddit.commons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.db.ConnectionFactory;
import kr.or.ddit.db.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ZipVO;

public class ZipCodeDAOImpl implements IZipCodeDAO{

	private static ZipCodeDAOImpl self;
	
	private ZipCodeDAOImpl() {
		super();
	}
	
	public static ZipCodeDAOImpl getInstance() {
		if(self==null)
			self = new ZipCodeDAOImpl();
		return self;
	}
	
	private SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getsqlSessionFactory();
	
	@Override
	public int selectTotalCount(PagingVO pagingVO) {
		try(
			SqlSession session = sqlSessionFactory.openSession();	
		){
			IZipCodeDAO mapper = session.getMapper(IZipCodeDAO.class); // 매퍼프록시
			return mapper.selectTotalCount(pagingVO);
		}
	}
	
	@Override
	public List<ZipVO> selectByZipcode(PagingVO pagingVO) {
		try(
			SqlSession session = sqlSessionFactory.openSession();
		){
			return session.selectList("kr.or.ddit.commons.dao.IZipCodeDAO.selectByZipcode", pagingVO);
		}
		
	}

}

















