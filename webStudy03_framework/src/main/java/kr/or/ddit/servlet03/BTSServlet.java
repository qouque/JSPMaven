package kr.or.ddit.servlet03;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/bts/list.do", loadOnStartup = 1)
public class BTSServlet extends HttpServlet {

	@Override
	public void init() throws ServletException {
		super.init();
		System.out.printf("%s초기화\n",getClass().getSimpleName());
		Map<String, String[]> btsMap = new LinkedHashMap<>();
		
		btsMap.put("B001", new String[] {"RM", "/bts/rm.jsp"});
		btsMap.put("B002", new String[] {"제이홉", "/bts/j-hope.jsp"});
		btsMap.put("B003", new String[] {"지민", "/bts/jimin.jsp"});
		btsMap.put("B004", new String[] {"진", "/bts/jin.jsp"});
		btsMap.put("B005", new String[] {"정국", "/bts/jungkuk.jsp"});
		btsMap.put("B006", new String[] {"슈가", "/bts/suga.jsp"});
		btsMap.put("B007", new String[] {"뷔", "/bts/v.jsp"});
		
		getServletContext().setAttribute("btsMap", btsMap);
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.getRequestDispatcher("/WEB-INF/bts/btsList.jsp").forward(req, resp);
		
	}
	
}





















