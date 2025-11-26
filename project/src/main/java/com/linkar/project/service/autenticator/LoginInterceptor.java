package com.linkar.project.service.autenticator;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.linkar.project.service.CookieService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class LoginInterceptor implements HandlerInterceptor{
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

	    String valor = CookieService.getCookie(request, "usuarioId");
	    System.out.println("Cookie usuarioId = " + valor);

	    if (valor != null) {
	        return true;
	    }

	    response.sendRedirect("/login");
	    return false;
	}

}
