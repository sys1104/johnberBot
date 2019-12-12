package com.song.utils;

public final class CommonUtils {

	public static String moneyCommaUtil (String money) {
		int moneyInt = Integer.parseInt(money);
		money = String.format("%,d", moneyInt);
		return money;
	}
}