package com.catalyst.drive.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * SplashController handles the splash page.  
 * @author kpolen
 *
 */
@Controller
public class SplashController {
	
	/**
	 * Gets the splash page.
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/splash", method = {RequestMethod.GET})
	public ModelAndView getSplash(HttpServletRequest request, HttpServletResponse response){

		return new ModelAndView("splash"); // Maps to the tiles-configuration definition, name="splash"
	}
}
