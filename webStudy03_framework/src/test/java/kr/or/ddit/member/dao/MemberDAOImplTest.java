package kr.or.ddit.member.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import kr.or.ddit.vo.MemberVO;

public class MemberDAOImplTest {

	private IMemberDAO dao;
	
	@Before
	public void setUp() throws Exception {
		dao = MemberDAOImpl.getInstance();
	}

//	@Test
	public void testInsertMember() {
		MemberVO member = MemberVO.builder().mem_id("a0090")
											.mem_pass("1234")
											.mem_name("하이")
											.mem_regno1("111111")
											.mem_regno2("1111111")
											.mem_bir("1996-08-11")
											.mem_zip("333-333")
											.mem_add1("대전")
											.mem_add2("104동 405호")
											.mem_hometel("042-111-1111")
											.mem_comtel("042-111-1111")
											.mem_mail("leehw6269@naver.com")
											.mem_job("e")
										    .build();
		int cnt = dao.insertMember(member);
		assertNotEquals(0, cnt);
		System.out.println(cnt);
	}

	@Test
	public void testSelectMemberList() {
		
		List<MemberVO> list = dao.selectMemberList(null);
		assertNotNull(list);
		assertNotEquals(0, list.size());
		System.out.println(list);
	}

	@Test(timeout=2000)
	public void testSelectMember() {
		MemberVO member = dao.selectMember("a001");
		assertNotNull(member);
		member = dao.selectMember("asdasdasdaf");
		assertNull(member);
	}


	@Test
	public void testUpdateMember() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteMember() {
		int cnt = dao.deleteMember("Y");
		assertNotEquals(0, cnt);
	}

}
