package me.heitx.maserow.configs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
	private static Config ourInstance = new Config();
	private final String fileName = "config.properties";

	private File file;
	private Properties properties;

	public static Config getInstance() {
		return ourInstance;
	}

	private Config() {
		file = new File("." + File.separator + "config" + File.separator + fileName);
		properties = new LinkedProperties();

		// default values
		properties.setProperty("db-hostname", "127.0.0.1");
		properties.setProperty("db-username", "root");
		properties.setProperty("db-password", "toor");
		properties.setProperty("db-port", "3306");
		properties.setProperty("db-auth", "auth");
		properties.setProperty("db-character", "character");
		properties.setProperty("db-world", "world");
		properties.setProperty("save-info", String.valueOf(false));
		properties.setProperty("save-pass", String.valueOf(false));
	}

	public String get(String key) {
		String s = null;
		if(properties.containsKey(key)) {
			s = properties.getProperty(key);
		}
		return s;
	}

	// TODO: Perhaps change key to enum..
	public void update(String key, String value) {
		if(properties.containsKey(key)) {
			properties.setProperty(key, value);
		}
	}

	public void save() {
		if(!file.getParentFile().exists()) {
			if(file.getParentFile().mkdirs()) {
				System.out.println("Created Config Folder!");
			}
		}

		try {
			properties.store(new FileOutputStream(file), "MaseroW Configuration");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void load() {
		if(file.exists()) {
			try {
				properties.load(new FileInputStream(file));
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
