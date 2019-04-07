package me.heitx.maserow.common.io.config;

import me.heitx.maserow.Main;

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
		file = new File(Main.jarFile.getParent(), fileName);
		properties = new LinkedProperties();

		for(ConfigKey key : ConfigKey.values()) {
			properties.setProperty(key.getKey(), key.getDefaultValue());
		}
	}

	public String get(ConfigKey key) {
		return properties.getProperty(key.getKey());
	}

	public String getDefault(ConfigKey key) {
		return key.getDefaultValue();
	}

	// TODO: Perhaps change key to enum..
	public void update(ConfigKey key, String value) {
		if(properties.containsKey(key.getKey())) {
			properties.setProperty(key.getKey(), value);
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
