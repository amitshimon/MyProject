package servlets;/*


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebFilter(urlPatterns ="/rest/*")
public class SessionFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chin)
			throws IOException, ServletException {
		
		HttpServletResponse res =(HttpServletResponse) response;
		HttpSession s = ((HttpServletRequest) request).getSession(false);
		if(s==null){
			res.sendRedirect("loginSPA.html");	
		}else{
			chin.doFilter(request, response);
		}


		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}



	
	
}
*/