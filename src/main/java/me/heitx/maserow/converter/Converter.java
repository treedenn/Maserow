package me.heitx.maserow.converter;

import me.heitx.maserow.model.Column;
import me.heitx.maserow.model.Quest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Converter {
	public static <T> List<T> toObjects(Class<T> tClass, List<Map<String, Object>> attributesList) {
		List<T> oList = new ArrayList<>();

		for(Map<String, Object> map : attributesList) {
			oList.add(toObject(tClass, map));
		}

		return oList;
	}

	public static <T> T toObject(Class<T> tClass, Map<String, Object> attributes) {
		T o = null;

		try {
			o = tClass.newInstance();

			for(Field field : tClass.getDeclaredFields()) {
				Column column = field.getAnnotation(Column.class);
				if(column != null) {
					if(attributes.containsKey(column.value())) {
						field.setAccessible(true);
						field.set(o, attributes.get(column.value()));
					}
				}
			}
		} catch(InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return o;
	}

	public static Map<String, Object> toAttributes(Object o) {
		Map<String, Object> attributes = new HashMap<>();

		for(Field field : Quest.class.getDeclaredFields()) {
			Column column = field.getAnnotation(Column.class);
			if(column != null) {
				field.setAccessible(true);

				try {
					attributes.put(column.value(), field.get(o));
				} catch(IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}

		return attributes;
	}
}
