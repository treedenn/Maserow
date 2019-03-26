package me.heitx.maserow.database;

import me.heitx.maserow.database.repository.*;

public interface IDatabase {
	IClient getClient();
	void setClient(IClient client);

	IItemRepository getItemRepository();
	IQuestRepository getQuestRepository();
	ICreatureRepository getCreatureRepository();
	ICharacterRepository getCharacterRepository();
	IItemInstanceRepository getItemInstanceRepository();
	IMailRepository getMailRepository();
	ISmartScriptRepository getSmartScriptRepository();
}