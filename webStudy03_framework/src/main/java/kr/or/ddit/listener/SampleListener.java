package kr.or.ddit.listener;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.vo.MemberVO;

/**
 * context 가 시작되면, ServletContet를 미리 받아놓으려함.
 * 접속ㅈ자 리스트를 웰컴페이지에 출력.
 *
 */
public class SampleListener implements ServletContextListener {

	public static ServletContext currentContext;
	private static final Logger logger = LoggerFactory.getLogger(SampleListener.class) ;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		currentContext = sce.getServletContext();
		logger.info("{} 시작", currentContext.getContextPath());
		Map<String, MemberVO> userList = new LinkedHashMap<>();
		currentContext.setAttribute("userList", userList);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("{} 종료", currentContext.getContextPath());
		
	}

	
	
}













