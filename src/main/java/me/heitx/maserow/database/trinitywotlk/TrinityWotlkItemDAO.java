package me.heitx.maserow.database.trinitywotlk;

import me.heitx.maserow.database.IClient;
import me.heitx.maserow.database.SqlDatabase;
import me.heitx.maserow.database.dao.ItemDAO;
import me.heitx.maserow.query.Query;
import me.heitx.maserow.query.TrinityItemQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class TrinityWotlkItemDAO extends SqlDatabase implements ItemDAO {
	public TrinityWotlkItemDAO(IClient client) {
		super(client, client.getWorld());
	}

	public TrinityWotlkItemDAO(IClient client, String database) {
		super(client, database);
	}

	@Override
	public boolean insert(Map<String, Object> item) {
		AtomicBoolean atomic = new AtomicBoolean();

		try {
			execute(conn -> {
				PreparedStatement ps = conn.prepareStatement(TrinityItemQuery.getInsertItemQuery(item, true));
				atomic.set(ps.executeUpdate() > 0);
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
				PreparedStatement ps = conn.prepareStatement(TrinityItemQuery.getUpdateItemQuery(item, true));
				atomic.set(ps.executeUpdate() > 0);
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
				PreparedStatement ps = conn.prepareStatement(TrinityItemQuery.getDeleteItemQuery(entry, true));
				atomic.set(ps.executeUpdate() > 0);
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
				Query query = new Query()
						.newlineFormat(true)
						.select("*")
						.from("item_template")
						.where("entry = " + entry)
						.limit(1);

				PreparedStatement ps = conn.prepareStatement(query.buildSQL());
				ResultSet rs = ps.executeQuery();

				if(rs.next()) {
					atomic.set(convertResultSet(rs));
				}
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
				Query query = new Query()
						.newlineFormat(true)
						.select("*")
						.from("item_template")
						.limit(limit);

				PreparedStatement ps = conn.prepareStatement(query.buildSQL());
				ResultSet rs = ps.executeQuery();

				while(rs.next()) {
					set.add(convertResultSet(rs));
				}
			});
		} catch(SQLException e) {
			e.printStackTrace();
		}

		return set;
	}

	@Override
	public List<Map<String, Object>> search(int entry, String name, int limit) {
		List<Map<String, Object>> set = new ArrayList<>();

		try {
			execute(conn -> {
				Query query = new Query()
						.newlineFormat(true)
						.select("*")
						.from("item_template")
						.where("entry = " + entry)
						.or("name LIKE '%" + name + "%'")
						.limit(limit);

				PreparedStatement ps = conn.prepareStatement(query.buildSQL());
				ResultSet rs = ps.executeQuery();

				while(rs.next()) {
					set.add(convertResultSet(rs));
				}
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
				PreparedStatement ps = conn.prepareStatement("SELECT EXISTS(SELECT 0 FROM item_template WHERE entry = ?);");
				ps.setLong(1, entry);

				ResultSet rs = ps.executeQuery();
				atomic.set(rs.next() && rs.getBoolean(1));
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
				PreparedStatement ps = conn.prepareStatement(
						new Query().select("MAX(entry)")
								.from("item_template")
								.buildSQL());

				ResultSet rs = ps.executeQuery();
				if(rs.next()) atomic.set(rs.getLong(1));
			});
		} catch(SQLException e) {
			e.printStackTrace();
		}

		return atomic.get();
	}
}