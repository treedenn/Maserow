package me.heitx.maserow.config;

import java.util.*;

public class LinkedProperties extends Properties {
	private final HashSet<Object> keys;

	public LinkedProperties() {
		this(null);
	}

	public LinkedProperties(Properties properties) {
		super(properties);
		keys = new LinkedHashSet<>();
	}

	@Override
	public Enumeration<Object> keys() {
		return Collections.enumeration(keys);
	}

	@Override
	public Object put(Object key, Object value) {
		keys.add(key);
		return super.put(key, value);
	}
}