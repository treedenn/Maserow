package me.heitx.maserow.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface IClient {
	String getHostname();
	//void setHostname(String hostname);
	String getUsername();
	//void setUsername(String username);
	String getPassword();
	//void setPassword(String password);
	int getPort();
	//void setPort(int port);
	String getAuth();
	//void setAuth(String auth);
	String getCharacters();
	//void setCharacters(String characters);
	String getWorld();
	//void setWorld(String world);

	void setDatabase(String database);

	Connection getConnection(Database.Selection selection) throws SQLException;
}