package me.heitx.maserow.query;

import java.util.Map;

public class TrinityItemQuery {
	public static String getInsertItemQuery(Map<String, Object> attributes, boolean newlineFormat) {
		Query query = new Query();
		query.newlineFormat(newlineFormat)
				.insertInto("item_template");

		for(Map.Entry<String, Object> entry : attributes.entrySet()) {
			query.values(entry.getKey(), entry.getValue());
		}

		return query.buildSQL();
	}

	public static String getUpdateItemQuery(Map<String, Object> attributes, boolean newlineFormat) {
		Query query = new Query();
		query.newlineFormat(newlineFormat)
				.update("item_template")
				.where("entry = " + attributes.get("entry"));

		for(Map.Entry<String, Object> entry : attributes.entrySet()) {
			query.set(entry.getKey(), entry.getValue());
		}

		return query.buildSQL();
	}

	public static String getDeleteItemQuery(int entry, boolean newlineFormat) {
		return new Query().newlineFormat(newlineFormat).deleteFrom("item_template").where("entry = " + entry).buildSQL();
	}
}