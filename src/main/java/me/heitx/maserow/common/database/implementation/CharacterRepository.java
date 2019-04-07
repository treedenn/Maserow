package me.heitx.maserow.common.database.implementation;

import me.heitx.maserow.common.database.Database;
import me.heitx.maserow.common.database.MySqlDatabase;
import me.heitx.maserow.common.database.repository.ICharacterRepository;
import me.heitx.maserow.common.model.Character;
import me.heitx.maserow.common.utils.ConverterUtil;
import me.heitx.maserow.common.utils.Queries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class CharacterRepository extends MySqlDatabase implements ICharacterRepository {
	private static final Class<Character> TYPE = Character.class;

	@Override
	public List<Character> search(int guid, String name, int[] raceIds, int[] classIds, int limit) {
		List<Character> list = new ArrayList<>();

		execute(Database.Selection.CHARACTERS, conn -> {
			String query = Queries.Character.search(false, guid, name, raceIds, classIds, limit);

			PreparedStatement ps = executeAndConvertRows(query, conn, TYPE, list);

			return new Statement[] { ps };
		});

		return list;
	}

	@Override
	public boolean insert(Character character) {
		AtomicBoolean atomic = new AtomicBoolean();

		execute(Database.Selection.CHARACTERS, conn -> {
			String query = Queries.Character.insert(false, ConverterUtil.toAttributes(character));

			PreparedStatement ps = conn.prepareStatement(query);
			atomic.set(ps.executeUpdate() > 0);

			return new Statement[] { ps };
		});

		return atomic.get();
	}

	@Override
	public boolean update(Character character) {
		AtomicBoolean atomic = new AtomicBoolean();

		execute(Database.Selection.CHARACTERS, conn -> {
			String query = Queries.Character.update(false, ConverterUtil.toAttributes(character));

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
			String query = Queries.Character.delete(false, entry);

			PreparedStatement ps = conn.prepareStatement(query);
			atomic.set(ps.executeUpdate() > 0);

			return new Statement[] { ps };
		});

		return atomic.get();
	}

	@Override
	public Character get(int entry) {
		AtomicReference<Character> atomic = new AtomicReference<>();

		execute(Database.Selection.CHARACTERS, conn -> {
			String query = Queries.Character.get(false, entry);

			PreparedStatement ps = executeAndConvertRow(query, conn, TYPE, atomic);

			return new Statement[] { ps };
		});

		return atomic.get();
	}

	@Override
	public List<Character> getAll(int limit) {
		List<Character> list = new ArrayList<>();

		execute(Database.Selection.CHARACTERS, conn -> {
			String query = Queries.Character.getAll(false, limit);

			PreparedStatement ps = executeAndConvertRows(query, conn, TYPE, list);

			return new Statement[] { ps };
		});

		return list;
	}

	@Override
	public boolean exists(int entry) {
		AtomicBoolean atomic = new AtomicBoolean();

		execute(Database.Selection.CHARACTERS, conn -> {
			String query = Queries.Character.exists(false, entry);

			PreparedStatement ps = conn.prepareStatement(query);

			ResultSet rs = ps.executeQuery();
			atomic.set(rs.next() && rs.getBoolean(1));

			return new Statement[] { ps };
		});

		return atomic.get();
	}
}