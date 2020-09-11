package kr.or.ddit.user.dao;

import java.util.List;

import kr.or.ddit.vo.UserVO;

public interface IUserDAO {
	
	public List<UserVO> selectAllUser();
	
	public List<String> selectAlluserId();
	
}
