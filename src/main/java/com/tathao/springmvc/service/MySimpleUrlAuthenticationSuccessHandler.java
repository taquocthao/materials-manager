package com.tathao.springmvc.service;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		System.out.println("onAuthenticationSuccess()");
		
		handle(request, response, authentication);
		clearAuthenticationAttributes(request);
	}

	private void handle(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
		
		String targetUrl = determineTargetUrl(authentication, request);
		System.out.println("target url: " + targetUrl);
		redirectStrategy.sendRedirect(request, response, targetUrl);
		
	}

	private String determineTargetUrl(Authentication authentication, HttpServletRequest request) {
		
		boolean isUser = false;
		boolean isCongTy = false;
		boolean isChiNhanh = false;
		
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		
		for(GrantedAuthority grantedAuthority : authorities) {
			if(grantedAuthority.getAuthority().equals("ROLE_USER")) {
				isUser = true;
				break;
			} else if(grantedAuthority.getAuthority().equals("ROLE_CHINHANH")) {
				isChiNhanh = true;
				break;
			} else if(grantedAuthority.getAuthority().equals("ROLE_CONGTY")) {
				isCongTy = true;
				break;
			}
		}
		
		String branch_id = request.getParameter("branch").trim();
		String contextPath = "";
		
		if(branch_id.equals("CN1")) {
			contextPath = "/branch/1/home";
		} else if(branch_id.equals("CN2")) {
			contextPath = "/branch/2/home";
		}
		
		if(isUser) {
			return contextPath;
		} else if(isCongTy) {
			return "/company/home";
		} else if(isChiNhanh) {
			return contextPath;
		} else {		
			throw new IllegalStateException();
		}

	}
	
	protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
	 
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

}
