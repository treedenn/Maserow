package me.heitx.maserow.common.database.implementation;

import me.heitx.maserow.common.database.Database;
import me.heitx.maserow.common.database.MySqlDatabase;
import me.heitx.maserow.common.database.repository.IItemRepository;
import me.heitx.maserow.common.model.Item;
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

public class ItemRepository extends MySqlDatabase implements IItemRepository {
	private static final Class<Item> TYPE = Item.class;

	@Override
	public List<Item> search(int entry, String name, int limit) {
		List<Item> list = new ArrayList<>();

		execute(Database.Selection.WORLD, conn -> {
			String query = Queries.Item.search(false, entry, name, limit);

			PreparedStatement ps = executeAndConvertRows(query, conn, TYPE, list);

			return new Statement[] { ps };
		});

		return list;
	}

	@Override
	public boolean insert(Item item) {
		AtomicBoolean atomic = new AtomicBoolean();

		execute(Database.Selection.WORLD, conn -> {
			String query = Queries.Item.insert(false, ConverterUtil.toAttributes(item));

			PreparedStatement ps = conn.prepareStatement(query);
			atomic.set(ps.executeUpdate() > 0);

			return new Statement[] { ps };
		});

		return atomic.get();
	}

	@Override
	public boolean update(Item item) {
		AtomicBoolean atomic = new AtomicBoolean();

		execute(Database.Selection.WORLD, conn -> {
			String query = Queries.Item.update(false, ConverterUtil.toAttributes(item));

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
			String query = Queries.Item.delete(false, entry);

			PreparedStatement ps = conn.prepareStatement(query);
			atomic.set(ps.executeUpdate() > 0);

			return new Statement[] { ps };
		});

		return atomic.get();
	}

	@Override
	public Item get(int entry) {
		AtomicReference<Item> atomic = new AtomicReference<>();

		execute(Database.Selection.WORLD, conn -> {
			String query = Queries.Item.get(false, entry);

			PreparedStatement ps = executeAndConvertRow(query, conn, TYPE, atomic);

			return new Statement[] { ps };
		});

		return atomic.get();
	}

	@Override
	public List<Item> getAll(int limit) {
		List<Item> list = new ArrayList<>();

		execute(Database.Selection.WORLD, conn -> {
			String query = Queries.Item.getAll(false, limit);

			PreparedStatement ps = executeAndConvertRows(query, conn, TYPE, list);

			return new Statement[] { ps };
		});

		return list;
	}

	@Override
	public boolean exists(int entry) {
		AtomicBoolean atomic = new AtomicBoolean();

		execute(Database.Selection.WORLD, conn -> {
			String query = Queries.Item.exists(false, entry);

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
			String query = Queries.Item.getMaxEntry(false);

			PreparedStatement ps = conn.prepareStatement(query);

			ResultSet rs = ps.executeQuery();
			if(rs.next()) atomic.set(rs.getLong(1));

			return new Statement[] { ps };
		});

		return atomic.get();
	}
}