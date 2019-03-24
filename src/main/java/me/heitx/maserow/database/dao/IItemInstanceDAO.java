package me.heitx.maserow.database.dao;

import java.util.List;
import java.util.Map;

public interface IItemInstanceDAO extends BaseDAO {
	List<Map<String, Object>> search(long guid, int itemEntry, long ownerGuid, long creatorGuid, int limit);
}
