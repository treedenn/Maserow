package me.heitx.maserow.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class SqlDatabase {
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
			callback.execute(conn);
			conn.commit();
		} catch(SQLException e) {
			conn.rollback();
			e.printStackTrace();
		}

		conn.close();
	}

	public Map<String, Object> convertResultSet(ResultSet rs) throws SQLException {
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