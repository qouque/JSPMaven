package kr.or.ddit.servlet04;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/characterList.do", loadOnStartup = 1)
public class BloodServlet extends HttpServlet {
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		Map<String, String[]> bloodMap = new LinkedHashMap<>();
		
		bloodMap.put("C001", new String[] {"A형", "/WEB-INF/blood/a.jsp"});
		bloodMap.put("C002", new String[] {"B형", "/WEB-INF/blood/b.jsp"});
		bloodMap.put("C003", new String[] {"AB형", "/WEB-INF/blood/ab.jsp"});
		bloodMap.put("C004", new String[] {"O형", "/WEB-INF/blood/o.jsp"});
		
		getServletContext().setAttribute("bloodMap", bloodMap);
		
	}
		
}
