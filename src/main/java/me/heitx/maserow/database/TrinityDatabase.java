package me.heitx.maserow.database;

import me.heitx.maserow.database.dao.ICreatureDAO;
import me.heitx.maserow.database.dao.IItemDAO;
import me.heitx.maserow.database.dao.IQuestDAO;
import me.heitx.maserow.database.wrappers.CreatureDAO;
import me.heitx.maserow.database.wrappers.ItemDAO;
import me.heitx.maserow.database.wrappers.QuestDAO;

public class TrinityDatabase implements IDatabase {
	private IClient client;

	private IItemDAO itemDAO;
	private IQuestDAO questDAO;
	private ICreatureDAO creatureDAO;

	TrinityDatabase() {

	}

	@Override
	public IClient getClient() {
		return client;
	}

	@Override
	public void setClient(IClient client) {
		this.client = client;
	}

	@Override
	public IItemDAO getItemDAO() {
		if(Database.isLoggedIn() && itemDAO == null) itemDAO = new ItemDAO(client);
		return itemDAO;
	}

	@Override
	public IQuestDAO getQuestDAO() {
		if(Database.isLoggedIn() && questDAO == null) questDAO = new QuestDAO(client);
		return questDAO;
	}

	@Override
	public ICreatureDAO getCreatureDAO() {
		if(Database.isLoggedIn() && creatureDAO == null) creatureDAO = new CreatureDAO(client);
		return creatureDAO;
	}
}
