package me.heitx.maserow.database.dao;

import java.util.List;
import java.util.Map;

public interface ICharacter extends BaseDAO {
	List<Map<String, Object>> search(int entry, String name, int[] raceIds, int[] classIds, int limit);
}
