package kr.or.ddit.buyer.service;

import kr.or.ddit.buyer.dao.BuyerDAOImpl;
import kr.or.ddit.buyer.dao.IBuyerDAO;
import kr.or.ddit.exception.CustomException;
import kr.or.ddit.vo.BuyerVO;

public class BuyerServiceImpl implements IBuyerService {
	
	private static IBuyerService self;
	
	private BuyerServiceImpl() {
		super();
	}
	
	public static IBuyerService getInstance() {
		if(self == null) {
			self = new BuyerServiceImpl();
		}
		return self;
	}
	
	private IBuyerDAO dao = BuyerDAOImpl.getInstance();
	
	@Override
	public BuyerVO retrieveBuyer(String buyer_id) {
		BuyerVO buyer = dao.selectBuyer(buyer_id);
		if(buyer == null) {
			throw new CustomException(buyer_id + "는 존재하지 않은 거래처 입니당");
		}
		return buyer;
	}

}
