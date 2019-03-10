package me.heitx.maserow.database.trinitywotlk;

import me.heitx.maserow.database.IClient;
import me.heitx.maserow.database.SqlDatabase;
import me.heitx.maserow.database.dao.IQuestDAO;
import me.heitx.maserow.query.Query;
import me.heitx.maserow.utils.query.TrinityQuestQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class QuestDAO extends SqlDatabase implements IQuestDAO {
	public QuestDAO(IClient client) {
		super(client, client.getWorld());
	}

	@Override
	public boolean insert(Map<String, Object> map) {
		AtomicBoolean atomic = new AtomicBoolean();

		try {
			execute(conn -> {
				PreparedStatement ps = conn.prepareStatement(TrinityQuestQuery.getInsertQuery(map, false));
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
				PreparedStatement ps = conn.prepareStatement(TrinityQuestQuery.getUpdateQuery(map, false));
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
				PreparedStatement ps = conn.prepareStatement(TrinityQuestQuery.getDeleteQuery(entry, false));
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
						.from(TrinityQuestQuery.TEMPLATE_TABLE)
						.where("ID = " + entry)
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
						.from(TrinityQuestQuery.TEMPLATE_TABLE)
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
	public List<Map<String, Object>> search(int entry, String logTitle, int limit) {
		List<Map<String, Object>> set = new ArrayList<>();

		try {
			execute(conn -> {
				Query query = new Query()
						.select("*")
						.from(TrinityQuestQuery.TEMPLATE_TABLE);

				if(entry != 0) {
					query.where("ID = " + entry);
				}
				if(!logTitle.isEmpty()) {
					query.or("LogTitle LIKE '%" + logTitle + "%'");
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
						TrinityQuestQuery.TEMPLATE_TABLE + " WHERE ID = ?);");
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
						new Query().select("MAX(ID)")
								.from(TrinityQuestQuery.TEMPLATE_TABLE)
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