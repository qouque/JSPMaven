package kr.or.ddit.annotation;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public interface IHandlerMapper {
	
	public URIMappingInfo findCommandHandler(HttpServletRequest req);
	
}
