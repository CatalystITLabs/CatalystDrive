package com.catalyst.drive.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for logging in.
 * @author kpolen
 *
 */
@Controller
public class AuthorizationController {
	
	/**
	 * Gets the login page
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/login",method={RequestMethod.GET})
	public ModelAndView getLogin(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("pages/login.jsp");
	}
	
	/**
	 * When a user is authenticated by spring security via ldap, they are sent here as denoted in default-target-url
	 * in the spring-security.xml.  This is where a redirect would be made based on the principal/user's authorities/roles.
	 * (The principal contains only the username, but the authorities/roles can of course be queried given the username).
	 * @param request
	 * @param response
	 * @param principal - this is the object 
	 * @return
	 */
	@RequestMapping(value="/logon",method={RequestMethod.GET})
	public ModelAndView postLogin(HttpServletRequest request, HttpServletResponse response, Principal principal){
		//Redirect to appropriate page, possibly dependent on role.	
		return new ModelAndView("redirect:document.html");
	}

	/**
	 * Logs the user out.
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout.html", method = {RequestMethod.GET})
	public String logout(HttpServletRequest request){
		request.getSession().invalidate();
		return "redirect:login.html";
	}
}

