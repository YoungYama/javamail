package com.yzz.javamail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertiesLoader {

	private static Logger logger = Logger.getLogger("PropertiesLoader");

	public static Properties loadProperties(String filePath) {
		Properties props = null;
		if (filePath == null || filePath.equals("")) {
			logger.warning("that path of properties file is null");
			return props;
		}

		URL rootPath = PropertiesLoader.class.getClassLoader().getResource("");
		String path = rootPath.getPath() + filePath;
		File file = new File(path);
		if (!file.exists()) {
			logger.warning("that properties file is not found");
			return props;
		}
		props = new Properties();
		InputStream is = null;

		try {
			is = new FileInputStream(file);
			props.load(is);
		} catch (FileNotFoundException e) {
			logger.warning("that properties file is not found");
			e.printStackTrace();
		} catch (IOException e) {
			logger.warning("that properties file was loading error");
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				logger.warning("that fileInputStream of properties was closeing error");
				e.printStackTrace();
			}
		}

		return props;
	}

}
