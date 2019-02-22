package me.heitx.maserow.io;

import java.io.File;

public enum ItemCSV implements ICSV {
	ITEM_BAG_FAMILY("item_bag_family", false, true),
	ITEM_BONDING("item_bonding", true, false),
	ITEM_CLASSES("item_classes", true, false),
	ITEM_DAMAGE_TYPE("item_damage_type", true, false),
	ITEM_FLAGS("item_flags", false, true),
	ITEM_FLAGS_CUSTOM("item_flags_custom", false, true),
	ITEM_FLAGS_EXTRA("item_flags_extra", false, true),
	ITEM_FOOD_TYPE("item_food_type", true, false),
	ITEM_INVENTORY_TYPE("item_inventory_type", true, false),
	ITEM_MATERIAL("item_material", true, false),
	ITEM_QUALITY("item_quality", true, false),
	ITEM_REQUIRED_REPUTATION_FACTION("item_required_reputation_faction", true, false),
	ITEM_SHEATH("item_sheath", true, false),
	ITEM_SOCKET_COLOR("item_socket_color", false, true),
	ITEM_SPELL_TRIGGER("item_spell_trigger", true, false),
	ITEM_STAT_TYPES("item_stat_types", true, false),
	ITEM_TOTEM_CATEGORY("item_totem_category", true, false),
	ITEM_SUBCLASS_00("item_subclass_00", true, true),
	ITEM_SUBCLASS_01("item_subclass_01", true, true),
	ITEM_SUBCLASS_02("item_subclass_02", true, true),
	ITEM_SUBCLASS_03("item_subclass_03", true, true),
	ITEM_SUBCLASS_04("item_subclass_04", true, true),
	ITEM_SUBCLASS_05("item_subclass_05", true, true),
	ITEM_SUBCLASS_06("item_subclass_06", true, true),
	ITEM_SUBCLASS_07("item_subclass_07", true, true),
	ITEM_SUBCLASS_08("item_subclass_08", true, true),
	ITEM_SUBCLASS_09("item_subclass_09", true, true),
	ITEM_SUBCLASS_10("item_subclass_10", true, true),
	ITEM_SUBCLASS_11("item_subclass_11", true, true),
	ITEM_SUBCLASS_12("item_subclass_12", true, true),
	ITEM_SUBCLASS_13("item_subclass_13", true, true),
	ITEM_SUBCLASS_14("item_subclass_14", true, true),
	ITEM_SUBCLASS_15("item_subclass_15", true, true),
	ITEM_SUBCLASS_16("item_subclass_16", true, true);

	public static final String ITEM_CSV_FOLDER = "item";

	private String fileName;
	private boolean hasId;
	private boolean hasValue;

	ItemCSV(String fileName, boolean hasId, boolean hasValue) {
		this.fileName = fileName;
		this.hasId = hasId;
		this.hasValue = hasValue;
	}

	@Override
	public boolean hasId() {
		return hasId;
	}

	@Override
	public boolean hasValue() {
		return hasValue;
	}

	@Override
	public String getFile() {
		return ICSV.CSV_FOLDER_NAME + File.separator + ITEM_CSV_FOLDER + File.separator + fileName + ".csv";
	}
}