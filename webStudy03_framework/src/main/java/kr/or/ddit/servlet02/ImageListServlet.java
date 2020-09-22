package kr.or.ddit.servlet02;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.Constans;
import kr.or.ddit.utils.CookieUtils;
import kr.or.ddit.utils.TemplateUtils;


public class ImageListServlet extends HttpServlet {
	
	private File folder ;
	private String pattern = "<option class = '%s'>%s</option>\n";
	private String imageUrl;
	private ServletContext application;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println(getServletContext().hashCode());
//		String contentsPath = config.getInitParameter("contentsPath");
		String contentsPath = getServletContext().getInitParameter("contentsPath");
		imageUrl = config.getInitParameter("imagePath");
		folder = new File(contentsPath);
		
		application = getServletContext();
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String contextPath = req.getContextPath();
		String[] listFiles = folder.list((dir, name) -> {
			boolean accept = false;

			String mime = application.getMimeType(name);
			
			accept = mime != null && (mime.startsWith("image/") || mime.startsWith("video/"));
			
			return accept;
		});
		
		CookieUtils utils = new CookieUtils(req);
		if(utils.exists("imageCookie")) {
			req.setAttribute(Constans.IMAGESTREAMINGCOOKIENAME, utils.getCookieValue(Constans.IMAGESTREAMINGCOOKIENAME));
		}
		req.setAttribute("listFiles", listFiles);
		req.setAttribute("includePage", "/WEB-INF/views/imageView.jsp");
		
		req.getRequestDispatcher("/WEB-INF/views/layout.jsp").forward(req, resp);
		
	}
	
	
	

}

















