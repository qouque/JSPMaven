package kr.or.ddit.mvc.annotation;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.mvc.annotation.resolvers.IHandlerMethodArgumentResolver;
import kr.or.ddit.mvc.annotation.resolvers.ModelDataArgumentResolver;
import kr.or.ddit.mvc.annotation.resolvers.RequestParameterArgumentResolver;
import kr.or.ddit.mvc.annotation.resolvers.ServletSpecArgumentResolver;
import oracle.net.aso.a;

public class HandlerInvoker implements IHandlerInvoker {
	
	private static final Logger logger = LoggerFactory.getLogger(HandlerInvoker.class);
	
	private List<IHandlerMethodArgumentResolver> argumentResolvers;
	{
		argumentResolvers = new ArrayList<>();
		argumentResolvers.add(new ServletSpecArgumentResolver());
		argumentResolvers.add(new RequestParameterArgumentResolver());
		argumentResolvers.add(new ModelDataArgumentResolver());
		
	}
	@Override
	public String invokeHandler(URIMappingInfo mappingInfo, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int index = 0;
		Object handler = mappingInfo.getCommandHandler();
		Method handlerMethod = mappingInfo.getHandlerMethod();
		Parameter[] parameters = handlerMethod.getParameters();
		try {
			if(parameters == null || parameters.length == 0) {
				return (String) handlerMethod.invoke(handler);
			}
			
			Object[] realParameters = new Object[parameters.length];
			for(Parameter parameter : parameters) {
				IHandlerMethodArgumentResolver matched = null;
				for(IHandlerMethodArgumentResolver temp : argumentResolvers) {
					if(temp.isSupported(parameter)) {
						matched = temp;
						break;
					}
				}
				if(matched!=null) {
					try {
						realParameters[index++] = matched.argumentResolve(parameter, req, resp);
					}catch (IllegalArgumentException e) {
						resp.sendError(400, e.getMessage());
						return null;
					}
				}else {
					String typeName = parameter.getType().getName();
					String handlerclassName = handler.getClass().getName();
					String handlerMethodName = handlerMethod.getName();
					logger.error("{}.{} 핸들러 호출 실패",handlerclassName, handlerMethodName);
					throw new RuntimeException(typeName + " 타입 파라미터 처리에 실패 ㅜㅜ, 지원하는 타입이 아녀요!!");
					
				}
				
			}
			return (String) handlerMethod.invoke(handler, realParameters);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new ServletException(e);
		}
		
	}

}















