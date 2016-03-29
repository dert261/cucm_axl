package ru.obelisk.cucmaxl.web.databinding;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import ru.obelisk.cucmaxl.annotations.DatatableCriterias;
import ru.obelisk.cucmaxl.web.ui.datatables.DatatablesCriterias;

@Component
public class DatatableCriteriasHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public DatatablesCriterias resolveArgument(MethodParameter methodParameter,
			ModelAndViewContainer modelAndWirewContainer, NativeWebRequest nativeWebRequest,
			WebDataBinderFactory webDataBinderFactory) throws Exception {
		
		HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();
        return DatatablesCriterias.getFromRequest(request);
	}

	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return methodParameter.hasParameterAnnotation(DatatableCriterias.class);
	}

	
}

