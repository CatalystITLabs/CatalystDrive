package com.catalyst.drive.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * General error handling controller
 * @author kpolen
 *
 */
@Controller
public class ErrorController {

	/**
	 * Handles errors from web.xml mapped to /error
	 * @return
	 */
	@RequestMapping(value = "/error")
	public ModelAndView handleException() {  
		return new ModelAndView("error");
	}
}
