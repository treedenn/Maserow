package me.heitx.maserow.utils;

import me.heitx.maserow.query.Query;

import java.util.*;

public class QueryUtil {
	public static String simpleInsert(String table, Map<String, Object> attributes, boolean newlineFormat) {
		String[] blocks = new String[] {
				"INSERT INTO " + table,
				columns(attributes.keySet(), true),
				values(attributes.values())
		};

		return String.join(" " + (newlineFormat ? System.lineSeparator() : ""), blocks);
	}

	public static String simpleUpdate(String table, String where, Map<String, Object> attributes, boolean newlineFormat) {
		String[] blocks = new String[] {
				"UPDATE " + table,
				set(attributes),
				where
		};

		return String.join(" " + (newlineFormat ? System.lineSeparator() : ""), blocks);
	}

	public static String simpleSelect(Collection<String> columns, String table, String where, boolean newlineFormat) {
		String[] blocks = new String[] {
				"SELECT " + (columns == null ? "*" : columns(columns, false)),
				"FROM " + table,
				where
		};

		return String.join(" " + (newlineFormat ? System.lineSeparator() : ""), blocks);
	}

	public static String simpleDelete(String table, String where, boolean newlineFormat) {
		String[] blocks = new String[] {
				"DELETE FROM " + table,
				where
		};

		return String.join(" " + (newlineFormat ? System.lineSeparator() : ""), blocks);
	}

	/**
	 * Separates the columns with a comma and surrounds the columns with parenthesises.
	 * @param columns any columns
	 * @param withParenthesis if true, surrounds the joined columns with parenthesis.
	 * @return a formatted string
	 */
	public static String columns(Collection<String> columns, boolean withParenthesis) {
		String joined = String.join(", ", columns);
		return withParenthesis ? "(" + joined + ")" : joined;
	}

	/**
	 * Formats the values into the sql VALUES syntax.
	 * @param values any values
	 * @return a formatted string with the VALUES syntax
	 */
	public static String values(Collection values) {
		Collection<String> objectsAsStrings = objectsToStrings(values);
		return "VALUES (" + String.join(", ", objectsAsStrings) + ")";
	}

	public static String set(Map<String, Object> map) {
		return set(map.keySet(), map.values());
	}

	public static String set(Collection<String> columns, Collection values) {
		if(columns.size() != values.size()) return "";

		Iterator<String> cIterator = columns.iterator();
		Iterator vIterator = values.iterator();

		List<String> sets = new ArrayList<>(columns.size());
		while(cIterator.hasNext() && vIterator.hasNext()) {
			sets.add(cIterator.next() + " = " + formatValue(vIterator.next()));
		}
		return "SET " + String.join(", ", sets);
	}

	/**
	 * Formats and separates the objects with AND(s).
	 * @param objects any array of objects
	 * @return a formatted string
	 */
	public static String and(Collection objects) {
		Collection<String> objectsAsStrings = objectsToStrings(objects);
		return String.join(" AND ", objectsAsStrings);
	}

	/**
	 * Formats and separates the objects with OR(s).
	 * @param objects any array of objects
	 * @return a formatted string
	 */
	public static String or(Collection objects) {
		Collection<String> objectsAsStrings = objectsToStrings(objects);
		return String.join(" OR ", objectsAsStrings);
	}

	/**
	 * Formats and separates the objects with comma(s), and surrounds with parenthesis and the sql syntax IN.
	 * @param objects any array of objects
	 * @return a formatted string with sql IN syntax
	 */
	public static String in(Collection objects) {
		Collection<String> objectsAsStrings = objectsToStrings(objects);
		return "IN (" + String.join(", ", objectsAsStrings) + ")";
	}

	public static String limit(int limit) {
		return "LIMIT " + limit;
	}

	/**
	 * Simplifies the {@link #exist(String, String)} by having an equal sign between column and value.
	 * @param table name of the table to target
	 * @param column any column
	 * @param value any value
	 * @return a formatted select query
	 */
	public static String exist(String table, String column, Object value) {
		return exist(table, column + " = " + formatValue(value));
	}

	/**
	 * Returns a query with the keyword EXISTS to check if a condition exists.
	 * Does not contain a semicolon at the end.
	 * The where sentence could be 'GUID = 5'.
	 * @param table name of the table to target
	 * @param where any where clause
	 * @returna formatted select query
	 */
	public static String exist(String table, String where) {
		return "SELECT EXISTS(SELECT 0 FROM " + table + " WHERE " + where + ")";
	}

	/**
	 * Formats the object to the correct string depending on the object type.
	 * For example, a string needs to be surrounded by the ' symbol.
	 * @param obj any object
	 * @return a formatted string
	 */
	public static String formatValue(Object obj) {
		String value;

		if(obj instanceof String) {
			String string = (String) obj;
			value = "'" + string.replaceAll("'", "''") + "'";
		} else {
			value = String.valueOf(obj);
		}

		return value;
	}

	/**
	 * Converts objects to strings by using the {@link #formatValue(Object)} function.
	 * @param objects any object
	 * @return array for strings from the objects
	 */
	private static Collection<String> objectsToStrings(Collection objects) {
		Collection<String> collection = new LinkedList<>();
		for(Object object : objects) {
			collection.add(formatValue(object));
		}
		return collection;
	}
}