package me.heitx.maserow.utils;

import me.heitx.maserow.model.Column;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConverterUtil {
	private static final Logger LOGGER = LogManager.getLogger(ConverterUtil.class.getName());

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
			LOGGER.fatal(e.getMessage());
			e.printStackTrace();
		}



		return o;
	}

	public static Map<String, Object> toAttributes(Object o) {
		Map<String, Object> attributes = new LinkedHashMap<>();

		for(Field field : o.getClass().getDeclaredFields()) {
			Column column = field.getAnnotation(Column.class);
			if(column != null) {
				field.setAccessible(true);

				try {
					attributes.put(column.value(), field.get(o));
				} catch(IllegalAccessException e) {
					LOGGER.fatal(e.getMessage());
					e.printStackTrace();
				}
			}
		}

		return attributes;
	}

	public static int[] toPrimitive(List<Integer> list) {
		int[] arr = new int[list.size()];
		for(int i = 0; i < arr.length; i++) {
			arr[i] = list.get(i);
		}
		return arr;
	}
}
