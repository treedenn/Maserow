package me.heitx.maserow.utils;

import me.heitx.maserow.query.Query;

import java.util.Map;

public class QueryUtil {
	public static String simpleInsert(String table, Map<String, Object> attributes, boolean newlineFormat) {
		Query query = new Query();
		return query.newlineFormat(newlineFormat)
				.insertInto(table)
				.values(attributes).buildSQL();
	}

	public static String simpleUpdate(String table, String where, Map<String, Object> attributes, boolean newlineFormat) {
		Query query = new Query();
		return query.newlineFormat(newlineFormat)
				.update(table)
				.where(where)
				.set(attributes)
				.buildSQL();
	}

	public static String simpleDelete(String table, String where, boolean newlineFormat) {
		return new Query().newlineFormat(newlineFormat).deleteFrom(table).where(where).buildSQL();
	}
}