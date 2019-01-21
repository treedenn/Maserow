package me.heitx.maserow.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectionCallback {
	void execute(Connection conn) throws SQLException;
}