package me.heitx.maserow.database.wrappers;

import me.heitx.maserow.database.dao.IItemInstance;

import java.util.List;
import java.util.Map;

public class ItemInstanceDAO implements IItemInstance {
	@Override
	public List<Map<String, Object>> search(long guid, int itemEntry, long owner, int limit) {
		return null;
	}

	@Override
	public boolean insert(Map<String, Object> map) {
		return false;
	}

	@Override
	public boolean update(Map<String, Object> map) {
		return false;
	}

	@Override
	public boolean delete(int entry) {
		return false;
	}

	@Override
	public Map<String, Object> get(int entry) {
		return null;
	}

	@Override
	public List<Map<String, Object>> getAll(int limit) {
		return null;
	}

	@Override
	public boolean exists(int entry) {
		return false;
	}
}