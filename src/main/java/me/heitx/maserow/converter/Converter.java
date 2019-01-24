package me.heitx.maserow.converter;

public class Converter {
	private static IConverter ourInstance;

	public static IConverter getInstance() {
		return ourInstance;
	}

	public static void select(Type type) {
		switch(type) {
			case TRINITY: ourInstance = new TrinityWotlkConverter(); break;
		}
	}

	public enum Type {
		TRINITY
	}
}
