package me.heitx.maserow.common.database;

import me.heitx.maserow.common.database.repository.*;

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