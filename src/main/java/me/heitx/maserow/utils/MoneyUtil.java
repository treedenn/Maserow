package me.heitx.maserow.utils;

public class MoneyUtil {
	public static long[] totalToGSC(long total) {
		long[] money = new long[3];

		money[0] = total / 10000;
		total = total % 10000;
		money[1] = total / 100;
		total = total % 100;
		money[2] = total;

		return money;
	}

	public static long gscToTotal(long gold, int silver, int copper) {
		return gold * 10000 + silver * 100 + copper;
	}
}