package me.heitx.maserow.database.wrappers;

import me.heitx.maserow.database.IClient;
import me.heitx.maserow.database.MySqlDatabase;
import me.heitx.maserow.database.dao.IItemDAO;
import me.heitx.maserow.utils.Queries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class ItemDAO extends MySqlDatabase implements IItemDAO {
	public ItemDAO(IClient client) {
		super(client, client.getWorld());
	}

	@Override
	public List<Map<String, Object>> search(int entry, String name, int limit) {
		List<Map<String, Object>> set = new ArrayList<>();

		// TODO: Fix all the DAOs

		try {
			execute(conn -> {
				PreparedStatement ps = conn.prepareStatement(Queries.Item.search(false, entry, name, limit));
				ResultSet rs = ps.executeQuery();

				while(rs.next()) {
					set.add(convertResultSet(rs));
				}

				return ps;
			});
		} catch(SQLException e) {
			e.printStackTrace();
		}

		return set;
	}

	@Override
	public boolean insert(Map<String, Object> item) {
		AtomicBoolean atomic = new AtomicBoolean();

		try {
			execute(conn -> {
				PreparedStatement ps = conn.prepareStatement(Queries.Item.insert(false, item));
				atomic.set(ps.executeUpdate() > 0);

				return ps;
			});
		} catch(SQLException e) {
			e.printStackTrace();
		}

		return atomic.get();
	}

	@Override
	public boolean update(Map<String, Object> item) {
		AtomicBoolean atomic = new AtomicBoolean();

		try {
			execute(conn -> {
				PreparedStatement ps = conn.prepareStatement(Queries.Item.update(false, item));
				atomic.set(ps.executeUpdate() > 0);

				return ps;
			});
		} catch(SQLException e) {
			e.printStackTrace();
		}

		return atomic.get();
	}

	@Override
	public boolean delete(int entry) {
		AtomicBoolean atomic = new AtomicBoolean();

		try {
			execute(conn -> {
				PreparedStatement ps = conn.prepareStatement(Queries.Item.delete(false, entry));
				atomic.set(ps.executeUpdate() > 0);

				return ps;
			});
		} catch(SQLException e) {
			e.printStackTrace();
		}

		return atomic.get();
	}

	@Override
	public Map<String, Object> get(int entry) {
		AtomicReference<Map<String, Object>> atomic = new AtomicReference<>();

		try {
			execute(conn -> {
				PreparedStatement ps = conn.prepareStatement(Queries.Item.get(false, entry));
				ResultSet rs = ps.executeQuery();

				if(rs.next()) {
					atomic.set(convertResultSet(rs));
				}

				return ps;
			});
		} catch(SQLException e) {
			e.printStackTrace();
		}

		return atomic.get();
	}

	@Override
	public List<Map<String, Object>> getAll(int limit) {
		List<Map<String, Object>> set = new ArrayList<>();

		try {
			execute(conn -> {
				PreparedStatement ps = conn.prepareStatement(Queries.Item.getAll(false, limit));
				ResultSet rs = ps.executeQuery();

				while(rs.next()) {
					set.add(convertResultSet(rs));
				}

				return ps;
			});
		} catch(SQLException e) {
			e.printStackTrace();
		}

		return set;
	}

	@Override
	public boolean exists(int entry) {
		AtomicBoolean atomic = new AtomicBoolean();

		try {
			execute(conn -> {
				PreparedStatement ps = conn.prepareStatement(Queries.Item.exists(false, entry));

				ResultSet rs = ps.executeQuery();
				atomic.set(rs.next() && rs.getBoolean(1));

				return ps;
			});
		} catch(SQLException e) {
			e.printStackTrace();
		}

		return atomic.get();
	}

	@Override
	public long getMaxEntry() {
		AtomicLong atomic = new AtomicLong(-1);

		try {
			execute(conn -> {
				PreparedStatement ps = conn.prepareStatement(Queries.Item.getMaxEntry(false));

				ResultSet rs = ps.executeQuery();
				if(rs.next()) atomic.set(rs.getLong(1));

				return ps;
			});
		} catch(SQLException e) {
			e.printStackTrace();
		}

		return atomic.get();
	}
}