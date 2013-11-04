package com.catalyst.drive.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles the document page
 * @author kpolen
 *
 */
@Controller
public class DocumentController {
	
	/**
	 * Gets the document page
	 * @return the document page
	 */
	@RequestMapping(value = "/document", method = RequestMethod.GET)
	public ModelAndView getDocument() {
		return new ModelAndView("document");
	}
}
