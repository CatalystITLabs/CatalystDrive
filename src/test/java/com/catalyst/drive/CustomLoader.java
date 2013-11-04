package com.catalyst.drive;

import java.io.File;
import org.springframework.test.context.support.GenericXmlContextLoader;

public class CustomLoader extends GenericXmlContextLoader {
	public static final String DEFAULT_TEST_CONTEXT_LOCATION = "src/main/webapp/WEB-INF/contexts/test-servlet.xml";

	@Override
	protected String[] generateDefaultLocations(Class<?> clazz) {
		return new String[] { "file:" + new File(DEFAULT_TEST_CONTEXT_LOCATION).getAbsolutePath() };
	}
}
