package kr.or.ddit.others.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.BuyerVO;

public interface IOthersDAO {
	
	public List<Map<String, Object>> selectLprodGuList();
	
	public List<BuyerVO> selectBuyerList();
	
	
}
