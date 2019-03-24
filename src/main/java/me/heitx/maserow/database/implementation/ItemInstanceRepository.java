package me.heitx.maserow.database.implementation;

import me.heitx.maserow.database.Database;
import me.heitx.maserow.database.IClient;
import me.heitx.maserow.database.MySqlDatabase;
import me.heitx.maserow.database.repository.IItemInstanceRepository;
import me.heitx.maserow.model.ItemInstance;
import me.heitx.maserow.utils.ConverterUtil;
import me.heitx.maserow.utils.Queries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class ItemInstanceRepository extends MySqlDatabase implements IItemInstanceRepository {
	private static final Class<ItemInstance> TYPE = ItemInstance.class;

	public ItemInstanceRepository(IClient client) {
		super(client);
	}

	@Override
	public List<ItemInstance> search(long guid, int itemEntry, long ownerGuid, long creatorGuid, int limit) {
		List<ItemInstance> list = new ArrayList<>();

		execute(Database.Selection.CHARACTERS, conn -> {
			String query = Queries.ItemInstance.search(false, guid, itemEntry, ownerGuid, creatorGuid, limit);

			PreparedStatement ps = executeAndConvertRows(query, conn, TYPE, list);

			return new Statement[] { ps };
		});

		return list;
	}

	@Override
	public boolean insert(ItemInstance itemInstance) {
		AtomicBoolean atomic = new AtomicBoolean();

		execute(Database.Selection.CHARACTERS, conn -> {
			String query = Queries.ItemInstance.insert(false, ConverterUtil.toAttributes(itemInstance));

			PreparedStatement ps = conn.prepareStatement(query);
			atomic.set(ps.executeUpdate() > 0);

			return new Statement[] { ps };
		});

		return atomic.get();
	}

	@Override
	public boolean update(ItemInstance itemInstance) {
		AtomicBoolean atomic = new AtomicBoolean();

		execute(Database.Selection.CHARACTERS, conn -> {
			String query = Queries.ItemInstance.update(false, ConverterUtil.toAttributes(itemInstance));

			PreparedStatement ps = conn.prepareStatement(query);
			atomic.set(ps.executeUpdate() > 0);

			return new Statement[] { ps };
		});

		return atomic.get();
	}

	@Override
	public boolean delete(int entry) {
		AtomicBoolean atomic = new AtomicBoolean();

		execute(Database.Selection.CHARACTERS, conn -> {
			String query = Queries.ItemInstance.delete(false, entry);

			PreparedStatement ps = conn.prepareStatement(query);
			atomic.set(ps.executeUpdate() > 0);

			return new Statement[] { ps };
		});

		return atomic.get();
	}

	@Override
	public ItemInstance get(int entry) {
		AtomicReference<ItemInstance> atomic = new AtomicReference<>();

		execute(Database.Selection.CHARACTERS, conn -> {
			String query = Queries.ItemInstance.get(false, entry);

			PreparedStatement ps = executeAndConvertRow(query, conn, TYPE, atomic);

			return new Statement[] { ps };
		});

		return atomic.get();
	}

	@Override
	public List<ItemInstance> getAll(int limit) {
		List<ItemInstance> list = new ArrayList<>();

		execute(Database.Selection.CHARACTERS, conn -> {
			String query = Queries.ItemInstance.getAll(false, limit);

			PreparedStatement ps = executeAndConvertRows(query, conn, TYPE, list);

			return new Statement[] { ps };
		});

		return list;
	}

	@Override
	public boolean exists(int entry) {
		AtomicBoolean atomic = new AtomicBoolean();

		execute(Database.Selection.CHARACTERS, conn -> {
			String query = Queries.ItemInstance.exists(false, entry);

			PreparedStatement ps = conn.prepareStatement(query);

			ResultSet rs = ps.executeQuery();
			atomic.set(rs.next() && rs.getBoolean(1));

			return new Statement[] { ps };
		});

		return atomic.get();
	}
}