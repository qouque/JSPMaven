package kr.or.ddit.commons.dao;

import java.util.List;

import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ZipVO;

public interface IZipCodeDAO {

	public int selectTotalCount(PagingVO pagingVO);
	
	public List<ZipVO> selectByZipcode(PagingVO pagingVO);
}
