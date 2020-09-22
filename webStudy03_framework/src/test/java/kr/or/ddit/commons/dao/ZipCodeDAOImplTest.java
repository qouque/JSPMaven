package kr.or.ddit.commons.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ZipVO;

public class ZipCodeDAOImplTest {
	
	IZipCodeDAO dao;
	
	@Before
	public void setUp() throws Exception {
		dao = ZipCodeDAOImpl.getInstance();
	}

	@Test
	public void testSelectByZipcode() {
		PagingVO<ZipVO> pagingVO = new PagingVO<>();
		List<ZipVO> list = dao.selectByZipcode(pagingVO);
		assertNotNull(list);
		assertNotEquals(0, list.size());
		System.out.println(list);
	}

}



















