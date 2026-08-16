package com.app.config;

import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class SessionAuthFilter implements Filter {

    private static final String SESSION_USER_ATTRIBUTE = "IDUSER";
    private static final Set<String> PUBLIC_PATHS = new HashSet<>(java.util.Arrays.asList(
            "/valida", "/", "/index", "/login", "/favicon.ico"
    ));
    private static final Set<String> STATIC_EXTENSIONS = new HashSet<>(java.util.Arrays.asList(
            ".css", ".js", ".png", ".jpg", ".jpeg", ".gif", ".svg", ".ico", ".woff", ".woff2", ".ttf", ".eot"
    ));

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String contextPath = request.getContextPath();
        String path = request.getRequestURI().substring(contextPath.length());

        if (isPublic(path) || hasSession(request)) {
            chain.doFilter(request, response);
            return;
        }

        if (isApiRequest(request)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":false,\"message\":\"Sesion no valida\"}");
        } else {
            response.sendRedirect(contextPath + "/");
        }
    }

    private boolean isPublic(String path) {
        if (PUBLIC_PATHS.contains(path)) {
            return true;
        }
        String lowerPath = path.toLowerCase();
        for (String extension : STATIC_EXTENSIONS) {
            if (lowerPath.endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute(SESSION_USER_ATTRIBUTE) != null;
    }

    private boolean isApiRequest(HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        return (accept != null && accept.contains("application/json"))
                || "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

}
