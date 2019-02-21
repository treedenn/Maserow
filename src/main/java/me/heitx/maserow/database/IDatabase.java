package me.heitx.maserow.database;

import me.heitx.maserow.database.dao.IItemDAO;
import me.heitx.maserow.database.dao.IQuestDAO;

public interface IDatabase {
	IClient getClient();
	void setClient(IClient client);

	IItemDAO getItemDAO();
	IQuestDAO getQuestDAO();
}