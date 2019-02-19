package me.heitx.maserow.database.dao;

import java.util.Map;

public interface IQuestAddonDAO {
	boolean insert(Map<String, Object> addon);

	boolean update(Map<String, Object> addon);

	boolean delete(int entry);

	Map<String, Object> get(int entry);

	boolean exists(int entry);
}