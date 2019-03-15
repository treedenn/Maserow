package me.heitx.maserow.database.dao;

import java.util.List;
import java.util.Map;

public interface IItemInstance extends BaseDAO {
	List<Map<String, Object>> search(long guid, int itemEntry, long owner, int limit);
}
