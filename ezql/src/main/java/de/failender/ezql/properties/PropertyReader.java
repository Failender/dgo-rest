package de.failender.ezql.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class PropertyReader{


	public static void initialize(String[] args) {

		tryLoadProperties("/application.properties");
		properties.putAll(System.getenv());
		for (String arg : args) {
			if(arg.contains("=")) {
				String[] splits = arg.split("=");
				properties.put(splits[0], splits[1]);
			}
		}
		String profile = properties.getProperty("profile");
		if(profile != null) {
			tryLoadProperties("/application-" + profile + ".properties");
		}
	}

	private static Properties properties = new Properties();

	static {

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
