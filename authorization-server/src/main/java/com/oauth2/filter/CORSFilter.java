package com.oauth2.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

public class CORSFilter extends GenericFilterBean {
	
	private static final Logger logger = LoggerFactory.getLogger(CORSFilter.class);


	@Override
	public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse response = (HttpServletResponse) res;
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
		response.addHeader("Access-Control-Max-Age", "3600");
		response.addHeader("Access-Control-Allow-Headers","Origin,X-Requested-With, Content-Type, Authorization, Accept, enctype, filename, file, Cache-Control, Pragma, Expires ");
		response.addHeader("Access-Control-Allow-Credentials", "true");

		if (req.getMethod().equalsIgnoreCase("options")) {
			return;
		}		
		chain.doFilter(request, response);
	}	
}

