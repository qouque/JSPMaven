package kr.or.ddit.member.service;

import java.util.List;

import kr.or.ddit.commons.service.IAuthenticateService;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.CustomException;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ZipVO;

/**
 * 회원관리 Business Logic Layer
 *
 */
public interface IMemberService {
	/**
	 * 회원 가입
	 * @param member
	 * @return PKDUPLICATED, OK, FAILED
	 */
	public ServiceResult registMember(MemberVO member);
	
	
	public int retrieveMemberCount(PagingVO<MemberVO> pagingVO);
	
	/**
	 * 회원 목록 조회(관리자용)
	 * @return
	 */
	public List<MemberVO> retrieveMemberList(PagingVO<MemberVO> pagingVO);
	/**
	 * 회원 상세 조회(관리자용)
	 * @param mem_id
	 * @return 존재하지 않는 경우, {@link CustomException} 발생
	 */
	public MemberVO retrieveMember(String mem_id);
	/**
	 * 자기 정보 수정
	 * @param member
	 * @return 존재하지 않는 경우, {@link CustomException} 발생 , INVALIDPASSWORD, OK, FAILED
	 */
	public ServiceResult modifyMember(MemberVO member);
	/**
	 * 회원 탈퇴
	 * @param member
	 * @return 존재하지 않는 경우, {@link CustomException} 발생 , INVALIDPASSWORD, OK, FAILED
	 */
	public ServiceResult removeMember(MemberVO member);
	
	/**
	 * 아이디 중복체크
	 * @param mem_id
	 * @return 중복되는경우, PKDUPLICATED
	 */
	public ServiceResult checkId(String mem_id);

}













