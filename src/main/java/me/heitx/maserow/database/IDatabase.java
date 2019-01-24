package me.heitx.maserow.database;

import me.heitx.maserow.database.dao.ItemDAO;

import java.sql.SQLException;

public interface IDatabase {
	IClient getClient();
	void setClient(IClient client);

	ItemDAO getItemDAO();
}