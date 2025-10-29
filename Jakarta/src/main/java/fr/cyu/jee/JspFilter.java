package fr.cyu.jee;

import jakarta.servlet.*;

import java.io.IOException;

public class JspFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        req.getRequestDispatcher("/WEB-INF/errors/404.jsp").forward(req, response);
    }
}
