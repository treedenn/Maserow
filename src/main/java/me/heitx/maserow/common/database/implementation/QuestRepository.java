package me.heitx.maserow.common.database.implementation;

import me.heitx.maserow.common.database.Database;
import me.heitx.maserow.common.database.MySqlDatabase;
import me.heitx.maserow.common.database.repository.IQuestRepository;
import me.heitx.maserow.common.model.Quest;
import me.heitx.maserow.common.utils.ConverterUtil;
import me.heitx.maserow.common.utils.Queries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class QuestRepository extends MySqlDatabase implements IQuestRepository {
	private static final Class<Quest> TYPE = Quest.class;

	@Override
	public List<Quest> search(int entry, String logTitle, int limit) {
		List<Quest> list = new ArrayList<>();

		execute(Database.Selection.WORLD, conn -> {
			String query = Queries.Quest.search(false, entry, logTitle, limit);

			PreparedStatement ps = executeAndConvertRows(query, conn, TYPE, list);

			return new Statement[] { ps };
		});

		return list;
	}

	@Override
	public boolean insert(Quest quest) {
		AtomicBoolean atomic = new AtomicBoolean();

		execute(Database.Selection.WORLD, conn -> {
			String query = Queries.Quest.insert(false, ConverterUtil.toAttributes(quest));

			PreparedStatement ps = conn.prepareStatement(query);
			atomic.set(ps.executeUpdate() > 0);

			return new Statement[] { ps };
		});

		return atomic.get();
	}

	@Override
	public boolean update(Quest quest) {
		AtomicBoolean atomic = new AtomicBoolean();

		execute(Database.Selection.WORLD, conn -> {
			String query = Queries.Quest.update(false, ConverterUtil.toAttributes(quest));

			PreparedStatement ps = conn.prepareStatement(query);
			atomic.set(ps.executeUpdate() > 0);

			return new Statement[] { ps };
		});

		return atomic.get();
	}

	@Override
	public boolean delete(int entry) {
		AtomicBoolean atomic = new AtomicBoolean();

		execute(Database.Selection.WORLD, conn -> {
			String query = Queries.Quest.delete(false, entry);

			PreparedStatement ps = conn.prepareStatement(query);
			atomic.set(ps.executeUpdate() > 0);

			return new Statement[] { ps };
		});

		return atomic.get();
	}

	@Override
	public Quest get(int entry) {
		AtomicReference<Quest> atomic = new AtomicReference<>();

		execute(Database.Selection.WORLD, conn -> {
			String query = Queries.Quest.get(false, entry);

			PreparedStatement ps = executeAndConvertRow(query, conn, TYPE, atomic);

			return new Statement[] { ps };
		});

		return atomic.get();
	}

	@Override
	public List<Quest> getAll(int limit) {
		List<Quest> list = new ArrayList<>();

		execute(Database.Selection.WORLD, conn -> {
			String query = Queries.Quest.getAll(false, limit);

			PreparedStatement ps = executeAndConvertRows(query, conn, TYPE, list);

			return new Statement[] { ps };
		});

		return list;
	}

	@Override
	public boolean exists(int entry) {
		AtomicBoolean atomic = new AtomicBoolean();

		execute(Database.Selection.WORLD, conn -> {
			String query = Queries.Quest.exists(false, entry);

			PreparedStatement ps = conn.prepareStatement(query);

			ResultSet rs = ps.executeQuery();
			atomic.set(rs.next() && rs.getBoolean(1));

			return new Statement[] { ps };
		});

		return atomic.get();
	}

	@Override
	public long getMaxEntry() {
		AtomicLong atomic = new AtomicLong(-1);

		execute(Database.Selection.WORLD, conn -> {
			String query = Queries.Quest.getMaxEntry(false);

			PreparedStatement ps = conn.prepareStatement(query);

			ResultSet rs = ps.executeQuery();
			if(rs.next()) atomic.set(rs.getLong(1));

			return new Statement[] { ps };
		});

		return atomic.get();
	}
}