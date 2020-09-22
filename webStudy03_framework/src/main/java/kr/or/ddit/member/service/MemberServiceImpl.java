package kr.or.ddit.member.service;

import java.util.List;

import kr.or.ddit.commons.service.AuthenticateServiceImpl;
import kr.or.ddit.commons.service.IAuthenticateService;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.CustomException;
import kr.or.ddit.member.dao.IMemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ZipVO;

public class MemberServiceImpl implements IMemberService {
	private IMemberDAO memberDAO = MemberDAOImpl.getInstance();
	private IAuthenticateService authService = new AuthenticateServiceImpl();
	
	private static MemberServiceImpl self;
	
	private MemberServiceImpl() {
		super();
	}

	public static MemberServiceImpl getInstance() {
		if(self==null) self = new MemberServiceImpl();
		return self;
	}
	
	@Override
	public ServiceResult registMember(MemberVO member) {
		ServiceResult result = null;
		if( memberDAO.selectMember(member.getMem_id()) == null) {
			int rowcnt = memberDAO.insertMember(member);
			if(rowcnt>0) {
				result = ServiceResult.OK;
			}else {
				result = ServiceResult.FAILED;
			}
		}else {
			result = ServiceResult.PKDUPLICATED;
		}
		return result;
	}
	
	@Override
	public ServiceResult checkId(String mem_id) {
		ServiceResult result = null;
		if(memberDAO.selectMember(mem_id) == null) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.PKDUPLICATED;
		}
		return result;
	}
	
	@Override
	public int retrieveMemberCount(PagingVO<MemberVO> pagingVO) {
		
		int cnt = memberDAO.selectMemberCount(pagingVO);
		
		return cnt;
	}
	
	@Override
	public List<MemberVO> retrieveMemberList(PagingVO<MemberVO> pagingVO) {
		List<MemberVO> list = memberDAO.selectMemberList(pagingVO);
		
		
		return list;
	}
	
	@Override
	public MemberVO retrieveMember(String mem_id) {
		MemberVO savedMember = memberDAO.selectMember(mem_id);
		if(savedMember == null)
			throw new CustomException(mem_id + "는 존재하지 않는 회원.");
		return savedMember;
	}

	
	@Override
	public ServiceResult modifyMember(MemberVO member) {
		Object res = authService.authenticate(member);
		ServiceResult serviceResult = null;
		if(res instanceof MemberVO) {
			int rowcnt = memberDAO.updateMember(member);
			if(rowcnt > 0) {
				serviceResult = ServiceResult.OK;
			}else {
				serviceResult = ServiceResult.FAILED;
			}
		}else {
			if(ServiceResult.NOTEXIST.equals(res)) {
				throw new CustomException(member.getMem_id() + "는 회원이 아님.");
			}else {
				serviceResult = (ServiceResult) res;
			}
		}
		return serviceResult;
//		ServiceResult result = null;
//		if( memberDAO.selectMember(member.getMem_id()) != null) {
//			int rowcnt = memberDAO.updateMember(member);
//			System.out.println(rowcnt);
//			if(rowcnt>0) {
//				result = ServiceResult.OK;
//			}else {
//				result = ServiceResult.FAILED;
//			}
//		}else {
//			result = ServiceResult.NOTEXIST;
//		}
//		return result;
	}

	@Override
	public ServiceResult removeMember(MemberVO member) {
		
		Object res = authService.authenticate(member);
		ServiceResult serviceResult = null;
		if(res instanceof MemberVO) {
			int rowcnt = memberDAO.deleteMember(member.getMem_id());
			if(rowcnt > 0) {
				serviceResult = ServiceResult.OK;
			}else {
				serviceResult = ServiceResult.FAILED;
			}
		}else {
			if(ServiceResult.NOTEXIST.equals(res)) {
				throw new CustomException(member.getMem_id() + "는 회원이 아님.");
			}else {
				serviceResult = (ServiceResult) res;
			}
		}
		return serviceResult;
	}

}
