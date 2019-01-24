package me.heitx.maserow.database;

import me.heitx.maserow.database.dao.ItemDAO;

public interface IDatabase {
	IClient getClient();
	void setClient(IClient client);

	ItemDAO getItemDAO();
}