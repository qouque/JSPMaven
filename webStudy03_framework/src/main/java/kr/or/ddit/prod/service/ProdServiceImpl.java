package kr.or.ddit.prod.service;

import java.util.List;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.CustomException;
import kr.or.ddit.prod.dao.IProdDAO;
import kr.or.ddit.prod.dao.ProdDaoImpl;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;

public class ProdServiceImpl implements IProdService {
	
	private static IProdService self;
	
	private ProdServiceImpl() {
		super();
	}
	
	public static IProdService getInstance() {
		if(self == null) {
			self = new ProdServiceImpl();
		}
		return self;
	}
	
	private IProdDAO dao = ProdDaoImpl.getInstance();
	
	@Override
	public ProdVO retrieveProd(String prod_id) {
		ProdVO prod = dao.selectProd(prod_id);
		if(prod==null) {
			throw new CustomException( prod_id+"는 존재하지 않는 상품");
		}
		return prod;
	}

	@Override
	public ServiceResult createProd(ProdVO prod) {
		ServiceResult result = null;
		int rowcnt = dao.insertProd(prod);
		if(rowcnt>0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAILED;
		}
		return result;
	}

	@Override
	public List<ProdVO> retrieveProdList(PagingVO<ProdVO> pagingVO) {
		List<ProdVO> list = dao.selectProdList(pagingVO);
		return list;
	}

	@Override
	public int retrieveProdCount(PagingVO<ProdVO> pagingVO) {
		int cnt = dao.selectProdCount(pagingVO);
		return cnt;
	}

}
