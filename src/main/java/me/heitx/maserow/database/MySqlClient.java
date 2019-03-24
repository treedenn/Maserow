package me.heitx.maserow.database;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class MySqlClient implements IClient {
	private MysqlDataSource ds;
	private String auth;
	private String characters;
	private String world;

	public MySqlClient() {
		this("127.0.0.1",
				"root",
				"toor",
				3306,
				"auth",
				"characters",
				"world");
	}

	public MySqlClient(String hostname, String username, String password, int port, String auth, String characters, String world) {
		ds = new MysqlDataSource();

		try {
			ds.setUseSSL(false);
			ds.setServerTimezone("UTC");
		} catch(SQLException e) {
			e.printStackTrace();
		}

		ds.setServerName(hostname);
		ds.setUser(username);
		ds.setPassword(password);
		ds.setPort(port);
		this.auth = auth;
		this.characters = characters;
		this.world = world;
	}

	@Override
	public String getHostname() {
		return ds.getServerName();
	}

	@Override
	public String getUsername() {
		return ds.getUser();
	}

	@Override
	public String getPassword() {
		return ds.getPassword();
	}

	@Override
	public int getPort() {
		return ds.getPort();
	}

	@Override
	public String getAuth() {
		return auth;
	}

	@Override
	public String getCharacters() {
		return characters;
	}

	@Override
	public String getWorld() {
		return world;
	}

	@Override
	public void setDatabase(String database) {
		ds.setDatabaseName(database);
	}

	@Override
	public Connection getConnection(Database.Selection selection) throws SQLException {
		switch(selection) {
			case AUTH: ds.setDatabaseName(auth); break;
			case CHARACTERS: ds.setDatabaseName(characters); break;
			case WORLD: ds.setDatabaseName(world); break;
		}

		return ds.getConnection();
	}
}