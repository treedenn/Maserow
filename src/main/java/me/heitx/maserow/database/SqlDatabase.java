package me.heitx.maserow.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public abstract class SqlDatabase {
	private static final Logger LOGGER = LogManager.getLogger(SqlDatabase.class);

	private IClient client;
	private String database;

	public SqlDatabase(IClient client, String database) {
		this.client = client;
		this.database = database;
	}

	public synchronized void execute(IConnectionCallback callback) throws SQLException {
		client.setDatabase(database);
		Connection conn = client.getConnection();
		conn.setAutoCommit(false);

		try {
			PreparedStatement ps = callback.execute(conn);
			String query = ps.toString().substring(ps.getClass().getName().length() + 2);
			LOGGER.info("Executed Query: " + query);
			conn.commit();
		} catch(SQLException e) {
			conn.rollback();
			LOGGER.error("SQLException: " + e.getMessage());
			e.printStackTrace();
		}

		conn.close();
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
}