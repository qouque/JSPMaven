package kr.or.ddit.filter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BlindFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(SampleFilter3.class);
	private Map<String, String> blindMap;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("{} 객체 생성 및 초기화 완료.", getClass().getName());
		blindMap = new LinkedHashMap<>();
		blindMap.put("192.168.41.29", "그냥 옆사람이여서 ㅎ2ㅎ2");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 특정 유저를 대상으로 한 블라인드 처리
		String clientIp = request.getRemoteAddr();
		boolean pass = true;
		String reason = null;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
		String goPage = "/13/blindMessage.jsp";
		String uri = req.getRequestURI();
		int length = req.getContextPath().length();
		
		uri = uri.substring(length);
		System.out.println(uri);
		
		if(goPage.equals(uri)) {
			if(blindMap.containsKey(clientIp)) {
				pass = false; 
				reason = blindMap.get(clientIp);
			}
		}
		
		if(pass) {
			chain.doFilter(request, response);
		}else {
			req.getSession().setAttribute("reason", reason);
//			request.getRequestDispatcher(goPage).forward(request, response);
			resp.sendRedirect(req.getContextPath() + goPage);
		}
	}

	@Override
	public void destroy() {
		logger.info("{} 객체 소멸 .", getClass().getName());
		
	}
	
}








