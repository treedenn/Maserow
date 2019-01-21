package me.heitx.maserow.converter;

import me.heitx.maserow.model.Item;
import me.heitx.maserow.model.ItemSpell;
import me.heitx.maserow.model.ItemStat;
import me.heitx.maserow.model.Resistance;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TrinityWotlkConverter {
	public static Item toItem(Map<String, Object> attributes) {
		Item item = new Item();
		List<ItemStat> stats = new ArrayList<>();
		List<ItemSpell> spells = new ArrayList<>();

		item.setEntry((Integer) attributes.get("entry"));
		item.setiClass((Integer) attributes.get("class"));
		item.setSubclass((Integer) attributes.get("subclass"));
		item.setName((String) attributes.get("name"));
		item.setDisplayId((Integer) attributes.get("displayid"));
		item.setQuality((Integer) attributes.get("Quality"));
		item.setItemFlags((Long) attributes.get("Flags"));
		item.setItemFlagsExtra((Long) attributes.get("FlagsExtra"));
		item.setBuyCount((Integer) attributes.get("BuyCount"));
		item.setBuyPrice((Long) attributes.get("BuyPrice"));
		item.setSellPrice((Long) attributes.get("SellPrice"));
		item.setInventoryType((Integer) attributes.get("InventoryType"));
		item.setAllowableClass((Integer) attributes.get("AllowableClass"));
		item.setAllowableRace((Integer) attributes.get("AllowableRace"));
		item.setItemLevel((Integer) attributes.get("ItemLevel"));
		item.setRequiredLevel((Integer) attributes.get("RequiredLevel"));
		item.setRequiredSkill((Integer) attributes.get("RequiredSkill"));
		item.setRequiredSkillRank((Integer) attributes.get("RequiredSkillRank"));
		item.setRequiredSpell((Integer) attributes.get("requiredspell"));
		item.setRequiredHonorRank((Integer) attributes.get("requiredhonorrank"));
		item.setRequiredCityRank((Integer) attributes.get("RequiredCityRank"));
		item.setRequiredReputationFaction((Integer) attributes.get("RequiredReputationFaction"));
		item.setRequiredReputationRank((Integer) attributes.get("RequiredReputationRank"));
		item.setMaxCount((Integer) attributes.get("maxcount"));
		item.setStackable((Integer) attributes.get("stackable"));
		item.setContainerSlots((Integer) attributes.get("ContainerSlots"));
		item.setStatsCount((Integer) attributes.get("StatsCount"));

		for(int i = 1; i <= 10; i++) {
			stats.add(new ItemStat((int) attributes.get("stat_type" + i), (int) attributes.get("stat_value" + i)));
		}

		item.setStats(stats);

		item.setScalingStatDistribution((Integer) attributes.get("ScalingStatDistribution"));
		item.setScalingStatValue((Long) attributes.get("ScalingStatValue"));
		item.setDamageMinimum1((Float) attributes.get("dmg_min1"));
		item.setDamageMaximum1((Float) attributes.get("dmg_max1"));
		item.setDamageType1((Integer) attributes.get("dmg_type1"));
		item.setDamageMinimum2((Float) attributes.get("dmg_min2"));
		item.setDamageMaximum2((Float) attributes.get("dmg_max2"));
		item.setDamageType2((Integer) attributes.get("dmg_type2"));
		item.setArmor((Integer) attributes.get("armor"));

		item.setResistance(getResistance(attributes,
				"holy_res", "fire_res", "nature_res",
				"frost_res", "shadow_res", "arcane_res"));

		item.setDelay((Integer) attributes.get("delay"));
		item.setAmmoType((Integer) attributes.get("ammo_type"));
		item.setRangedModRange((Float) attributes.get("RangedModRange"));

		for(int i = 1; i <= 5; i++) {
			spells.add(new ItemSpell((Integer) attributes.get("spellid_" + i), (Integer) attributes.get("spelltrigger_" + i), (Integer) attributes.get("spellcharges_" + i),
					(Float) attributes.get("spellppmRate_" + i), (Integer) attributes.get("spellcooldown_" + i), (Integer) attributes.get("spellcategory_" + i),
					(Integer) attributes.get("spellcategorycooldown_" + i)));
		}

		item.setSpells(spells);

		item.setBonding((Integer) attributes.get("bonding"));
		item.setDescription((String) attributes.get("description"));
		item.setPage((Integer) attributes.get("PageText"));
		item.setLanguageId((Integer) attributes.get("LanguageID"));
		item.setPageMaterial((Integer) attributes.get("PageMaterial"));
		item.setStartQuest((Integer) attributes.get("startquest"));
		item.setLockId((Integer) attributes.get("lockid"));
		item.setMaterial((Integer) attributes.get("Material"));
		item.setSheath((Integer) attributes.get("sheath"));
		item.setRandomProperty((Integer) attributes.get("RandomProperty"));
		item.setRandomSuffix((Integer) attributes.get("RandomSuffix"));
		item.setBlock((Integer) attributes.get("block"));
		item.setItemSet((Integer) attributes.get("itemset"));
		item.setMaxDurability((Integer) attributes.get("MaxDurability"));
		item.setArea((Integer) attributes.get("area"));
		item.setMap((Integer) attributes.get("Map"));
		item.setBagFamily((Integer) attributes.get("BagFamily"));
		item.setTotemCategory((Integer) attributes.get("TotemCategory"));
		item.setSocketColor_1((Integer) attributes.get("socketColor_1"));
		item.setSocketContent_1((Integer) attributes.get("socketContent_1"));
		item.setSocketColor_2((Integer) attributes.get("socketColor_2"));
		item.setSocketContent_2((Integer) attributes.get("socketContent_2"));
		item.setSocketColor_3((Integer) attributes.get("socketColor_3"));
		item.setSocketContent_3((Integer) attributes.get("socketContent_3"));
		item.setSocketBonus((Integer) attributes.get("socketBonus"));
		item.setGemProperties((Integer) attributes.get("GemProperties"));
		item.setRequiredDisenchantSkill((Integer) attributes.get("RequiredDisenchantSkill"));
		item.setArmorDamageModifier((Float) attributes.get("ArmorDamageModifier"));
		item.setDuration((Long) attributes.get("duration"));
		item.setItemLimitCategory((Integer) attributes.get("ItemLimitCategory"));
		item.setHolidayId((Long) attributes.get("HolidayId"));
		item.setScriptName((String) attributes.get("ScriptName"));
		item.setDisenchantID((Integer) attributes.get("DisenchantID"));
		item.setFoodType((Integer) attributes.get("FoodType"));
		item.setMinMoneyLoot((Long) attributes.get("minMoneyLoot"));
		item.setMaxMoneyLoot((Long) attributes.get("maxMoneyLoot"));
		item.setFlagsCustom((Long) attributes.get("flagsCustom"));

		return item;
	}

	public static Map<String, Object> toMap(Item item) {
		Map<String, Object> attributes = new LinkedHashMap<>();

		attributes.put("entry", item.getEntry());
		attributes.put("class", item.getiClass());
		attributes.put("subclass", item.getSubclass());
		attributes.put("name", item.getName());
		attributes.put("displayid", item.getDisplayId());
		attributes.put("Quality", item.getQuality());
		attributes.put("Flags", item.getItemFlags());
		attributes.put("FlagsExtra", item.getItemFlagsExtra());
		attributes.put("BuyCount", item.getBuyCount());
		attributes.put("BuyPrice", item.getBuyPrice());
		attributes.put("SellPrice", item.getSellPrice());
		attributes.put("InventoryType", item.getInventoryType());
		attributes.put("AllowableClass", item.getAllowableClass());
		attributes.put("AllowableRace", item.getAllowableRace());
		attributes.put("ItemLevel", item.getItemLevel());
		attributes.put("RequiredLevel", item.getRequiredLevel());
		attributes.put("RequiredSkill", item.getRequiredSkill());
		attributes.put("RequiredSkillRank", item.getRequiredSkillRank());
		attributes.put("requiredspell", item.getRequiredSpell());
		attributes.put("requiredhonorrank", item.getRequiredHonorRank());
		attributes.put("RequiredCityRank", item.getRequiredCityRank());
		attributes.put("RequiredReputationFaction", item.getRequiredReputationFaction());
		attributes.put("RequiredReputationRank", item.getRequiredReputationRank());
		attributes.put("maxcount", item.getMaxCount());
		attributes.put("stackable", item.getStackable());
		attributes.put("ContainerSlots", item.getContainerSlots());
		attributes.put("StatsCount", item.getStatsCount());

		for(int i = 1; i <= item.getStats().size(); i++) {
			ItemStat stat = item.getStats().get(i - 1);
			attributes.put("stat_type" + i, stat.getType());
			attributes.put("stat_value" + i, stat.getValue());
		}

		attributes.put("ScalingStatDistribution", item.getScalingStatDistribution());
		attributes.put("ScalingStatValue", item.getScalingStatValue());
		attributes.put("dmg_min1", item.getDamageMinimum1());
		attributes.put("dmg_max1", item.getDamageMaximum1());
		attributes.put("dmg_type1", item.getDamageType1());
		attributes.put("dmg_min2", item.getDamageMinimum2());
		attributes.put("dmg_max2", item.getDamageMaximum2());
		attributes.put("dmg_type2", item.getDamageType2());
		attributes.put("armor", item.getArmor());

		Resistance res = item.getResistance();
		attributes.put("holy_res", res.getHoly());
		attributes.put("fire_res", res.getFire());
		attributes.put("nature_res", res.getNature());
		attributes.put("frost_res", res.getFrost());
		attributes.put("shadow_res", res.getShadow());
		attributes.put("arcane_res", res.getArcane());

		attributes.put("delay", item.getDelay());
		attributes.put("ammo_type", item.getAmmoType());
		attributes.put("RangedModRange", item.getRangedModRange());

		for(int i = 1; i <= item.getSpells().size(); i++) {
			ItemSpell spell = item.getSpells().get(i - 1);
			attributes.put("spellid_" + i, spell.getId());
			attributes.put("spelltrigger_" + i, spell.getTrigger());
			attributes.put("spellcharges_" + i, spell.getCharges());
			attributes.put("spellppmRate_" + i, spell.getPpmRate());
			attributes.put("spellcooldown_" + i, spell.getCooldown());
			attributes.put("spellcategory_" + i, spell.getCategory());
			attributes.put("spellcategorycooldown_" + i, spell.getCategoryCooldown());
		}

		attributes.put("bonding", item.getBonding());
		attributes.put("description", item.getDescription());
		attributes.put("PageText", item.getPage());
		attributes.put("LanguageID", item.getLanguageId());
		attributes.put("PageMaterial", item.getPageMaterial());
		attributes.put("startquest", item.getStartQuest());
		attributes.put("lockid", item.getLockId());
		attributes.put("Material", item.getMaterial());
		attributes.put("sheath", item.getSheath());
		attributes.put("RandomProperty", item.getRandomProperty());
		attributes.put("RandomSuffix", item.getRandomSuffix());
		attributes.put("block", item.getBlock());
		attributes.put("itemset", item.getItemSet());
		attributes.put("MaxDurability", item.getMaxDurability());
		attributes.put("area", item.getArea());
		attributes.put("Map", item.getMap());
		attributes.put("BagFamily", item.getBagFamily());
		attributes.put("TotemCategory", item.getTotemCategory());
		attributes.put("socketColor_1", item.getSocketColor_1());
		attributes.put("socketContent_1", item.getSocketContent_1());
		attributes.put("socketColor_2", item.getSocketColor_2());
		attributes.put("socketContent_2", item.getSocketContent_2());
		attributes.put("socketColor_3", item.getSocketColor_3());
		attributes.put("socketContent_3", item.getSocketContent_3());
		attributes.put("socketBonus", item.getSocketBonus());
		attributes.put("GemProperties", item.getGemProperties());
		attributes.put("RequiredDisenchantSkill", item.getRequiredDisenchantSkill());
		attributes.put("ArmorDamageModifier", item.getArmorDamageModifier());
		attributes.put("duration", item.getDuration());
		attributes.put("ItemLimitCategory", item.getItemLimitCategory());
		attributes.put("HolidayId", item.getHolidayId());
		attributes.put("ScriptName", item.getScriptName());
		attributes.put("DisenchantID", item.getDisenchantID());
		attributes.put("FoodType", item.getFoodType());
		attributes.put("minMoneyLoot", item.getMinMoneyLoot());
		attributes.put("maxMoneyLoot", item.getMaxMoneyLoot());
		attributes.put("flagsCustom", item.getFlagsCustom());

		return attributes;
	}

	private static Resistance getResistance(Map<String, Object> attributes,
	                                        String holyColumn, String fireColumn, String natureColumn,
	                                        String frostColumn, String shadowColumn, String arcaneColumn) {

		Resistance resistance = new Resistance();

		resistance.setHoly((Integer) attributes.get(holyColumn));
		resistance.setFire((Integer) attributes.get(fireColumn));
		resistance.setNature((Integer) attributes.get(natureColumn));
		resistance.setFrost((Integer) attributes.get(frostColumn));
		resistance.setShadow((Integer) attributes.get(shadowColumn));
		resistance.setArcane((Integer) attributes.get(arcaneColumn));

		return resistance;
	}
}