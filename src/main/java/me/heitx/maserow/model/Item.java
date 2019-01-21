package me.heitx.maserow.model;

import java.util.ArrayList;
import java.util.List;

public class Item {
	private int entry;
	private int iClass;
	private int subclass;
	private String name;
	private int displayId;
	private int quality;
	private long itemFlags;
	private long itemFlagsExtra;
	private int buyCount;
	private long buyPrice;
	private long sellPrice;
	private int inventoryType;
	private int allowableClass;
	private int allowableRace;
	private int itemLevel;
	private int requiredLevel;
	private int requiredSkill;
	private int requiredSkillRank;
	private int requiredSpell;
	private int requiredHonorRank;
	private int requiredCityRank;
	private int requiredReputationFaction;
	private int requiredReputationRank;
	private int maxCount;
	private int stackable;
	private int containerSlots;
	private int statsCount;
	private List<ItemStat> stats;
	private int scalingStatDistribution;
	private long scalingStatValue;
	private float damageMinimum1;
	private float damageMaximum1;
	private int damageType1;
	private float damageMinimum2;
	private float damageMaximum2;
	private int damageType2;
	private int armor;
	private Resistance resistance;
	private int delay;
	private int ammoType;
	private float rangedModRange;
	private List<ItemSpell> spells;
	private int bonding;
	private String description;
	private int page;
	private int languageId;
	private int pageMaterial;
	private int startQuest;
	private int lockId;
	private int material;
	private int sheath;
	private int randomProperty;
	private int randomSuffix;
	private int block;
	private int itemSet;
	private int maxDurability;
	private int area;
	private int map;
	private int bagFamily;
	private int totemCategory;
	private int socketColor_1;
	private int socketContent_1;
	private int socketColor_2;
	private int socketContent_2;
	private int socketColor_3;
	private int socketContent_3;
	private int socketBonus;
	private int gemProperties;
	private int requiredDisenchantSkill;
	private float armorDamageModifier;
	private long duration;
	private int itemLimitCategory;
	private long holidayId;
	private String scriptName;
	private int disenchantID;
	private int foodType;
	private long minMoneyLoot;
	private long maxMoneyLoot;
	private long flagsCustom;

	public Item() {
		reset();
	}

	public int getEntry() {
		return entry;
	}

	public Item setEntry(int entry) {
		this.entry = entry;
		return this;
	}

	public int getiClass() {
		return iClass;
	}

	public Item setiClass(int iClass) {
		this.iClass = iClass;
		return this;
	}

	public int getSubclass() {
		return subclass;
	}

	public Item setSubclass(int subclass) {
		this.subclass = subclass;
		return this;
	}

	public String getName() {
		return name;
	}

	public Item setName(String name) {
		this.name = name;
		return this;
	}

	public int getDisplayId() {
		return displayId;
	}

	public Item setDisplayId(int displayId) {
		this.displayId = displayId;
		return this;
	}

	public int getQuality() {
		return quality;
	}

	public Item setQuality(int quality) {
		this.quality = quality;
		return this;
	}

	public long getItemFlags() {
		return itemFlags;
	}

	public Item setItemFlags(long itemFlags) {
		this.itemFlags = itemFlags;
		return this;
	}

	public long getItemFlagsExtra() {
		return itemFlagsExtra;
	}

	public Item setItemFlagsExtra(long itemFlagsExtra) {
		this.itemFlagsExtra = itemFlagsExtra;
		return this;
	}

	public int getBuyCount() {
		return buyCount;
	}

	public Item setBuyCount(int buyCount) {
		this.buyCount = buyCount;
		return this;
	}

	public long getBuyPrice() {
		return buyPrice;
	}

	public Item setBuyPrice(long buyPrice) {
		this.buyPrice = buyPrice;
		return this;
	}

	public long getSellPrice() {
		return sellPrice;
	}

	public Item setSellPrice(long sellPrice) {
		this.sellPrice = sellPrice;
		return this;
	}

	public int getInventoryType() {
		return inventoryType;
	}

	public Item setInventoryType(int inventoryType) {
		this.inventoryType = inventoryType;
		return this;
	}

