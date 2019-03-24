package me.heitx.maserow.database;

import me.heitx.maserow.database.implementation.*;
import me.heitx.maserow.database.repository.*;

public final class Database implements IDatabase {
	private static IDatabase ourInstance;

	private static boolean authAccess;
	private static boolean charactersAccess;
	private static boolean worldAccess;

	private IClient client;

	private IItemRepository itemDAO;
	private IQuestRepository questDAO;
	private ICreatureRepository creatureDAO;
	private ICharacterRepository characterDAO;
	private IItemInstanceRepository itemInstanceDAO;
	private IMailRepository mailDAO;

	public static IDatabase getInstance() {
		if(ourInstance == null) ourInstance = new Database();

		return ourInstance;
	}

	private Database() { }

	@Override
	public IClient getClient() {
		return client;
	}

	@Override
	public void setClient(IClient client) {
		this.client = client;

		itemDAO = new ItemRepository(client);
		questDAO = new QuestRepository(client);
		creatureDAO = new CreatureRepository(client);
		characterDAO = new CharacterRepository(client);
		itemInstanceDAO = new ItemInstanceRepository(client);
		mailDAO = new MailRepository(client);
	}

	@Override
	public IItemRepository getItemDAO() {
		return itemDAO;
	}

	@Override
	public IQuestRepository getQuestDAO() {
		return questDAO;
	}

	@Override
	public ICreatureRepository getCreatureDAO() {
		return creatureDAO;
	}

	@Override
	public ICharacterRepository getCharacterDAO() {
		return characterDAO;
	}

	@Override
	public IItemInstanceRepository getItemInstanceDAO() {
		return itemInstanceDAO;
	}

	@Override
	public IMailRepository getMailDAO() {
		return mailDAO;
	}

	public static boolean hasAccess(Selection ds) {
		switch(ds) {
			case AUTH: return authAccess;
			case CHARACTERS: return charactersAccess;
			case WORLD: return worldAccess;
			default: return false;
		}
	}

	public static void setAccess(Selection ds, boolean access) {
		switch(ds) {
			case AUTH: authAccess = access;
			case CHARACTERS: charactersAccess = access;
			case WORLD: worldAccess = access;
		}
	}

	public enum Selection {
		AUTH,
		CHARACTERS,
		WORLD
	}
}
