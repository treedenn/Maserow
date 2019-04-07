package me.heitx.maserow.common.utils;

public final class ResourceUtil {
	public static final String[] RESISTANCE_NAMES = {
			"Holy", "Fire", "Frost", "Shadow", "Nature", "Arcane"
	};

	public static String getResistancePath(int index) {
		return "resistance/" + RESISTANCE_NAMES[index].toLowerCase() + ".png";
	}
}