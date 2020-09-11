package kr.or.ddit.user.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.user.service.IUserService;
import kr.or.ddit.user.service.UserServiceImpl;
import kr.or.ddit.vo.UserVO;

@WebServlet("/homework/user.do")
public class UserControllerServlet extends HttpServlet {
	
	IUserService service = new UserServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<UserVO> userList = service.readUser();
		List<String> userIdList = service.readUserId();
//		System.out.println(userList);
		req.setAttribute("userList", userList);
		req.setAttribute("userIdList", userIdList);
		req.getRequestDispatcher("/WEB-INF/views/userMain.jsp").forward(req, resp);
		
		
	}
	
}
