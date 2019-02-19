package me.heitx.maserow.database;

import me.heitx.maserow.database.dao.IItemDAO;

public interface IDatabase {
	IClient getClient();
	void setClient(IClient client);

	IItemDAO getItemDAO();
}