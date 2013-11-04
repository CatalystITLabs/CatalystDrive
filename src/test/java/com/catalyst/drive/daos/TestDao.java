package com.catalyst.drive.daos;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.catalyst.drive.TestBase;
import com.catalyst.drive.daos.IDao;
import com.catalyst.drive.entities.User;

public class TestDao extends TestBase {
	@Autowired
	protected IDao<User> userDao;
	
	@Test
	public void testBasicFunctionality() {
		int allStart = userDao.getAll().size();
		User newUser0 = new User();
		newUser0.setUsername("test");
		// Only returned user guaranteed to be updated properly
		newUser0 = userDao.createOrUpdate(newUser0);
		int allEnd = userDao.getAll().size();
		userDao.delete(newUser0.getId());
		assertTrue(allEnd - allStart > 0);
	}
	
	@Test 
	public void testEmAndDelete() {
		User newUser0 = new User();
		newUser0.setUsername("test");
		// Only returned user guaranteed to be updated properly
		newUser0 = userDao.createOrUpdate(newUser0);
		newUser0 = userDao.getEm().find(User.class, newUser0.getId());
		userDao.delete(newUser0);
	}
}
