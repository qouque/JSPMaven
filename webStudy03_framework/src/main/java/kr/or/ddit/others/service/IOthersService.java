package kr.or.ddit.others.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.BuyerVO;

public interface IOthersService {
	
	public List<Map<String, Object>> retrieveLprodGuList();
	
	public List<BuyerVO> retrieveBuyList(String buyer_lgu);
	
	
}
