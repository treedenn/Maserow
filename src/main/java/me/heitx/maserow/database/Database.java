package me.heitx.maserow.database;

public final class Database {
	private static IDatabase ourInstance;
	private static boolean isLoggedIn;

	public static IDatabase getInstance() {
		return ourInstance;
	}

	public static void select(Type type) {
		switch(type) {
			case TRINITY: ourInstance = new TrinityDatabase(); break;
		}
	}

	public static boolean isLoggedIn() {
		return isLoggedIn;
	}

	public static void setIsLoggedIn(boolean isLoggedIn) {
		Database.isLoggedIn = isLoggedIn;
	}

	public enum Type {
		TRINITY
	}
}
