package kr.or.ddit.mvc.annotation.resolvers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ClassUtils;

public class ModelDataArgumentResolver implements IHandlerMethodArgumentResolver {

	@Override
	public boolean isSupported(Parameter parameter) {
		ModelData modelData = parameter.getAnnotation(ModelData.class);
		Class<?> paramType = parameter.getType();
		
		return modelData!=null && !(ClassUtils.isPrimitiveOrWrapper(paramType) || String.class.equals(paramType));
		
	}

	@Override
	public Object argumentResolve(Parameter parameter, HttpServletRequest req, HttpServletResponse resp) {
		Class<?> paramType = parameter.getType();
		ModelData modelData = parameter.getAnnotation(ModelData.class);
		try {
			Object realParamerter = paramType.newInstance();
			req.setAttribute(modelData.name(), realParamerter);
			Map<String, String[]> parameterMap = req.getParameterMap();
			BeanUtils.populate(realParamerter, parameterMap);
			
			return realParamerter;
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

}