	public long getAllowableClass() {
		return allowableClass;
	}

	public Item setAllowableClass(int allowableClass) {
		this.allowableClass = allowableClass;
		return this;
	}

	public long getAllowableRace() {
		return allowableRace;
	}

	public Item setAllowableRace(int allowableRace) {
		this.allowableRace = allowableRace;
		return this;
	}

	public int getItemLevel() {
		return itemLevel;
	}

	public Item setItemLevel(int itemLevel) {
		this.itemLevel = itemLevel;
		return this;
	}

	public int getRequiredLevel() {
		return requiredLevel;
	}

	public Item setRequiredLevel(int requiredLevel) {
		this.requiredLevel = requiredLevel;
		return this;
	}

	public int getRequiredSkill() {
		return requiredSkill;
	}

	public Item setRequiredSkill(int requiredSkill) {
		this.requiredSkill = requiredSkill;
		return this;
	}

	public int getRequiredSkillRank() {
		return requiredSkillRank;
	}

	public Item setRequiredSkillRank(int requiredSkillRank) {
		this.requiredSkillRank = requiredSkillRank;
		return this;
	}

	public int getRequiredSpell() {
		return requiredSpell;
	}

	public Item setRequiredSpell(int requiredSpell) {
		this.requiredSpell = requiredSpell;
		return this;
	}

	public int getRequiredHonorRank() {
		return requiredHonorRank;
	}

	public Item setRequiredHonorRank(int requiredHonorRank) {
		this.requiredHonorRank = requiredHonorRank;
		return this;
	}

	public int getRequiredCityRank() {
		return requiredCityRank;
	}

	public Item setRequiredCityRank(int requiredCityRank) {
		this.requiredCityRank = requiredCityRank;
		return this;
	}

	public int getRequiredReputationFaction() {
		return requiredReputationFaction;
	}

	public Item setRequiredReputationFaction(int requiredReputationFaction) {
		this.requiredReputationFaction = requiredReputationFaction;
		return this;
	}

	public int getRequiredReputationRank() {
		return requiredReputationRank;
	}

