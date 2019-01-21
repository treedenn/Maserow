package me.heitx.maserow.database;

import java.util.Map;
import java.util.Set;

public interface ItemDAO {
	boolean insert(Map<String, Object> item);

	boolean update(Map<String, Object> item);

	boolean delete(int entry);

	Map<String, Object> get(int entry);

	Set<Map<String, Object>> getAll(int limit);

	Set<Map<String, Object>> search(int entry, String name, int limit);

	boolean exists(int entry);

	long getMaxEntry();
}