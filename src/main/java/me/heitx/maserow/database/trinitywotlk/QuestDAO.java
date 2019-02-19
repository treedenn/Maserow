package me.heitx.maserow.database.trinitywotlk;

import me.heitx.maserow.database.IClient;
import me.heitx.maserow.database.SqlDatabase;
import me.heitx.maserow.database.dao.IQuestDAO;

import java.util.List;
import java.util.Map;

public class QuestDAO extends SqlDatabase implements IQuestDAO {
	public QuestDAO(IClient client) {
		super(client, client.getWorld());
	}

	@Override
	public boolean insert(Map<String, Object> quest) {
		return false;
	}

	@Override
	public boolean update(Map<String, Object> quest) {
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
	public List<Map<String, Object>> search(int entry, String name, int limit) {
		return null;
	}

	@Override
	public boolean exists(int entry) {
		return false;
	}

	@Override
	public long getMaxEntry() {
		return 0;
	}
}