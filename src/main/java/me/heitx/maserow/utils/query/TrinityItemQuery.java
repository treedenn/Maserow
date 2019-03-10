package me.heitx.maserow.utils.query;

import me.heitx.maserow.utils.QueryUtil;

import java.util.Map;

public class TrinityItemQuery {
	public static final String TEMPLATE_TABLE = "item_template";

	public static String getInsertQuery(Map<String, Object> attributes, boolean newlineFormat) {
		return QueryUtil.simpleInsert(TEMPLATE_TABLE, attributes, newlineFormat);
	}

	public static String getUpdateQuery(Map<String, Object> attributes, boolean newlineFormat) {
		return QueryUtil.simpleUpdate(TEMPLATE_TABLE, "entry = " + attributes.get("entry"), attributes, newlineFormat);
	}

	public static String getDeleteQuery(int entry, boolean newlineFormat) {
		return QueryUtil.simpleDelete(TEMPLATE_TABLE, "entry + " + entry, newlineFormat);
	}
}