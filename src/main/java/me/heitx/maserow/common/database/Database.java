package me.heitx.maserow.common.database;

import me.heitx.maserow.common.database.implementation.*;
import me.heitx.maserow.common.database.repository.*;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public final class Database implements IDatabase {
	private static IDatabase ourInstance;

	private IClient client;
	private Map<Class, Repository> repositories;

	private static boolean authAccess;
	private static boolean charactersAccess;
	private static boolean worldAccess;

	public static IDatabase getInstance() {
		if(ourInstance == null) ourInstance = new Database();

		return ourInstance;
	}

	private Database() {
		this.repositories = new HashMap<>();
	}

	@Override
	public IClient getClient() {
		return client;
	}

	@Override
	public void setClient(IClient client) {
		this.client = client;

		if(repositories.size() > 0) {
			for(Repository value : repositories.values()) {
				value.setClient(client);
			}
		} else {
			ServiceLoader<Repository> loader = ServiceLoader.load(Repository.class);
			for(Repository repository : loader) {
				repository.setClient(client);
				repositories.put(repository.getClass(), repository);
			}
		}
	}

	@Override
	public IItemRepository getItemRepository() {
		return (IItemRepository) repositories.getOrDefault(ItemRepository.class, null);
	}

	@Override
	public IQuestRepository getQuestRepository() {
		return (IQuestRepository) repositories.getOrDefault(QuestRepository.class, null);
	}

	@Override
	public ICreatureRepository getCreatureRepository() {
		return (ICreatureRepository) repositories.getOrDefault(CreatureRepository.class, null);
	}

	@Override
	public ICharacterRepository getCharacterRepository() {
		return (ICharacterRepository) repositories.getOrDefault(CharacterRepository.class, null);
	}

	@Override
	public IItemInstanceRepository getItemInstanceRepository() {
		return (IItemInstanceRepository) repositories.getOrDefault(ItemInstanceRepository.class, null);
	}

	@Override
	public IMailRepository getMailRepository() {
		return (IMailRepository) repositories.getOrDefault(MailRepository.class, null);
	}

	@Override
	public ISmartScriptRepository getSmartScriptRepository() {
		return (ISmartScriptRepository) repositories.getOrDefault(SmartScriptRepository.class, null);
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
