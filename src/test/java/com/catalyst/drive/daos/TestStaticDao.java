package com.catalyst.drive.daos;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Test;

import com.catalyst.drive.TestBase;
import com.catalyst.drive.daos.StaticDao;
import com.catalyst.drive.entities.User;

public class TestStaticDao extends TestBase {
	
	public static final String NAME = "test";
	
	@Test
	public void testGetEm() {
		EntityManager em = StaticDao.getEm();
		assertNotNull(em);
	}
	
	@Test
	public void testBasicFunctionality() {
		List<User> users = StaticDao.getAll(User.class);
		int allStart = users.size();
		User newUser0 = new User();
		newUser0.setUsername(NAME);
		// Only returned user guaranteed to be updated properly
		newUser0 = StaticDao.createOrUpdate(newUser0);
		users = StaticDao.getAll(User.class);
		int allEnd = users.size();
		StaticDao.delete(newUser0);
		assertTrue(allEnd - allStart > 0);
		assertNull(StaticDao.getEm().find(User.class, newUser0.getId()));
	}
	
	@Test
	public void testUpdateAndDeleteById() {
		User newUser0 = new User();
		newUser0.setUsername(NAME + 0);
		// Only returned user guaranteed to be updated properly
		newUser0 = StaticDao.createOrUpdate(newUser0);
		String name0 = newUser0.getUsername();
		newUser0.setUsername(NAME + 1);
		newUser0 = StaticDao.createOrUpdate(newUser0);
		String name1 = newUser0.getUsername();
		StaticDao.delete(User.class, newUser0.getId());
		assertTrue(!name0.equals(name1));
		assertNull(StaticDao.getEm().find(User.class, newUser0.getId()));
	}
	
	@Test
	public void testUpdateAndDeleteSetWithGetOrCreate() {
		int start = StaticDao.getAll(User.class).size();
		// Set up User set
		List<User> users = new ArrayList<User>();
		User newUser0 = new User();
		newUser0.setUsername(NAME + 0);
		// Test create branch
		User newUser1 = StaticDao.getOrCreate(User.class, 0);
		newUser1.setUsername(NAME + 1);
		users.add(newUser0);
		users.add(newUser1);
		users = StaticDao.createOrUpdate(users);
		assertTrue(start < StaticDao.getAll(User.class).size());
		newUser0 = users.get(0);
		// Test get branch
		newUser0 = StaticDao.getOrCreate(User.class, newUser0.getId());
		assertTrue(newUser0 != null);
		StaticDao.delete(users);
		assertTrue(start == StaticDao.getAll(User.class).size());
	}
}
