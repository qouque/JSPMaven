package kr.or.ddit.servlet04;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/serverExplorer.do")
public class ServerExplorerServlet extends HttpServlet {
	
	private ServletContext application;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		application = getServletContext();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String currentUrl = req.getParameter("url");
		String accept = req.getHeader("Accept");
		
		String ctxName = req.getParameter("ctxName");
		System.out.println(ctxName);
		ServletContext application = this.application;
		if(StringUtils.isNotBlank(ctxName)) {
			application = getServletContext().getContext(ctxName);
			//System.out.println(application.getContextPath());
		}
		if(application == null) {
			resp.sendError(404, "컨텍스트는 존재하지 않습니다.");
			return;
		}
		
		String currentPath = "/";
		if(StringUtils.isNotBlank(currentUrl)) {
			currentPath = currentUrl;
		}
		
		String realPath = application.getRealPath(currentPath);
		File folder = new File(realPath);
		int sc = 200;
		if(!folder.exists()) {
			sc = 404;
		}
		if(folder.isFile()) {
			sc = 400;
		}
		if(sc!=200) {
			resp.sendError(sc);
			return;
		}
		
		
		File[] listFiles = folder.listFiles();
		FileWrapper[] wrappers = new FileWrapper[listFiles.length];
		for(int i = 0; i < wrappers.length; i++) {
			wrappers[i] = new FileWrapper(listFiles[i], application);
		}
				
		
		if(accept.contains("json")) {
			resp.setContentType("application/json;charset=UTF-8");
			
			// marshalling, mime
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(wrappers);
			try(
				PrintWriter out = resp.getWriter();
			){
				// seriallize
				out.println(json);
			}
		}else {
			req.setAttribute("listFiles", wrappers);
			req.setAttribute("includePage", "/WEB-INF/views/serverExplorer2.jsp");
			
//			req.getRequestDispatcher("/WEB-INF/views/serverExplorer.jsp").forward(req, resp);
//			req.getRequestDispatcher("/WEB-INF/views/serverExplorer2.jsp").include(req, resp);
			req.getRequestDispatcher("/WEB-INF/views/layout.jsp").forward(req, resp);
		}
		
		
	} //doGet 끝..
	private interface FileCommandProcessor{
		public boolean process(File srcFile, File destFolder);
	}
	
	public static enum CommandType{
		COPY((srcFile, destFolder) -> {
			try {
				if(srcFile.isFile()) {
					FileUtils.copyFileToDirectory(srcFile, destFolder);
				}else {
					FileUtils.copyDirectoryToDirectory(srcFile, destFolder);
				}
				return true;
			} catch (IOException e) {
				return false;
			}
		}),
		DELETE((srcFile, destFolder) -> {
			return FileUtils.deleteQuietly(srcFile);
		}),
		MOVE((srcFile, destFolder) -> {
			boolean result = false;
			if(COPY.process(srcFile, destFolder)) {
				result = DELETE.process(srcFile, null);
			}
			return result;
		});
		
		private FileCommandProcessor processor;
		
		private CommandType(FileCommandProcessor processor) {
			this.processor = processor;
		}
		
		public boolean process(File srcFile, File destFolder) {
			return processor.process(srcFile, destFolder);
		}
		
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Map<String, Object> errors = new LinkedHashMap<>();
		
		boolean valid = validate(req, errors);
		if(valid) {
			//System.out.println("통과");
			// 파일에 대한 명령 처리
			CommandType commandType = (CommandType) req.getAttribute("commandType");
			File srcFile = (File) req.getAttribute("srcFile");
			File destFolder = (File) req.getAttribute("destFolder");
			
			boolean result = commandType.process(srcFile, destFolder);
			
			Map<String, Object> resultMap = Collections.singletonMap("success", result);
			
			resp.setContentType("application/json;charset=UTF-8");
			try(
				PrintWriter out = resp.getWriter();
			){
				ObjectMapper mapper = new ObjectMapper();
				mapper.writeValue(out, resultMap);
			}
			
		}else {
			Integer sc = (Integer) errors.get("statusCode");
			String msg = (String) errors.get("message");
			resp.sendError(sc, msg);
		}
		
//		System.out.println("command : " + command + ", srcFile : " + srcFile + ", destFolder : " + destFolder);
		
		
		
	}

	private boolean validate(HttpServletRequest req, Map<String, Object> errors) {
		boolean valid = true;
		String cmdParam = req.getParameter("command"); //403
		String srcParam = req.getParameter("srcFile"); //403
		String destParam = req.getParameter("destFolder"); 
		int sc = 200;
		StringBuffer msg = new StringBuffer();
		CommandType commandType = null;
		
		String ctxName = req.getParameter("ctxName");
		System.out.println("ctxName : " + ctxName);
		ServletContext application = this.application;
		if(StringUtils.isNotBlank(ctxName)) {
			application = getServletContext().getContext(ctxName);
			System.out.println(application.getContextPath());
		}
		if(application == null) {
			sc = 404;
			msg.append("컨텍스트는 존재하지 않습니다.");
			valid = false;
		}
		
		if(StringUtils.isBlank(cmdParam)) {
			valid = false;
			sc = 400;
			msg.append("명령이 전달되지 않았음.");
		}else {
			try {
				commandType = CommandType.valueOf(cmdParam);
			}catch (IllegalArgumentException e) {
				valid = false;
				sc = 400;
				msg.append("처리할수 없는 명령.");
			}
		}
		
		req.setAttribute("commandType", commandType);
		File srcFile = null;
		if(StringUtils.isBlank(srcParam)) {
			valid = false;
			sc = 400;
			msg.append("source 파일 파라미터가 없음");
		}else {
			String srcPath = application.getRealPath(srcParam);
			System.out.println(srcPath);
			srcFile = new File(srcPath);
			if(!srcFile.exists()) {
				valid = false;
				sc = 400;
				msg.append("source 파일이 존재하지 않음");
			}     
		}
		
		req.setAttribute("srcFile", srcFile);
		File destFolder = null;
		if(!CommandType.DELETE.equals(commandType) && StringUtils.isBlank(destParam)) {
			valid = false;
			sc = 400;
			msg.append("복사나 이동을 위한 대상 폴더 파라미터가 없음.");
		}else if(!CommandType.DELETE.equals(commandType)) {
			String destPath = application.getRealPath(destParam);
			destFolder = new File(destPath);
			if(!destFolder.exists() || destFolder.isFile()) {
				valid = false;
				sc = 400;
				msg.append("대상 폴더가 존재하지 않거나, 폴더가 아닌 파일이 대상으로 지정됨.");
			}
		}
		
		req.setAttribute("destFolder", destFolder);
		
		errors.put("statusCode", sc);
		errors.put("message", msg.toString());
		
		return valid;
	}
	
}























