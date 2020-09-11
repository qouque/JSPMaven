package kr.or.ddit.user.service;

import java.util.List;

import kr.or.ddit.user.dao.IUserDAO;
import kr.or.ddit.user.dao.UserDAOImpl;
import kr.or.ddit.vo.UserVO;

public class UserServiceImpl implements IUserService {

	IUserDAO dao = new UserDAOImpl();
	
	@Override
	public List<UserVO> readUser() {
		List<UserVO> list = dao.selectAllUser();
		return list;
	}

	@Override
	public List<String> readUserId() {
		List<String> list = dao.selectAlluserId();
		return list;
	}

}
