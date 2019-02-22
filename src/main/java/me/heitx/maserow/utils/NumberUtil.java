package me.heitx.maserow.utils;

public class NumberUtil {
	public static boolean isAllSameNumber(int[] numbers) {
		boolean b = true;

		for(int i = 1; i < numbers.length; i++) {
			if(numbers[i - 1] != numbers[i]) {
				b = false;
				break;
			}
		}

		return b;
	}
}