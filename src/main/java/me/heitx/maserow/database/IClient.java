package me.heitx.maserow.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface IClient {
	String getHostname();

	String getUsername();

	String getPassword();

	int getPort();

	String getAuth();

	String getCharacters();

	String getWorld();

	void setDatabase(String database);

	Connection getConnection() throws SQLException;
}