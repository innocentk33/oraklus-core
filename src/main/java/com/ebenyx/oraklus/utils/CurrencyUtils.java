package com.ebenyx.oraklus.utils;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.UUID;

public class CurrencyUtils {

	public static String formattingAmount(Double money){
		if(money == null) money = 0.0;
		Locale locale = new Locale("fr", "FR");
		NumberFormat numberFormat = NumberFormat.getInstance(locale);
		String sMoney = numberFormat.format(money);
		return sMoney;
	}

}
