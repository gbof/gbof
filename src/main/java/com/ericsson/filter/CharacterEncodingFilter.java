package com.ericsson.filter;


import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CharacterEncodingFilter implements Filter {

        private String encoding = "UTF-8";

        public void init(FilterConfig config) throws ServletException {
                encoding = config.getInitParameter("encoding");
        }

        public void doFilter(ServletRequest request, ServletResponse response,
                        FilterChain chain) throws IOException, ServletException{
                HttpServletRequest req = (HttpServletRequest) request;
                HttpServletResponse res = (HttpServletResponse) response;
                req.setCharacterEncoding(encoding);
                res.setCharacterEncoding(encoding);
                chain.doFilter(req, res);
        }

        public void destroy() {
        }
}