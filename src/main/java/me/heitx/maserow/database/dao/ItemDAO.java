package me.heitx.maserow.database.dao;

import java.util.List;
import java.util.Map;

public interface ItemDAO {
	boolean insert(Map<String, Object> item);

	boolean update(Map<String, Object> item);

	boolean delete(int entry);

	Map<String, Object> get(int entry);

	List<Map<String, Object>> getAll(int limit);

	List<Map<String, Object>> search(int entry, String name, int limit);

	boolean exists(int entry);

	long getMaxEntry();
}