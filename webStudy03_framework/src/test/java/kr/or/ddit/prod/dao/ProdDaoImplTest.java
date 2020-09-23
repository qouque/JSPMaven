package kr.or.ddit.prod.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import kr.or.ddit.vo.ProdVO;

public class ProdDaoImplTest {
	
	IProdDAO dao;
	ProdVO test;
	@Before
	public void setUp() throws Exception {
		dao = ProdDaoImpl.getInstance();
		test = ProdVO.builder().prod_name("신규상품")
							   .prod_lgu("P101")
							   .prod_buyer("P10101")
							   .prod_cost(300)
							   .prod_price(300)
							   .prod_sale(300)
							   .prod_outline("하")
							   .prod_detail("이")
							   .prod_img("사진")
							   .prod_totalstock(100)
							   .prod_properstock(100)
							   .prod_size("크기")
							   .prod_color("색깔")
							   .prod_delivery("배달")
							   .prod_unit("유닛")
							   .prod_qtyin(100)
							   .prod_qtysale(190)
							   .prod_mileage(100)
							   .build();
	}

	@Test
	public void test() {
		int rownt = dao.insertProd(test);
		assertNotEquals(0, rownt);
		
		
	}
//	@Test
	
	
}
