package com.example.createuser.fillter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.example.createuser.entity.UserLogin;
import com.example.createuser.utility.JwtUtility;
import org.springframework.web.filter.GenericFilterBean;

public class JwtAuthenticationFilter extends GenericFilterBean {
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		try {
			String authHeader = request.getHeader("Authorization");
			System.out.println("Header Auth Data: " + authHeader);
			if (authHeader == null || !authHeader.startsWith("Bearer ")) {
				throw new ServletException("Missing or invalid Authorization header");
			}
			final String token = authHeader.substring(7);
			System.out.println("Token value is ::" + token);
			if (token != null && JwtUtility.verifyToken(token)) {
				UserLogin user = JwtUtility.getUserDtoFromToken(token);
				System.out.println("user value is ::" + user);
				request.setAttribute("user", user);
			} else {
				throw new ServletException("Token is invalid!!!");
			}
			filterChain.doFilter(req, res);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
		}
	}
}
