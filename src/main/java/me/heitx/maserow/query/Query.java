package me.heitx.maserow.query;

import java.util.*;

public class Query {
	private QueryType type;
	private String table;
	private String where;
	private List<String> andList;
	private List<String> orList;
	private int limit;
	private boolean newlineFormat;

	// INSERT / UPDATE VARIABLES
	private List<String> columns;
	private List<Object> values;

	// SELECT VARIABLES
	private List<String> selectList;

	public Query() {
		andList = new ArrayList<>();
		orList = new ArrayList<>();
		columns = new ArrayList<>();
		values = new ArrayList<>();
		selectList = new ArrayList<>();

		newlineFormat = false;
	}

	public Query where(String condition) {
		where = condition;
		return this;
	}

	public Query and(String condition) {
		if(where != null) andList.add(condition);
		else where(condition);
		return this;
	}

	public Query or(String condition) {
		if(where != null) orList.add(condition);
		else where(condition);
		return this;
	}

	public Query limit(int limit) {
		this.limit = limit;
		return this;
	}

	public Query newlineFormat(boolean newlineFormat) {
		this.newlineFormat = newlineFormat;
		return this;
	}

	// INSERT METHODS

	public Query insertInto(String table) {
		this.type = QueryType.INSERT_INTO;
		return from(table);
	}

	public Query values(Object value) {
		values.add(value);
		return this;
	}

	public Query values(String column, Object value) {
		columns.add(column);
		values.add(value);
		return this;
	}

	public Query values(Map<String, Object> columnValueMap) {
		for(Map.Entry<String, Object> entry : columnValueMap.entrySet()) {
			columns.add(entry.getKey());
			values.add(entry.getValue());
		}
		return this;
	}

	// SELECT METHODS

	public Query select(String... columns) {
		return select(Arrays.asList(columns));
	}

	public Query select(Collection<String> collection) {
		this.type = QueryType.SELECT;
		selectList.addAll(collection);
		return this;
	}

	public Query from(String table) {
		this.table = table;
		return this;
	}

	// UPDATE METHODS

	public Query update(String table) {
		type = QueryType.UPDATE;
		return from(table);
	}

	public Query set(String column, Object value) {
		return values(column, value);
	}

	public Query set(Map<String, Object> columnValueMap) {
		return values(columnValueMap);
	}

	// DELETE METHODS

	public Query deleteFrom(String table) {
		type = QueryType.DELETE;
		return from(table);
	}

	// BUILD SQL

	private List<String> correctValueFormat(List<Object> values) {
		List<String> valuesAsString = new ArrayList<>();

		for(Object value : values) {
			if(value instanceof String) {
				String s = (String) value;
				valuesAsString.add("'" + s.replaceAll("'", "''") + "'");
			} else if(value instanceof Number) {
				valuesAsString.add(String.valueOf(value));
			}
		}

		return valuesAsString;
	}

	private List<String> buildInsertIntoSQL() {
		String insertColumns = "";
		String insertValues = "";

		if(columns.size() > 0) {
			insertColumns = " (".concat(String.join(", ", columns)).concat(")");
		}

		if(values.size() > 0) {
			insertValues = "VALUES (".concat(String.join(", ", correctValueFormat(values))).concat(")");
		}

		List<String> pieces = new ArrayList<>(2);
		pieces.add("INSERT INTO " + table + insertColumns);
		pieces.add(insertValues);

		return pieces;
	}

	private List<String> buildSelectSQL() {
		List<String> pieces = new ArrayList<>(2);
		pieces.add("SELECT " + String.join(", ", selectList));
		pieces.add("FROM " + table);

		return pieces;
	}

	private List<String> buildUpdateSQL() {
		List<String> pieces = new ArrayList<>(2);
		pieces.add("UPDATE " + table);

		if(values.size() > 0 && values.size() == columns.size()) {
			List<String> valuesAsString = correctValueFormat(values);
			StringBuilder setBuilder = new StringBuilder("SET ");

			boolean multipleValues = values.size() > 1;

			for(int i = 0; i < values.size(); i++) {
				setBuilder.append(columns.get(i));
				setBuilder.append(" = ");
				setBuilder.append(valuesAsString.get(i));

				if(multipleValues && i < values.size() - 1)
					setBuilder.append(", ");
			}

			pieces.add(setBuilder.toString());
		}

		return pieces;
	}

	private List<String> buildDeleteSQL() {
		List<String> pieces = new ArrayList<>(1);
		pieces.add("DELETE FROM " + table);

		return pieces;
	}

	public String buildSQL() {
		if(type == null) return "The Type of the Query is unknown!";

		List<String> pieces = new ArrayList<>();

		switch(type) {
			case INSERT_INTO:
				pieces.addAll(buildInsertIntoSQL());
				break;
			case SELECT:
				pieces.addAll(buildSelectSQL());
				break;
			case UPDATE:
				pieces.addAll(buildUpdateSQL());
				break;
			case DELETE:
				pieces.addAll(buildDeleteSQL());
				break;
		}

		// Adds the: WHERE ... AND ...

		if(where != null && !where.isEmpty()) {
			String wherePiece = "WHERE ".concat(where);
			if(andList.size() > 0) {
				wherePiece = wherePiece.concat(" AND " + String.join(" AND ", andList));
			}
			if(orList.size() > 0) {
				wherePiece = wherePiece.concat(" OR " + String.join(" OR ", orList));
			}
			pieces.add(wherePiece);
		}

		// Adds the: LIMIT X

		if(limit > 0) {
			pieces.add("LIMIT ".concat(String.valueOf(limit)));
		}

		return buildSQL(pieces);
	}

	private String buildSQL(List<String> pieces) {
		List<String> blocks = new ArrayList<>();

		for(String s : pieces) {
			if(!s.isEmpty()) blocks.add(s);
		}

		return String.join((newlineFormat ? System.lineSeparator() : " "), blocks) + ';';
	}
}