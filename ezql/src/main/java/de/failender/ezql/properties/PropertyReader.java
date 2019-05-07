package de.failender.ezql.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader{

	private static Properties properties = new Properties();

	static {
		tryLoadProperties("/application.properties");
		String profile = properties.getProperty("profile");
		if(profile != null) {
			tryLoadProperties("/application-" + profile + ".properties");
		}
	}

	private static void tryLoadProperties(String name) {
		try {
			InputStream is = PropertyReader.class.getResourceAsStream(name);
			if(is == null) {
				return;
			}
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getProperty(String name) {
		return properties.getProperty(name);
	}
}
