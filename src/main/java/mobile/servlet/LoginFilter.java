package mobile.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mobile.entity.Users;
 

/**
 * Created by root on 2017/7/11.
 */
public class LoginFilter implements Filter {
    private String loginUrl;
    public void init(FilterConfig filterConfig) throws ServletException {
        this.loginUrl = filterConfig.getInitParameter("loginUrl");
    }
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Cookie[] c = request.getCookies();
        // 获取response
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Users userSession = (Users) request.getSession().getAttribute("sessionUser");
        if(userSession == null){
        	String url ="";
        	if(c == null){
				request.getRequestDispatcher("/error.html").include(request, response);  
				return;
        	}
            for (int i = 0; i < c.length; i++) {
				Cookie ck =c[i];
				if(ck.getName().equals("loginURL")){
					url = ck.getValue();
					break;
				}
			}
            response.sendRedirect(url);
        }else{
            filterChain.doFilter(request,response);
        }
    }
    public void destroy() {

    }
}
