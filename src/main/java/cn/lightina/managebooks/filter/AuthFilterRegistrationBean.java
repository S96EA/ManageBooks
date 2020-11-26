package cn.lightina.managebooks.filter;

import cn.lightina.managebooks.service.UserService;
import com.sun.tools.javac.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
@Component
public class AuthFilterRegistrationBean extends FilterRegistrationBean<Filter> {
    @Autowired
    UserService userService;


    @PostConstruct
    public void init() {
        setUrlPatterns(List.of("/managebooks/*"));
    }

    @Override
    public Filter getFilter() {
        return new AuthFilter();
    }

    class AuthFilter implements Filter {

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {

        }

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            try {
                String requestUri = ((HttpServletRequest) servletRequest).getRequestURI();
                if (((HttpServletRequest) servletRequest).getSession().getAttribute("user") == null) {
                    ((HttpServletResponse) servletResponse).sendRedirect("/");
                } else {
                    filterChain.doFilter(servletRequest, servletResponse);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void destroy() {

        }
    }
}

