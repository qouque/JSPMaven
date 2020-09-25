package kr.or.ddit.prod.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.db.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.CustomException;
import kr.or.ddit.listener.SampleListener;
import kr.or.ddit.prod.dao.IProdDAO;
import kr.or.ddit.prod.dao.ProdDaoImpl;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;

public class ProdServiceImpl implements IProdService {
	
	private static IProdService self;
	SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getsqlSessionFactory();
	File folder;
	private ProdServiceImpl() {
		super();
		String folderURL = "/prodImages";
		String folderPath = SampleListener.currentContext.getRealPath(folderURL);
		folder = new File(folderPath);
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
		try(
			SqlSession session = sqlSessionFactory.openSession();	
		){
			int rowcnt = dao.insertProd(prod, session);
			
//			if(1==1) throw new RuntimeException("트랜잭션 관리여부를 확인하기 위한 강제 예외");
			prod.getProd_image().saveToRealPath(folder);
			
			
			ServiceResult result = null;
			if(rowcnt>0) {
				result = ServiceResult.OK;
				session.commit();
			}else {
				result = ServiceResult.FAILED;
				
			}
			return result;
		}catch (IOException e) {
			throw new RuntimeException(e);
		}
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
	
	@Override
	public ServiceResult modifyProd(ProdVO prod) {
		try(
			SqlSession session = sqlSessionFactory.openSession();
		){
			int rowCnt = dao.updateProd(prod, session);
			
//			if(1==1) throw new RuntimeException("트랜잭션 관리여부를 확인하기 위한 강제 예외");
			
			ServiceResult result = null;
			if(rowCnt > 0) {
				result = ServiceResult.OK;
				if(prod.getProd_image()!=null) {
					prod.getProd_image().saveToRealPath(folder);
				}
				session.commit();
			}else {
				result = ServiceResult.FAILED;
			}
			return result;
		}catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
	
	
	
	
	
	
	
}
