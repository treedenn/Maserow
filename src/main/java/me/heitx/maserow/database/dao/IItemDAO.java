package me.heitx.maserow.database.dao;

import java.util.List;
import java.util.Map;

public interface IItemDAO extends ExtendedDAO {
	List<Map<String, Object>> search(int entry, String name, int limit);
}