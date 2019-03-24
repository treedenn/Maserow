package me.heitx.maserow.database;

public final class Database {
	private static IDatabase ourInstance;
	private static boolean authAccess;
	private static boolean charactersAccess;
	private static boolean worldAccess;

	public static IDatabase getInstance() {
		return ourInstance;
	}

	public static void select(Type type) {
		switch(type) {
			case TRINITY: ourInstance = new TrinityDatabase(); break;
		}
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

	public enum Type {
		TRINITY
	}

	public enum Selection {
		AUTH,
		CHARACTERS,
		WORLD
	}
}
