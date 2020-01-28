package com.song.utils;

public final class CommonUtils {

	public static String addMoneyComma (String money) {
		int moneyInt = Integer.parseInt(removeComma(money));
		money = String.format("%,d", moneyInt);
		return money;
	}
	
	public static String removeComma (String str) {
		str = str.replace(",", "");
		return str;
	}	
}