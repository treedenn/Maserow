package me.heitx.maserow.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Item {
	@Column("entry") private int entry;
	@Column("class") private int _class;
	@Column("subclass") private int subclass;
	@Column("name") private String name;
	@Column("displayid") private int displayId;
	@Column("Quality") private int quality;
	@Column("Flags") private long itemFlags;
	@Column("FlagsExtra") private long itemFlagsExtra;
	@Column("BuyCount") private int buyCount;
	@Column("BuyPrice") private long buyPrice;
	@Column("SellPrice") private long sellPrice;
	@Column("InventoryType") private int inventoryType;
	@Column("AllowableClass") private int allowableClass;
	@Column("AllowableRace") private int allowableRace;
	@Column("ItemLevel") private int itemLevel;
	@Column("RequiredLevel") private int requiredLevel;
	@Column("RequiredSkill") private int requiredSkill;
	@Column("RequiredSkillRank") private int requiredSkillRank;
	@Column("requiredspell") private int requiredSpell;
	@Column("requiredhonorrank") private int requiredHonorRank;
	@Column("RequiredCityRank") private int requiredCityRank;
	@Column("RequiredReputationFaction") private int requiredReputationFaction;
	@Column("RequiredReputationRank") private int requiredReputationRank;
	@Column("maxcount") private int maxCount;
	@Column("stackable") private int stackable;
	@Column("ContainerSlots") private int containerSlots;
	@Column("StatsCount") private int statsCount;
	@Column("stat_type1") private int statType1;
	@Column("stat_value1") private int statValue1;
	@Column("stat_type2") private int statType2;
	@Column("stat_value2") private int statValue2;
	@Column("stat_type3") private int statType3;
	@Column("stat_value3") private int statValue3;
	@Column("stat_type4") private int statType4;
	@Column("stat_value4") private int statValue4;
	@Column("stat_type5") private int statType5;
	@Column("stat_value5") private int statValue5;
	@Column("stat_type6") private int statType6;
	@Column("stat_value6") private int statValue6;
	@Column("stat_type7") private int statType7;
	@Column("stat_value7") private int statValue7;
	@Column("stat_type8") private int statType8;
	@Column("stat_value8") private int statValue8;
	@Column("stat_type9") private int statType9;
	@Column("stat_value9") private int statValue9;
	@Column("stat_type10") private int statType10;
	@Column("stat_value10") private int statValue10;
	@Column("ScalingStatDistribution") private int scalingStatDistribution;
	@Column("ScalingStatValue") private long scalingStatValue;
	@Column("dmg_min1") private float damageMinimum1;
	@Column("dmg_max1") private float damageMaximum1;
	@Column("dmg_type1") private int damageType1;
	@Column("dmg_min2") private float damageMinimum2;
	@Column("dmg_max2") private float damageMaximum2;
	@Column("dmg_type2") private int damageType2;
	@Column("armor") private int armor;
	@Column("holy_res") private int holyRes;
	@Column("fire_res") private int fireRes;
	@Column("nature_res") private int natureRes;
	@Column("frost_res") private int frostRes;
	@Column("shadow_res") private int shadowRes;
	@Column("arcane_res") private int arcaneRes;
	@Column("delay") private int delay;
	@Column("ammo_type") private int ammoType;
	@Column("RangedModRange") private float rangedModRange;
	@Column("spellid_1") private int spellId1;
	@Column("spelltrigger_1") private int spellTrigger1;
	@Column("spellcharges_1") private int spellCharges1;
	@Column("spellppmrate_1") private float spellPpmRate1;
	@Column("spellcooldown_1") private int spellCooldown1;
	@Column("spellcategory_1") private int spellCategory1;
	@Column("spellcategorycooldown_1") private int spellCategoryCooldown1;
	@Column("spellid_2") private int SpellId2;
	@Column("spelltrigger_2") private int spellTrigger2;
	@Column("spellcharges_2") private int spellCharges2;
	@Column("spellppmrate_2") private float spellPpmRate2;
	@Column("spellcooldown_2") private int spellCooldown2;
	@Column("spellcategory_2") private int spellCategory2;
	@Column("spellcategorycooldown_2") private int spellCategoryCooldown2;
	@Column("spellid_3") private int SpellId3;
	@Column("spelltrigger_3") private int spellTrigger3;
	@Column("spellcharges_3") private int spellCharges3;
	@Column("spellppmrate_3") private float spellPpmRate3;
	@Column("spellcooldown_3") private int spellCooldown3;
	@Column("spellcategory_3") private int spellCategory3;
	@Column("spellcategorycooldown_3") private int spellCategoryCooldown3;
	@Column("spellid_4") private int SpellId4;
	@Column("spelltrigger_4") private int spellTrigger4;
	@Column("spellcharges_4") private int spellCharges4;
	@Column("spellppmrate_4") private float spellPpmRate4;
	@Column("spellcooldown_4") private int spellCooldown4;
	@Column("spellcategory_4") private int spellCategory4;
	@Column("spellcategorycooldown_4") private int spellCategoryCooldown4;
	@Column("spellid_5") private int SpellId5;
	@Column("spelltrigger_5") private int spellTrigger5;
	@Column("spellcharges_5") private int spellCharges5;
	@Column("spellppmrate_5") private float spellPpmRate5;
	@Column("spellcooldown_5") private int spellCooldown5;
	@Column("spellcategory_5") private int spellCategory5;
	@Column("spellcategorycooldown_5") private int spellCategoryCooldown5;
	@Column("bonding") private int bonding;
	@Column("description") private String description;
	@Column("PageText") private int page;
	@Column("LanguageID") private int languageId;
	@Column("PageMaterial") private int pageMaterial;
	@Column("startquest") private int startQuest;
	@Column("lockid") private int lockId;
	@Column("Material") private int material;
	@Column("sheath") private int sheath;
	@Column("RandomProperty") private int randomProperty;
	@Column("RandomSuffix") private int randomSuffix;
	@Column("block") private int block;
	@Column("itemset") private int itemSet;
	@Column("MaxDurability") private int maxDurability;
	@Column("area") private int area;
	@Column("Map") private int map;
	@Column("BagFamily") private int bagFamily;
	@Column("TotemCategory") private int totemCategory;
	@Column("socketColor_1") private int socketColor_1;
	@Column("socketContent_1") private int socketContent_1;
	@Column("socketColor_2") private int socketColor_2;
	@Column("socketContent_2") private int socketContent_2;
	@Column("socketColor_3") private int socketColor_3;
	@Column("socketContent_3") private int socketContent_3;
	@Column("socketBonus") private int socketBonus;
	@Column("GemProperties") private int gemProperties;
	@Column("RequiredDisenchantSkill") private int requiredDisenchantSkill;
	@Column("ArmorDamageModifier") private float armorDamageModifier;
	@Column("duration") private long duration;
	@Column("ItemLimitCategory") private int itemLimitCategory;
	@Column("HolidayId") private long holidayId;
	@Column("ScriptName") private String scriptName;
	@Column("DisenchantID") private int disenchantID;
	@Column("FoodType") private int foodType;
	@Column("minMoneyLoot") private long minMoneyLoot;
	@Column("maxMoneyLoot") private long maxMoneyLoot;
	@Column("flagsCustom") private long flagsCustom;

	public Item() {
		reset();
	}

	public int[] getStatTypes() {
		return new int[] {
				statType1, statType2, statType3,
				statType4, statType5, statType6,
				statType7, statType8, statType9,
				statType10
		};
	}

	public int[] getStatValues() {
		return new int[] {
				statValue1, statValue2, statValue3,
				statValue4, statValue5, statValue6,
				statValue7, statValue8, statValue9,
				statValue10
		};
	}

	public int[] getResistance() {
		return new int[] {
				holyRes, fireRes, frostRes,
				natureRes, shadowRes, arcaneRes
		};
	}

	public void reset() {
		entry = 0;
		_class = 0;
		subclass = 0;
		name = "Default Template";
		displayId = 0;
		quality = 0;
		itemFlags = 0;
		itemFlagsExtra = 0;
		buyCount = 1;
		buyPrice = 0;
		sellPrice = 0;
		inventoryType = 0;
		allowableClass = -1;
		allowableRace = -1;
		itemLevel = 0;
		requiredLevel = 0;
		requiredSkill = 0;
		requiredSkillRank = 0;
		requiredSpell = 0;
		requiredHonorRank = 0;
		requiredCityRank = 0;
		requiredReputationFaction = 0;
		requiredReputationRank = 0;
		maxCount = 0;
		stackable = 1;
		containerSlots = 0;
		statsCount = 0;
		statType1 = 0;
		statValue1 = 0;
		statType2 = 0;
		statValue2 = 0;
		statType3 = 0;
		statValue3 = 0;
		statType4 = 0;
		statValue4 = 0;
		statType5 = 0;
		statValue5 = 0;
		statType6 = 0;
		statValue6 = 0;
		statType7 = 0;
		statValue7 = 0;
		statType8 = 0;
		statValue8 = 0;
		statType9 = 0;
		statValue9 = 0;
		statType10 = 0;
		statValue10 = 0;
		scalingStatDistribution = 0;
		scalingStatValue = 0;
		damageMinimum1 = 0;
		damageMaximum1 = 0;
		damageType1 = 0;
		damageMinimum2 = 0;
		damageMaximum2 = 0;
		damageType2 = 0;
		armor = 0;
		holyRes = 0;
		fireRes = 0;
		natureRes = 0;
		frostRes = 0;
		shadowRes = 0;
		arcaneRes = 0;
		delay = 1000;
		ammoType = 0;
		rangedModRange = 0;
		spellId1 = 0;
		spellTrigger1 = 0;
		spellCharges1 = 0;
		spellPpmRate1 = 0;
		spellCooldown1 = -1;
		spellCategory1 = 0;
		spellCategoryCooldown1 = -1;
		SpellId2 = 0;
		spellTrigger2 = 0;
		spellCharges2 = 0;
		spellPpmRate2 = 0;
		spellCooldown2 = -1;
		spellCategory2 = 0;
		spellCategoryCooldown2 = -1;
		SpellId3 = 0;
		spellTrigger3 = 0;
		spellCharges3 = 0;
		spellPpmRate3 = 0;
		spellCooldown3 = -1;
		spellCategory3 = 0;
		spellCategoryCooldown3 = -1;
		SpellId4 = 0;
		spellTrigger4 = 0;
		spellCharges4 = 0;
		spellPpmRate4 = 0;
		spellCooldown4 = -1;
		spellCategory4 = 0;
		spellCategoryCooldown4 = -1;
		SpellId5 = 0;
		spellTrigger5 = 0;
		spellCharges5 = 0;
		spellPpmRate5 = 0;
		spellCooldown5 = -1;
		spellCategory5 = 0;
		spellCategoryCooldown5 = -1;
		bonding = 0;
		description = "";
		page = 0;
		languageId = 0;
		pageMaterial = 0;
		startQuest = 0;
		lockId = 0;
		material = 0;
		sheath = 0;
		randomProperty = 0;
		randomSuffix = 0;
		block = 0;
		itemSet = 0;
		maxDurability = 0;
		area = 0;
		map = 0;
		bagFamily = 0;
		totemCategory = 0;
		socketColor_1 = 0;
		socketContent_1 = 0;
		socketColor_2 = 0;
		socketContent_2 = 0;
		socketColor_3 = 0;
		socketContent_3 = 0;
		socketBonus = 0;
		gemProperties = 0;
		requiredDisenchantSkill = -1;
		armorDamageModifier = 0;
		duration = 0;
		itemLimitCategory = 0;
		holidayId = 0;
		scriptName = "";
		disenchantID = 0;
		foodType = 0;
		minMoneyLoot = 0;
		maxMoneyLoot = 0;
		flagsCustom = 0;
	}
}