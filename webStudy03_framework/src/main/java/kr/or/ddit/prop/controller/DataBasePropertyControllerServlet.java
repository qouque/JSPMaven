package kr.or.ddit.prop.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.prop.service.DataBasePropertyServiceImpl;
import kr.or.ddit.prop.service.IDataBasePropertyService;
import kr.or.ddit.vo.DataBase_PropertiesVO;
//
@WebServlet("/10/jdbcDesc.do")
public class DataBasePropertyControllerServlet extends HttpServlet {
	
	IDataBasePropertyService service = new DataBasePropertyServiceImpl();
	
	@Override
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String property_name = req.getParameter("property_name");
		String property_value = req.getParameter("property_value");
		
		String accept = req.getHeader("Accept");
		
		DataBase_PropertiesVO param= DataBase_PropertiesVO.getBuilder()
														  .property_Name(property_name)
													   	  .property_Value(property_value)
														  .build();
		List<DataBase_PropertiesVO> list =  service.readDataBaseProperties(param);
		List<String> propNames = service.readAllProperty_names();
		
		if(StringUtils.containsIgnoreCase(accept, "json")) {
			resp.setContentType("application/json;charset=UTF-8");
			// marshalling
			ObjectMapper mapper = new ObjectMapper();
			try(
				PrintWriter out = resp.getWriter();
			){
				mapper.writeValue(out, list);
			}
			
			
		}else {
			req.setAttribute("param", param);
			req.setAttribute("propList", list);
			req.setAttribute("propNames", propNames);
			
			String goPage = "/WEB-INF/10/jdbcDesc.jsp";
			req.getRequestDispatcher(goPage).forward(req, resp);
		}
		
		
		
	}
	
}
