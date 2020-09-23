package kr.or.ddit.others.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.others.dao.IOthersDAO;
import kr.or.ddit.others.dao.OthersDAOImpl;
import kr.or.ddit.vo.BuyerVO;

public class OthersServiceImpl implements IOthersService{

	private static IOthersService self;
	private OthersServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public static IOthersService getInstance() {
		if(self == null) {
			self = new OthersServiceImpl();
		}
		return self;
	}
	
	private IOthersDAO dao = OthersDAOImpl.getInstance();
	
	@Override
	public List<Map<String, Object>> retrieveLprodGuList() {
		List<Map<String, Object>> list = dao.selectLprodGuList();
		return list;
	}

	@Override
	public List<BuyerVO> retrieveBuyList(String buyer_lgu) {
		List<BuyerVO> list = dao.selectBuyerList(buyer_lgu);
		return list;
	}

}
