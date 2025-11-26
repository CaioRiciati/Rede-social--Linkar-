package com.linkar.project.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Optional;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieService {

    public static void setCookie(HttpServletResponse response, String key, String valor, int segundos) throws UnsupportedEncodingException {
        Cookie cookie = new Cookie(key, URLEncoder.encode(valor, "UTF-8"));
        cookie.setMaxAge(segundos);
        cookie.setPath("/"); // ESSENCIAL!!!
        response.addCookie(cookie);
    }

    public static String getCookie(HttpServletRequest request, String key) throws UnsupportedEncodingException {
        String valor = Optional.ofNullable(request.getCookies())
                .flatMap(cookies -> Arrays.stream(cookies)
                        .filter(cookie -> key.equals(cookie.getName()))
                        .findAny()
                ).map(Cookie::getValue)
                .orElse(null);

        if (valor != null) {
            return URLDecoder.decode(valor, "UTF-8");
        }

        return null;
    }
}
