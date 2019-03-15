package me.heitx.maserow.utils;

import java.util.*;

import static me.heitx.maserow.utils.QueryUtil.*;

public final class Queries {
	private Queries() { }

	public static final class Character {
		public static final String TEMPLATE_TABLE = "characters";
		private static final String identifier = "guid";

		private Character() { }

		public static String search(boolean newlineFormat, int guid, String name, int[] raceIds, int[] classIds, int limit) {
			List<String> orBlocks = new ArrayList<>();
			if(guid > -1) orBlocks.add(identifier + " = " + guid);
			if(!name.isEmpty()) orBlocks.add("name LIKE '%" + name + "%'");
			if(raceIds.length > 0) orBlocks.add("race IN(" + String.join(", ", ConverterUtil.toStrings(raceIds) + ")"));
			if(classIds.length > 0) orBlocks.add("class IN(" + String.join(", ", ConverterUtil.toStrings(classIds) + ")"));

			List<String> blocks = new ArrayList<>(select(null, TEMPLATE_TABLE, or(orBlocks)));
			blocks.add(limit(limit));

			return buildNewLineFormat(newlineFormat, blocks);
		}

		public static String insert(boolean newlineFormat, Map<String, Object> attributes) {
			return buildNewLineFormat(newlineFormat, QueryUtil.insert(TEMPLATE_TABLE, attributes));
		}

		public static String update(boolean newlineFormat, Map<String, Object> attributes) {
			return buildNewLineFormat(newlineFormat, simpleUpdate(TEMPLATE_TABLE, identifier, attributes.get(identifier), attributes));
		}

		public static String delete(boolean newlineFormat, int guid) {
			return buildNewLineFormat(newlineFormat, simpleDelete(TEMPLATE_TABLE, identifier, guid));
		}

		public static String get(boolean newlineFormat, int guid) {
			List<String> blocks = new ArrayList<>(simpleSelect(null, TEMPLATE_TABLE, identifier, guid));
			blocks.add(limit(1));

			return buildNewLineFormat(newlineFormat, blocks);
		}

		public static String getAll(boolean newlineFormat, int limit) {
			List<String> blocks = new ArrayList<>(select(null, TEMPLATE_TABLE, null));
			blocks.add(limit(limit));

			return buildNewLineFormat(newlineFormat, blocks);
		}

		public static String exists(boolean newlineFormat, int guid) {
			List<String> blocks = new ArrayList<>();
			blocks.add(exist(TEMPLATE_TABLE, identifier, guid));
			blocks.add(limit(1));

			return buildNewLineFormat(newlineFormat, blocks);
		}
	}

	public static final class Quest {
		public static final String TEMPLATE_TABLE = "quest_template";
		private static final String identifier = "ID";

		private Quest() { }

		public static String search(boolean newlineFormat, int entry, String logTitle, int limit) {
			List<String> orBlocks = new ArrayList<>();
			if(entry > -1) orBlocks.add(identifier + " = " + entry);
			if(!logTitle.isEmpty()) orBlocks.add("LogTitle LIKE '%" + logTitle + "%'");

			List<String> blocks = new ArrayList<>(select(null, TEMPLATE_TABLE, or(orBlocks)));
			blocks.add(limit(limit));

			return buildNewLineFormat(newlineFormat, blocks);
		}

		public static String insert(boolean newlineFormat, Map<String, Object> attributes) {
			return buildNewLineFormat(newlineFormat, QueryUtil.insert(TEMPLATE_TABLE, attributes));
		}

		public static String update(boolean newlineFormat, Map<String, Object> attributes) {
			return buildNewLineFormat(newlineFormat, simpleUpdate(TEMPLATE_TABLE, identifier, attributes.get(identifier), attributes));
		}

		public static String delete(boolean newlineFormat, int entry) {
			return buildNewLineFormat(newlineFormat, simpleDelete(TEMPLATE_TABLE, identifier, entry));
		}

		public static String get(boolean newlineFormat, int entry) {
			List<String> blocks = new ArrayList<>(simpleSelect(null, TEMPLATE_TABLE, identifier, entry));
			blocks.add(limit(1));

			return buildNewLineFormat(newlineFormat, blocks);
		}

		public static String getAll(boolean newlineFormat, int limit) {
			List<String> blocks = new ArrayList<>(select(null, TEMPLATE_TABLE, null));
			blocks.add(limit(limit));

			return buildNewLineFormat(newlineFormat, blocks);
		}

		public static String exists(boolean newlineFormat, int entry) {
			List<String> blocks = new ArrayList<>();
			blocks.add(exist(TEMPLATE_TABLE, identifier, entry));
			blocks.add(limit(1));

			return buildNewLineFormat(newlineFormat, blocks);
		}

