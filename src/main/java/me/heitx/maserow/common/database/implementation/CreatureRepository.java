package me.heitx.maserow.common.database.implementation;

import me.heitx.maserow.common.database.Database;
import me.heitx.maserow.common.database.MySqlDatabase;
import me.heitx.maserow.common.database.repository.ICreatureRepository;
import me.heitx.maserow.common.model.Creature;
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

public class CreatureRepository extends MySqlDatabase implements ICreatureRepository {
	private static final Class<Creature> TYPE = Creature.class;

	@Override
	public List<Creature> search(int entry, String name, int limit) {
		List<Creature> list = new ArrayList<>();

		execute(Database.Selection.WORLD, conn -> {
			String query = Queries.Creature.search(false, entry, name, limit);

			PreparedStatement ps = executeAndConvertRows(query, conn, TYPE, list);

			return new Statement[] { ps };
		});

		return list;
	}

	@Override
	public boolean insert(Creature creature) {
		AtomicBoolean atomic = new AtomicBoolean();

		execute(Database.Selection.WORLD, conn -> {
			String query = Queries.Creature.insert(false, ConverterUtil.toAttributes(creature));

			PreparedStatement ps = conn.prepareStatement(query);
			atomic.set(ps.executeUpdate() > 0);

			return new Statement[] { ps };
		});

		return atomic.get();
	}

	@Override
	public boolean update(Creature creature) {
		AtomicBoolean atomic = new AtomicBoolean();

		execute(Database.Selection.WORLD, conn -> {
			String query = Queries.Creature.update(false, ConverterUtil.toAttributes(creature));

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
			String query = Queries.Creature.delete(false, entry);

			PreparedStatement ps = conn.prepareStatement(query);
			atomic.set(ps.executeUpdate() > 0);

			return new Statement[] { ps };
		});

		return atomic.get();
	}

	@Override
	public Creature get(int entry) {
		AtomicReference<Creature> atomic = new AtomicReference<>();

		execute(Database.Selection.WORLD, conn -> {
			String query = Queries.Creature.get(false, entry);

			PreparedStatement ps = executeAndConvertRow(query, conn, TYPE, atomic);

			return new Statement[] { ps };
		});

		return atomic.get();
	}

	@Override
	public List<Creature> getAll(int limit) {
		List<Creature> list = new ArrayList<>();

		execute(Database.Selection.WORLD, conn -> {
			String query = Queries.Creature.getAll(false, limit);

			PreparedStatement ps = executeAndConvertRows(query, conn, TYPE, list);

			return new Statement[] { ps };
		});

		return list;
	}

	@Override
	public boolean exists(int entry) {
		AtomicBoolean atomic = new AtomicBoolean();

		execute(Database.Selection.WORLD, conn -> {
			PreparedStatement ps = conn.prepareStatement(Queries.Creature.exists(false, entry));

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
			PreparedStatement ps = conn.prepareStatement(Queries.Creature.getMaxEntry(false));

			ResultSet rs = ps.executeQuery();
			if(rs.next()) atomic.set(rs.getLong(1));

			return new Statement[] { ps };
		});

		return atomic.get();
	}
}