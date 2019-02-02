package me.heitx.maserow.database.dao;

import java.util.List;
import java.util.Map;

public interface QuestDAO {
	boolean insert(Map<String, Object> quest);

	boolean update(Map<String, Object> quest);

	boolean delete(int entry);

	Map<String, Object> get(int entry);

	List<Map<String, Object>> getAll(int limit);

	List<Map<String, Object>> search(int entry, String name, int limit);

	boolean exists(int entry);

	long getMaxEntry();
}