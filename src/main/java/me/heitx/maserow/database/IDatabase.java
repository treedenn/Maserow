package me.heitx.maserow.database;

import me.heitx.maserow.database.dao.*;

public interface IDatabase {
	IClient getClient();
	void setClient(IClient client);

	IItemDAO getItemDAO();
	IQuestDAO getQuestDAO();
	ICreatureDAO getCreatureDAO();
	ICharacterDAO getCharacterDAO();
	IItemInstanceDAO getItemInstanceDAO();
	IMailDAO getMailDAO();
}