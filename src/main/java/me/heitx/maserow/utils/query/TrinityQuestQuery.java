package me.heitx.maserow.utils.query;

import me.heitx.maserow.utils.QueryUtil;

import java.util.Map;

public class TrinityQuestQuery {
	private static final String TEMPLATE_TABLE = "quest_template";
	private static final String ADDON_TABLE = "quest_template_addon";

	public static String getInsertQuery(Map<String, Object> attributes, boolean newlineFormat) {
		return QueryUtil.simpleInsert(TEMPLATE_TABLE, attributes, newlineFormat);
	}

	public static String getUpdateQuery(Map<String, Object> attributes, boolean newlineFormat) {
		return QueryUtil.simpleUpdate(TEMPLATE_TABLE, "ID = " + attributes.get("ID"), attributes, newlineFormat);
	}

	public static String getDeleteQuery(int id, boolean newlineFormat) {
		return QueryUtil.simpleDelete(TEMPLATE_TABLE, "ID = " + id, newlineFormat);
	}

	public static String getInsertAddonQuery(Map<String, Object> attributes, boolean newlineFormat) {
		return QueryUtil.simpleInsert(ADDON_TABLE, attributes, newlineFormat);
	}

	public static String getUpdateAddonQuery(Map<String, Object> attributes, boolean newlineFormat) {
		return QueryUtil.simpleUpdate(ADDON_TABLE, "ID = " + attributes.get("ID"), attributes, newlineFormat);
	}

	public static String getDeleteAddonQuery(int id, boolean newlineFormat) {
		return QueryUtil.simpleDelete(ADDON_TABLE, "ID = " + id, newlineFormat);
	}
}