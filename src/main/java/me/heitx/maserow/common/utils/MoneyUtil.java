package me.heitx.maserow.common.utils;

import java.util.*;

public class MoneyUtil {
	public static Collection<Long> totalToGSC(long total) {
		List<Long> money = new ArrayList<>();

		money.add(total / 10000); // gold
		total = total % 10000;
		money.add(total / 100); // silver
		total = total % 100;
		money.add(total); // copper (leftover)

		return Collections.unmodifiableCollection(money);
	}

	public static int gscToTotal(String gold, String silver, String copper) {
		return gscToTotal(Integer.parseInt(gold), Integer.parseInt(silver), Integer.parseInt(copper));
	}

	public static int gscToTotal(int gold, int silver, int copper) {
		return gold * 10000 + silver * 100 + copper;
	}
}