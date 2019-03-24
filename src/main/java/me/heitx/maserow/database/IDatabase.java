package me.heitx.maserow.database;

import me.heitx.maserow.database.repository.*;

public interface IDatabase {
	IClient getClient();
	void setClient(IClient client);

	IItemRepository getItemDAO();
	IQuestRepository getQuestDAO();
	ICreatureRepository getCreatureDAO();
	ICharacterRepository getCharacterDAO();
	IItemInstanceRepository getItemInstanceDAO();
	IMailRepository getMailDAO();
}