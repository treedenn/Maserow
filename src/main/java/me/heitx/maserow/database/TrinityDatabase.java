package me.heitx.maserow.database;

import me.heitx.maserow.database.dao.*;
import me.heitx.maserow.database.wrappers.*;

public class TrinityDatabase implements IDatabase {
	private IClient client;

	private IItemDAO itemDAO;
	private IQuestDAO questDAO;
	private ICreatureDAO creatureDAO;
	private ICharacterDAO characterDAO;
	private IItemInstanceDAO itemInstanceDAO;
	private IMailDAO mailDAO;

	TrinityDatabase() { }

	@Override
	public IClient getClient() {
		return client;
	}

	@Override
	public void setClient(IClient client) {
		this.client = client;

		itemDAO = new ItemDAO(client);
		questDAO = new QuestDAO(client);
		creatureDAO = new CreatureDAO(client);
		characterDAO = new CharacterDAO(client);
		itemInstanceDAO = new ItemInstanceDAO(client);
		mailDAO = new MailDAO(client);
	}

	@Override
	public IItemDAO getItemDAO() {
		return itemDAO;
	}

	@Override
	public IQuestDAO getQuestDAO() {
		return questDAO;
	}

	@Override
	public ICreatureDAO getCreatureDAO() {
		return creatureDAO;
	}

	@Override
	public ICharacterDAO getCharacterDAO() {
		return characterDAO;
	}

	@Override
	public IItemInstanceDAO getItemInstanceDAO() {
		return itemInstanceDAO;
	}

	@Override
	public IMailDAO getMailDAO() {
		return mailDAO;
	}
}