	public Item setRequiredReputationRank(int requiredReputationRank) {
		this.requiredReputationRank = requiredReputationRank;
		return this;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public Item setMaxCount(int maxCount) {
		this.maxCount = maxCount;
		return this;
	}

	public int getStackable() {
		return stackable;
	}

	public Item setStackable(int stackable) {
		this.stackable = stackable;
		return this;
	}

	public int getContainerSlots() {
		return containerSlots;
	}

	public Item setContainerSlots(int containerSlots) {
		this.containerSlots = containerSlots;
		return this;
	}

	public int getStatsCount() {
		return statsCount;
	}

	public Item setStatsCount(int statsCount) {
		this.statsCount = statsCount;
		return this;
	}

	public List<ItemStat> getStats() {
		return stats;
	}

	public Item setStats(List<ItemStat> stats) {
		this.stats = stats;
		return this;
	}

	public int getScalingStatDistribution() {
		return scalingStatDistribution;
	}

	public Item setScalingStatDistribution(int scalingStatDistribution) {
		this.scalingStatDistribution = scalingStatDistribution;
		return this;
	}

	public long getScalingStatValue() {
		return scalingStatValue;
	}

	public Item setScalingStatValue(long scalingStatValue) {
		this.scalingStatValue = scalingStatValue;
		return this;
	}

	public float getDamageMinimum1() {
		return damageMinimum1;
	}

	public Item setDamageMinimum1(float damageMinimum1) {
		this.damageMinimum1 = damageMinimum1;
		return this;
	}

	public float getDamageMaximum1() {
		return damageMaximum1;
	}

	public Item setDamageMaximum1(float damageMaximum1) {
		this.damageMaximum1 = damageMaximum1;
		return this;
	}

	public int getDamageType1() {
		return damageType1;
	}

	public Item setDamageType1(int damageType1) {
		this.damageType1 = damageType1;
		return this;
	}

	public float getDamageMinimum2() {
		return damageMinimum2;
	}

	public Item setDamageMinimum2(float damageMinimum2) {
		this.damageMinimum2 = damageMinimum2;
		return this;
	}

	public float getDamageMaximum2() {
		return damageMaximum2;
	}

	public Item setDamageMaximum2(float damageMaximum2) {
		this.damageMaximum2 = damageMaximum2;
		return this;
	}

	public int getDamageType2() {
		return damageType2;
	}

	public Item setDamageType2(int damageType2) {
		this.damageType2 = damageType2;
		return this;
	}

	public int getArmor() {
		return armor;
	}

	public Item setArmor(int armor) {
		this.armor = armor;
		return this;
	}

	public Resistance getResistance() {
		return resistance;
	}

	public Item setResistance(Resistance resistance) {
		this.resistance = resistance;
		return this;
	}

	public int getDelay() {
		return delay;
	}

	public Item setDelay(int delay) {
		this.delay = delay;
		return this;
	}

	public int getAmmoType() {
		return ammoType;
	}

	public Item setAmmoType(int ammoType) {
		this.ammoType = ammoType;
		return this;
	}

	public float getRangedModRange() {
		return rangedModRange;
	}

	public Item setRangedModRange(float rangedModRange) {
		this.rangedModRange = rangedModRange;
		return this;
	}

	public List<ItemSpell> getSpells() {
		return spells;
	}

	public Item setSpells(List<ItemSpell> spells) {
		this.spells = spells;
		return this;
	}

	public int getBonding() {
		return bonding;
	}

	public Item setBonding(int bonding) {
		this.bonding = bonding;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Item setDescription(String description) {
		this.description = description;
		return this;
	}

	public int getPage() {
		return page;
	}

	public Item setPage(int page) {
		this.page = page;
		return this;
	}

	public int getLanguageId() {
		return languageId;
	}

	public Item setLanguageId(int languageId) {
		this.languageId = languageId;
		return this;
	}

	public int getPageMaterial() {
		return pageMaterial;
	}

	public Item setPageMaterial(int pageMaterial) {
		this.pageMaterial = pageMaterial;
		return this;
	}

	public int getStartQuest() {
		return startQuest;
	}

	public Item setStartQuest(int startQuest) {
		this.startQuest = startQuest;
		return this;
	}

	public int getLockId() {
		return lockId;
	}

	public Item setLockId(int lockId) {
		this.lockId = lockId;
		return this;
	}

	public int getMaterial() {
		return material;
	}

	public Item setMaterial(int material) {
		this.material = material;
		return this;
	}

	public int getSheath() {
		return sheath;
	}

	public Item setSheath(int sheath) {
		this.sheath = sheath;
		return this;
	}

	public int getRandomProperty() {
		return randomProperty;
	}

	public Item setRandomProperty(int randomProperty) {
		this.randomProperty = randomProperty;
		return this;
	}

	public int getRandomSuffix() {
		return randomSuffix;
	}

	public Item setRandomSuffix(int randomSuffix) {
		this.randomSuffix = randomSuffix;
		return this;
	}

	public int getBlock() {
		return block;
	}

	public Item setBlock(int block) {
		this.block = block;
		return this;
	}

	public int getItemSet() {
		return itemSet;
	}

	public Item setItemSet(int itemSet) {
		this.itemSet = itemSet;
		return this;
	}

	public int getMaxDurability() {
		return maxDurability;
	}

	public Item setMaxDurability(int maxDurability) {
		this.maxDurability = maxDurability;
		return this;
	}

	public int getArea() {
		return area;
	}

	public Item setArea(int area) {
		this.area = area;
		return this;
	}

	public int getMap() {
		return map;
	}

	public Item setMap(int map) {
		this.map = map;
		return this;
	}

	public int getBagFamily() {
		return bagFamily;
	}

	public Item setBagFamily(int bagFamily) {
		this.bagFamily = bagFamily;
		return this;
	}

	public int getTotemCategory() {
		return totemCategory;
	}

	public Item setTotemCategory(int totemCategory) {
		this.totemCategory = totemCategory;
		return this;
	}

	public int getSocketColor_1() {
		return socketColor_1;
	}

	public Item setSocketColor_1(int socketColor_1) {
		this.socketColor_1 = socketColor_1;
		return this;
	}

	public int getSocketContent_1() {
		return socketContent_1;
	}

	public Item setSocketContent_1(int socketContent_1) {
		this.socketContent_1 = socketContent_1;
		return this;
	}

	public int getSocketColor_2() {
		return socketColor_2;
	}

	public Item setSocketColor_2(int socketColor_2) {
		this.socketColor_2 = socketColor_2;
		return this;
	}

	public int getSocketContent_2() {
		return socketContent_2;
	}

	public Item setSocketContent_2(int socketContent_2) {
		this.socketContent_2 = socketContent_2;
		return this;
	}

	public int getSocketColor_3() {
		return socketColor_3;
	}

	public Item setSocketColor_3(int socketColor_3) {
		this.socketColor_3 = socketColor_3;
		return this;
	}

	public int getSocketContent_3() {
		return socketContent_3;
	}

	public Item setSocketContent_3(int socketContent_3) {
		this.socketContent_3 = socketContent_3;
		return this;
	}

	public int getSocketBonus() {
		return socketBonus;
	}

	public Item setSocketBonus(int socketBonus) {
		this.socketBonus = socketBonus;
		return this;
	}

	public int getGemProperties() {
		return gemProperties;
	}

	public Item setGemProperties(int gemProperties) {
		this.gemProperties = gemProperties;
		return this;
	}

	public int getRequiredDisenchantSkill() {
		return requiredDisenchantSkill;
	}

	public Item setRequiredDisenchantSkill(int requiredDisenchantSkill) {
		this.requiredDisenchantSkill = requiredDisenchantSkill;
		return this;
	}

	public float getArmorDamageModifier() {
		return armorDamageModifier;
	}

	public Item setArmorDamageModifier(float armorDamageModifier) {
		this.armorDamageModifier = armorDamageModifier;
		return this;
	}

	public long getDuration() {
		return duration;
	}

	public Item setDuration(long duration) {
		this.duration = duration;
		return this;
	}

	public int getItemLimitCategory() {
		return itemLimitCategory;
	}

	public Item setItemLimitCategory(int itemLimitCategory) {
		this.itemLimitCategory = itemLimitCategory;
		return this;
	}

	public long getHolidayId() {
		return holidayId;
	}

	public Item setHolidayId(long holidayId) {
		this.holidayId = holidayId;
		return this;
	}

	public String getScriptName() {
		return scriptName;
	}

	public Item setScriptName(String scriptName) {
		this.scriptName = scriptName;
		return this;
	}

	public int getDisenchantID() {
		return disenchantID;
	}

	public Item setDisenchantID(int disenchantID) {
		this.disenchantID = disenchantID;
		return this;
	}

	public int getFoodType() {
		return foodType;
	}

	public Item setFoodType(int foodType) {
		this.foodType = foodType;
		return this;
	}

	public long getMinMoneyLoot() {
		return minMoneyLoot;
	}

	public Item setMinMoneyLoot(long minMoneyLoot) {
		this.minMoneyLoot = minMoneyLoot;
		return this;
	}

	public long getMaxMoneyLoot() {
		return maxMoneyLoot;
	}

	public Item setMaxMoneyLoot(long maxMoneyLoot) {
		this.maxMoneyLoot = maxMoneyLoot;
		return this;
	}

	public long getFlagsCustom() {
		return flagsCustom;
	}

	public Item setFlagsCustom(long flagsCustom) {
		this.flagsCustom = flagsCustom;
		return this;
	}

	public void reset() {
		entry = 0;
		iClass = 0;
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

		stats = new ArrayList<>();
		for(int i = 0; i < 10; i++) stats.add(new ItemStat());

		scalingStatDistribution = 0;
		scalingStatValue = 0;
		damageMinimum1 = 0;
		damageMaximum1 = 0;
		damageType1 = 0;
		damageMinimum2 = 0;
		damageMaximum2 = 0;
		damageType2 = 0;
		armor = 0;
		resistance = new Resistance();
		delay = 1000;
		ammoType = 0;
		rangedModRange = 0;

		spells = new ArrayList<>();
		for(int i = 0; i < 5; i++) spells.add(new ItemSpell());

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