package mx.edu.utez.secondapp.utils;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(urlPatterns = {"/*"})
public class RequestFilter implements Filter {

    List<String> whiteList = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Endpoints publicos
        whiteList.add("/api/auth/login");
        whiteList.add("/api/auth");
        whiteList.add("/api/auth/view-recovery");
        whiteList.add("/api/auth/recovery");
        whiteList.add("/api/user/view-register");
        whiteList.add("/api/user/register");
        whiteList.add("/api/send-mail");
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String action = request.getServletPath();
        if (whiteList.contains(action)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            HttpSession session = request.getSession();
            if (session.getAttribute("user") != null) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                response.sendRedirect(request.getContextPath() + "/api/auth/login");
            }
        }
    }

    @Override
    public void destroy() {

    }
}
