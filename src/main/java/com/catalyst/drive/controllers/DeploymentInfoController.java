package com.catalyst.drive.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for deployment info pages
 * @author kpolen
 *
 */
@Controller
public class DeploymentInfoController {

	/**
	 * Standalone deployment instructions page
	 * @return
	 */
	@RequestMapping(value = "/standalone", method = RequestMethod.GET) 
	public String getStandaloneDeploymentInfo() {
		return "standalone";
	}
	
	/**
	 * External container deployment instructions page
	 * @return
	 */
	@RequestMapping(value = "/external", method = RequestMethod.GET) 
	public String getExternalDeploymentInfo() {
		return "external";
	}
}
