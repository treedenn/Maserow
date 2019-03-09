package me.heitx.maserow.database.trinitywotlk;

import me.heitx.maserow.database.IClient;
import me.heitx.maserow.database.SqlDatabase;
import me.heitx.maserow.database.dao.ICreatureDAO;
import me.heitx.maserow.query.Query;
import me.heitx.maserow.utils.query.TrinityCreatureQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class CreatureDAO extends SqlDatabase implements ICreatureDAO {
	public CreatureDAO(IClient client) {
		super(client, client.getWorld());
	}

	@Override
	public boolean insert(Map<String, Object> map) {
		AtomicBoolean atomic = new AtomicBoolean();

		try {
			execute(conn -> {
				PreparedStatement ps = conn.prepareStatement(TrinityCreatureQuery.getInsertQuery(map, false));
				atomic.set(ps.executeUpdate() > 0);

				return ps;
			});
		} catch(SQLException e) {
			e.printStackTrace();
		}

		return atomic.get();
	}

	@Override
	public boolean update(Map<String, Object> map) {
		AtomicBoolean atomic = new AtomicBoolean();

		try {
			execute(conn -> {
				PreparedStatement ps = conn.prepareStatement(TrinityCreatureQuery.getUpdateQuery(map, false));
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
				PreparedStatement ps = conn.prepareStatement(TrinityCreatureQuery.getDeleteQuery(entry, false));
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
				Query query = new Query()
						.select("*")
						.from(TrinityCreatureQuery.TEMPLATE_TABLE)
						.where("entry = " + entry)
						.limit(1);

				PreparedStatement ps = conn.prepareStatement(query.buildSQL());
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
				Query query = new Query()
						.select("*")
						.from(TrinityCreatureQuery.TEMPLATE_TABLE)
						.limit(limit);

				PreparedStatement ps = conn.prepareStatement(query.buildSQL());
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
	public List<Map<String, Object>> search(int entry, String name, int limit) {
		List<Map<String, Object>> set = new ArrayList<>();

		try {
			execute(conn -> {
				Query query = new Query()
						.select("*")
						.from(TrinityCreatureQuery.TEMPLATE_TABLE);

				if(entry != 0) {
					query.where("entry = " + entry);
				}
				if(!name.isEmpty()) {
					query.or("name LIKE '%" + name + "%'");
				}

				PreparedStatement ps = conn.prepareStatement(query.limit(limit).buildSQL());
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
				PreparedStatement ps = conn.prepareStatement("SELECT EXISTS(SELECT 0 FROM " +
						TrinityCreatureQuery.TEMPLATE_TABLE + " WHERE entry = ?);");
				ps.setLong(1, entry);

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
				PreparedStatement ps = conn.prepareStatement(
						new Query().select("MAX(entry)")
								.from(TrinityCreatureQuery.TEMPLATE_TABLE)
								.buildSQL());

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