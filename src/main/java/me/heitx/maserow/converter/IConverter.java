package me.heitx.maserow.converter;

import me.heitx.maserow.model.Item;
import me.heitx.maserow.model.Quest;

import java.util.List;
import java.util.Map;

public interface IConverter {
	List<Item> toItems(List<Map<String, Object>> attributesList);
	Item toItem(Map<String, Object> attributes);
	Map<String, Object> toMap(Item item);

	Quest toQuest(Map<String, Object> attributesList);
}