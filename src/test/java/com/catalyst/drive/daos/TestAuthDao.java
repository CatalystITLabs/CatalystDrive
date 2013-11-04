package com.catalyst.drive.daos;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.catalyst.drive.TestBase;
import com.catalyst.drive.daos.AuthDao;
import com.catalyst.drive.daos.StaticDao;
import com.catalyst.drive.entities.User;

public class TestAuthDao extends TestBase {
	
	public static final String TEST_USERNAME = "test" + new Date().getTime();
	public User user;
	@Autowired
	private AuthDao authDao;
	
	@Before
	public void setUp() {
		user = new User();
		user.setUsername(TEST_USERNAME);
		user = StaticDao.createOrUpdate(user);
	}

	@Test
	public void testGetUserByUsername() {
		assertEquals(user.getId(), authDao.getUserbyUserName(TEST_USERNAME).getId());
	}
	
	@After
	public void tearDown() {
		StaticDao.delete(user);
	}
}
