package kr.or.ddit.user.service;

import java.util.List;

import kr.or.ddit.vo.UserVO;

public interface IUserService {
	
	public List<UserVO> readUser();
	
	public List<String> readUserId();
}
