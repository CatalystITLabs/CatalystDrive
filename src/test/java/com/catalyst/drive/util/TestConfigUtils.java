package com.catalyst.drive.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Test;

import com.catalyst.drive.TestBase;
import com.catalyst.drive.util.ConfigUtils;

public class TestConfigUtils extends TestBase {
	public static final String PERISTENCE_UNIT_TAG = "persistence-unit";
	public static final String NAME_ATTRIBUTE = "name";
	public static final String KNOWN_NAME = "default";
	public static final String TEST_FILE_NAME = System.getProperty("user.dir") + "/src/test/resources/test_config_utils.xml";

	@Test
	public void testGetConfigAttributeValueAsStream() {
		Object target = null;
		try {
			target = new FileInputStream(TEST_FILE_NAME);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		List<String> names = ConfigUtils.getConfigAttributeValue(target, PERISTENCE_UNIT_TAG, NAME_ATTRIBUTE);
		assertEquals(names.get(0), KNOWN_NAME);
	}

	@Test
	public void testGetConfigAttributeValueAsFile() {
		Object target = new File(TEST_FILE_NAME);
		List<String> names = ConfigUtils.getConfigAttributeValue(target, PERISTENCE_UNIT_TAG, NAME_ATTRIBUTE);
		assertEquals(names.get(0), KNOWN_NAME);
	}

	@Test
	public void testGetConfigAttributeValueAsString() {
		Object target = TEST_FILE_NAME;
		List<String> names = ConfigUtils.getConfigAttributeValue(target, PERISTENCE_UNIT_TAG, NAME_ATTRIBUTE);
		assertEquals(names.get(0), KNOWN_NAME);
	}

	@Test
	public void testGetConfigAttributeValueWhenNull() {
		Object target = new Object();
		List<String> names = ConfigUtils.getConfigAttributeValue(target, PERISTENCE_UNIT_TAG, NAME_ATTRIBUTE);
		assertEquals(names, null);
	}

	@Test
	public void testGetConfigAttributeValueForExceptionCaught() {
		Object target = new File("");
		List<String> names = ConfigUtils.getConfigAttributeValue(target, PERISTENCE_UNIT_TAG, NAME_ATTRIBUTE);
		assertEquals(names, null);
	}
}
