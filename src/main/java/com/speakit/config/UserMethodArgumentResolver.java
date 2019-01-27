package com.speakit.config;

import com.speakit.entity.Session;
import com.speakit.entity.User;
import com.speakit.exception.NotAuthenticatedException;
import com.speakit.repository.SessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Component
public class UserMethodArgumentResolver implements HandlerMethodArgumentResolver {
	private SessionRepository sessionRepository;
	private static final Logger log = LoggerFactory.getLogger(UserMethodArgumentResolver.class);

	public UserMethodArgumentResolver(SessionRepository sessionRepository) {
		this.sessionRepository = sessionRepository;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return User.class == parameter.getParameterType();
	}

	@Override
	public Object resolveArgument(
			MethodParameter parameter,
			ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory
	) {
		String sessionToken = webRequest.getHeader("sessionToken");

		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		String method = request.getMethod();

		log.info("User Method Resolver Request: {}, requested uri: {}, sessionToken: {}", method, request.getRequestURI(), sessionToken);

		if(sessionToken == null) {
			throw new NotAuthenticatedException();
		}

		Session session = sessionRepository.findByToken(sessionToken);
		if(session == null) {
			throw new NotAuthenticatedException();
		} else {
			boolean isSessionValid = validateSession(session);
			if(isSessionValid){
				return session.getUser();
			} else {
				sessionRepository.delete(session);
				throw new NotAuthenticatedException();
			}
		}
	}

	private boolean validateSession(Session session) {
		return session.getUpdateDate().plusMinutes(60).isAfter(LocalDateTime.now());
	}
}
