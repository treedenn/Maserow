package me.heitx.maserow.database.wrappers;

import me.heitx.maserow.database.IClient;
import me.heitx.maserow.database.MySqlDatabase;
import me.heitx.maserow.database.dao.ICharacter;
import me.heitx.maserow.utils.Queries;
import me.heitx.maserow.utils.QueryUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class CharacterDAO extends MySqlDatabase implements ICharacter {
	public CharacterDAO(IClient client) {
		super(client, client.getAuth());
	}

	private static final String CHARACTER_TABLE = "Characters";

	@Override
	public List<Map<String, Object>> search(int guid, String name, int[] raceIds, int[] classIds, int limit) {
		List<Map<String, Object>> set = new ArrayList<>();

		try {
			execute(conn -> {
				PreparedStatement ps = conn.prepareStatement(Queries.Character.search(false, guid, name, raceIds, classIds, limit));
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
	public boolean insert(Map<String, Object> map) {
		AtomicBoolean atomic = new AtomicBoolean();

		try {
			execute(conn -> {
				PreparedStatement ps = conn.prepareStatement(Queries.Character.insert(false, map));
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
				PreparedStatement ps = conn.prepareStatement(Queries.Character.update(false, map));
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
				PreparedStatement ps = conn.prepareStatement(Queries.Character.delete(false, entry));
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
				PreparedStatement ps = conn.prepareStatement(Queries.Character.get(false, entry));
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
				PreparedStatement ps = conn.prepareStatement(Queries.Character.getAll(false, limit));
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
				PreparedStatement ps = conn.prepareStatement(Queries.Character.exists(false, entry));

				ResultSet rs = ps.executeQuery();
				atomic.set(rs.next() && rs.getBoolean(1));

				return ps;
			});
		} catch(SQLException e) {
			e.printStackTrace();
		}

		return atomic.get();
	}
}