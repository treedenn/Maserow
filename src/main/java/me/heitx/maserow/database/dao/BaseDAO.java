package me.heitx.maserow.database.dao;

import java.util.List;
import java.util.Map;

public interface BaseDAO {
	boolean insert(Map<String, Object> map);

	boolean update(Map<String, Object> map);

	boolean delete(int entry);

	Map<String, Object> get(int entry);

	List<Map<String, Object>> getAll(int limit);

	boolean exists(int entry);
}