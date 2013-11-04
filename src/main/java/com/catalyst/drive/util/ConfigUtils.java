package com.catalyst.drive.util;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * Utility class for working with xml configuration files
 * @author kpolen
 *
 */
public class ConfigUtils {
	public static final String UTF_8 = "utf-8";
	private static final Logger logger = Logger.getLogger(ConfigUtils.class);
    
    /**
     * Gets a property value of the given property and attributes.  This is case insensitive.
     * @param target - the configuration File, String uri to the file, or InputStream
     * @param property - the property to be found
     * @param attributeValueMap - key-value pairs of the attributes for the property
     * @return the value of the property if found.  this will be blank if not found, null if there was an exception.
     */
    public static List<String> getConfigAttributeValue(Object target, String tag, String attribute) {
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        List<String> values = null;
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = null;
		    if (target instanceof String) {
		    	document = db.parse(target.toString());
		    } else if (target instanceof InputStream) {
		    	document = db.parse((InputStream) target);
		    } else if (target instanceof File) {
		    	document = db.parse((File) target);
		    } 
			if (document != null) {
				NodeList nodeList = document.getElementsByTagName(tag);
				values = new ArrayList<String>();
				for (int x = 0, size = nodeList != null ? nodeList.getLength() : 0; size != 0 && x < size; x++) {
					values.add(nodeList.item(x).getAttributes().getNamedItem(attribute).getNodeValue());
				}
			}
		} catch (Exception e ) {
			logger.warn("Nodelist not stable or configuration not found.");
		} 
        return values;
    }
}
