package me.heitx.maserow.database;

import me.heitx.maserow.database.function.BiFunction;
import me.heitx.maserow.database.function.Function;
import me.heitx.maserow.database.function.TriFunction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MySqlDatabase {
	private static final Logger LOGGER = LogManager.getLogger(MySqlDatabase.class);

	private IClient client;

	public MySqlDatabase(IClient client) {
		this.client = client;
	}

	public void execute(Database.Selection db, Function<Connection, Statement[]> callback) {
		try {
			Connection conn = client.getConnection(db);
			execute(callback.apply(conn), conn);
		} catch(SQLException e) {
			LOGGER.error("SQLException at connection: " + e.getMessage());
		}
	}

	public void execute(Database.Selection db1, Database.Selection db2, BiFunction<Connection, Connection, Statement[]> callback) {
		if(db1 == null || db2 == null || db1 == db2) {
			LOGGER.error("Database Selection for execute queries are either null or the same. Contact developers.");
			return;
		}

		try {
			Connection conn1 = client.getConnection(db1);
			Connection conn2 = client.getConnection(db2);

			execute(callback.apply(conn1, conn2), conn1, conn2);
		} catch(SQLException e) {
			LOGGER.error("SQLException at connection: " + e.getMessage());
		}
	}

	public void execute(TriFunction<Connection, Connection, Connection, Statement[]> callback) {
		try {
			Connection aConn = client.getConnection(Database.Selection.AUTH);
			Connection cConn = client.getConnection(Database.Selection.CHARACTERS);
			Connection wConn = client.getConnection(Database.Selection.WORLD);

			execute(callback.apply(aConn, cConn, wConn), aConn, cConn, wConn);
		} catch(SQLException e) {
			LOGGER.error("SQLException at connection: " + e.getMessage());
		}
	}

	private void execute(Statement[] statements, Connection ... conns) throws SQLException {
		for(Connection conn : conns) conn.setAutoCommit(false);

		try {
			if(statements != null) {
				for(Statement p : statements) {
					String query = p.toString().substring(p.getClass().getName().length() + 2);
					LOGGER.info(p.getConnection().getCatalog() + " - Executed Query: " + query);
				}
			}

			for(Connection conn : conns) conn.commit();
		} catch(SQLException e) {
			for(Connection conn : conns) conn.rollback();

			LOGGER.error("SQLException when executing queries: " + e.getMessage());
		} finally {
			for(Connection conn : conns) conn.close();
		}
	}

	protected Map<String, Object> convertResultSet(ResultSet rs) throws SQLException {
		Map<String, Object> map = new HashMap<>();

		ResultSetMetaData rsmd = rs.getMetaData();
		int columnAmount = rsmd.getColumnCount();

		for(int i = 1; i <= columnAmount; i++) {
			String column = rsmd.getColumnName(i);
			map.put(column, rs.getObject(column));
		}

		return map;
	}

	protected PreparedStatement executeAndConvertResultToSet(List<Map<String, Object>> set, Connection conn, String query) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();

		while(rs.next()) {
			set.add(convertResultSet(rs));
		}

		return ps;
	}
}