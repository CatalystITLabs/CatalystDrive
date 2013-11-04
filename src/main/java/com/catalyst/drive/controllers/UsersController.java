package com.catalyst.drive.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.catalyst.drive.daos.StaticDao;
import com.catalyst.drive.entities.User;

/**
 * Controller used to test the "transactional" StaticDao (non-Spring) in
 * different environments.
 * @author kpolen
 *
 */
@Controller
public class UsersController {

	/**
	 * Get the users test page
	 * @return a model and view
	 */
	@RequestMapping(value = "/users", method = {RequestMethod.GET})
	public ModelAndView getUsers() {
		List<User> users = StaticDao.getAll(User.class);
		// Testing that static dao works in dev and deployment
		for (User user : users) {
			user.setPassword("PasswordNotRecorded" + new Date().getTime());
			StaticDao.createOrUpdate(user);
		}
		
		return new ModelAndView("users","usersModel",users);
	}
}
