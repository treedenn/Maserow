package me.heitx.maserow.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface IConnectionCallback {
	PreparedStatement execute(Connection ps) throws SQLException;
}