package me.heitx.maserow.configs;

import java.util.*;

public class LinkedProperties extends Properties {
	private final HashSet<Object> keys;

	public LinkedProperties() {
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