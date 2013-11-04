package com.catalyst.drive;

import static org.junit.Assert.*;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = CustomLoader.class)
@TransactionConfiguration(transactionManager = "myTxManager", defaultRollback = false)
public class TestBase extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Override
	@Resource(name = "myDataSource")
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}
	
	@Test
	public void testBasicFunctionality() {
		assertTrue(true);
	}
}
