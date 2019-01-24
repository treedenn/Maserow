package me.heitx.maserow.io.config;

public enum ConfigKey {
	HOSTNAME("db-hostname", "127.0.0.1"),
	USERNAME("db-username", "root"),
	PASSWORD("db-password", "toor"),
	PORT("db-port", "3306"),
	AUTH("db-auth", "auth"),
	CHARACTERS("db-characters", "characters"),
	WORLD("db-world", "world"),
	SAVE_INFO("save-info", "false"),
	SAVE_PASS("save-pass", "false");

	private String key;
	private String defaultValue;

	ConfigKey(String key, String defaultValue) {
		this.key = key;
		this.defaultValue = defaultValue;
	}

	public String getKey() {
		return key;
	}

	String getDefaultValue() {
		return defaultValue;
	}
}