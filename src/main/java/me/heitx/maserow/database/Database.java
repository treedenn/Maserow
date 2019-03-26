package me.heitx.maserow.database;

import me.heitx.maserow.database.implementation.*;
import me.heitx.maserow.database.repository.*;

public final class Database implements IDatabase {
	private static IDatabase ourInstance;

	private static boolean authAccess;
	private static boolean charactersAccess;
	private static boolean worldAccess;

	private IClient client;

	private IItemRepository itemRepository;
	private IQuestRepository questRepository;
	private ICreatureRepository creatureRepository;
	private ICharacterRepository characterRepository;
	private IItemInstanceRepository itemInstanceRepository;
	private IMailRepository mailRepository;
	private ISmartScriptRepository smartScriptRepository;

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

		itemRepository = new ItemRepository(client);
		questRepository = new QuestRepository(client);
		creatureRepository = new CreatureRepository(client);
		characterRepository = new CharacterRepository(client);
		itemInstanceRepository = new ItemInstanceRepository(client);
		mailRepository = new MailRepository(client);
		smartScriptRepository = new SmartScriptRepository(client);
	}

	@Override
	public IItemRepository getItemRepository() {
		return itemRepository;
	}

	@Override
	public IQuestRepository getQuestRepository() {
		return questRepository;
	}

	@Override
	public ICreatureRepository getCreatureRepository() {
		return creatureRepository;
	}

	@Override
	public ICharacterRepository getCharacterRepository() {
		return characterRepository;
	}

	@Override
	public IItemInstanceRepository getItemInstanceRepository() {
		return itemInstanceRepository;
	}

	@Override
	public IMailRepository getMailRepository() {
		return mailRepository;
	}

	@Override
	public ISmartScriptRepository getSmartScriptRepository() {
		return smartScriptRepository;
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
