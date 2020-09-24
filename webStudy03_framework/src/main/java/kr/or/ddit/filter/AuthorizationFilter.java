package kr.or.ddit.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.vo.MemberVO;

/**
 * 보호 자원에 대한 접근 권한 소유여부 확인
 *
 */
//@WebFilter("/*")
public class AuthorizationFilter implements Filter {

	// 관리자가 아닌데 요구하는것 
	private static final Logger logger = LoggerFactory.getLogger(SampleFilter.class);
	
	private Map<String, String[]> secured;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("{} 객체 생성 및 초기화 완료.", getClass().getName());
		
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		secured = (Map<String, String[]>) req.getServletContext().getAttribute("secured");
		
//		System.out.println(secured);
		String uri = req.getRequestURI();
		int length = req.getContextPath().length();
		uri = uri.substring(length).split(";")[0];
		
		boolean pass = true;
		if(secured.containsKey(uri)) {
			// 보호 자원 요청
			HttpSession session = req.getSession();
			MemberVO authMember = (MemberVO) session.getAttribute("authMember");
			String userRole = authMember.getMem_role();
			String[] roles = secured.get(uri);
			
			if(Arrays.binarySearch(roles, userRole)<0) {
				pass = false;
			}
			
		}
		
		if(pass) {
			
			chain.doFilter(request, response);
		}else {
			// 로그인하지 않은 상태에서 보호 자원 요청
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "허용되지 않은 접근 (관리자가 아니에용)");;
		}
		
	}

	@Override
	public void destroy() {
		logger.info("{} 객체 소멸 .", getClass().getName());
		
	}
	
	
	
	
}
