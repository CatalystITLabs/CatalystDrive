package com.catalyst.drive.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import com.catalyst.drive.TestBase;
import com.catalyst.drive.controllers.SplashController;

public class TestSplashController extends TestBase {

	@Test
	public void testGetSplash() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		ModelAndView result = new SplashController().getSplash(request, response);
		assertEquals(result.getViewName(), "splash");
	}
}