		public static String getMaxEntry(boolean newlineFormat) {
			return buildNewLineFormat(newlineFormat, select(Collections.singletonList("MAX(" + identifier + ")"), TEMPLATE_TABLE, null));
		}
	}

	public static final class Item {
		public static final String TEMPLATE_TABLE = "item_template";
		private static final String identifier = "entry";

		private Item() { }

		public static String search(boolean newlineFormat, int entry, String name, int limit) {
			List<String> orBlocks = new ArrayList<>();
			if(entry > -1) orBlocks.add(identifier + " = " + entry);
			if(!name.isEmpty()) orBlocks.add("name LIKE '%" + name + "%'");

			List<String> blocks = new ArrayList<>(select(null, TEMPLATE_TABLE, or(orBlocks)));
			blocks.add(limit(limit));

			return buildNewLineFormat(newlineFormat, blocks);
		}

		public static String insert(boolean newlineFormat, Map<String, Object> attributes) {
			return buildNewLineFormat(newlineFormat, QueryUtil.insert(TEMPLATE_TABLE, attributes));
		}

		public static String update(boolean newlineFormat, Map<String, Object> attributes) {
			return buildNewLineFormat(newlineFormat, simpleUpdate(TEMPLATE_TABLE, identifier, attributes.get(identifier), attributes));
		}

		public static String delete(boolean newlineFormat, int entry) {
			return buildNewLineFormat(newlineFormat, simpleDelete(TEMPLATE_TABLE, identifier, entry));
		}

		public static String get(boolean newlineFormat, int entry) {
			List<String> blocks = new ArrayList<>(simpleSelect(null, TEMPLATE_TABLE, identifier, entry));
			blocks.add(limit(1));

			return buildNewLineFormat(newlineFormat, blocks);
		}

		public static String getAll(boolean newlineFormat, int limit) {
			List<String> blocks = new ArrayList<>(select(null, TEMPLATE_TABLE, null));
			blocks.add(limit(limit));

			return buildNewLineFormat(newlineFormat, blocks);
		}

		public static String exists(boolean newlineFormat, int entry) {
			List<String> blocks = new ArrayList<>();
			blocks.add(exist(TEMPLATE_TABLE, identifier, entry));
			blocks.add(limit(1));

			return buildNewLineFormat(newlineFormat, blocks);
		}

		public static String getMaxEntry(boolean newlineFormat) {
			return buildNewLineFormat(newlineFormat, select(Collections.singletonList("MAX(" + identifier + ")"), TEMPLATE_TABLE, null));
		}
	}

	public static final class Creature {
		public static final String TEMPLATE_TABLE = "creature_template";
		private static final String identifier = "entry";

		private Creature() { }

		public static String search(boolean newlineFormat, int entry, String name, int limit) {
			List<String> orBlocks = new ArrayList<>();
			if(entry > -1) orBlocks.add(identifier + " = " + entry);
			if(!name.isEmpty()) orBlocks.add("name LIKE '%" + name + "%'");

			List<String> blocks = new ArrayList<>(select(null, TEMPLATE_TABLE, or(orBlocks)));
			blocks.add(limit(limit));

			return buildNewLineFormat(newlineFormat, blocks);
		}

		public static String insert(boolean newlineFormat, Map<String, Object> attributes) {
			return buildNewLineFormat(newlineFormat, QueryUtil.insert(TEMPLATE_TABLE, attributes));
		}

		public static String update(boolean newlineFormat, Map<String, Object> attributes) {
			return buildNewLineFormat(newlineFormat, simpleUpdate(TEMPLATE_TABLE, identifier, attributes.get(identifier), attributes));
		}

		public static String delete(boolean newlineFormat, int entry) {
			return buildNewLineFormat(newlineFormat, simpleDelete(TEMPLATE_TABLE, identifier, entry));
		}

		public static String get(boolean newlineFormat, int entry) {
			List<String> blocks = new ArrayList<>(simpleSelect(null, TEMPLATE_TABLE, identifier, entry));
			blocks.add(limit(1));

			return buildNewLineFormat(newlineFormat, blocks);
		}

		public static String getAll(boolean newlineFormat, int limit) {
			List<String> blocks = new ArrayList<>(select(null, TEMPLATE_TABLE, null));
			blocks.add(limit(limit));

			return buildNewLineFormat(newlineFormat, blocks);
		}

		public static String exists(boolean newlineFormat, int entry) {
			List<String> blocks = new ArrayList<>();
			blocks.add(exist(TEMPLATE_TABLE, identifier, entry));
			blocks.add(limit(1));

			return buildNewLineFormat(newlineFormat, blocks);
		}

		public static String getMaxEntry(boolean newlineFormat) {
			return buildNewLineFormat(newlineFormat, select(Collections.singletonList("MAX(" + identifier + ")"), TEMPLATE_TABLE, null));
		}
	}
}