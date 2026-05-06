package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {
	protected Properties properties = null;
	protected FileInputStream file = null;
	public PropertiesReader() throws IOException {
		properties = new Properties();
		String path = System.getProperty("user.dir");
		String file_path = path+"\\src\\main\\resources\\object.properties";
		System.out.println(file_path);
		file = new FileInputStream(file_path);
		properties.load(file);
	}
	public String getProperty(String key) {
		return properties.getProperty(key);
	}
	public void closeFileInputStream() throws IOException {
		file.close();
	}
}
