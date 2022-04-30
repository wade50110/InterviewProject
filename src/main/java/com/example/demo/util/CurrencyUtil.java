package com.example.demo.util;

import java.util.Currency;
import java.util.Locale;
import java.util.Set;

public class CurrencyUtil {
	
	/**
	 * 給貨幣代號 回傳中文代稱
	 * Ex : TWD   ->  新臺幣
	 * @param numericCode
	 * @return
	 */
	public static String getCurrencyInstance(String numericCode) {
	    Set<Currency> currencies = Currency.getAvailableCurrencies();
	    for (Currency currency : currencies) {
	        if (currency.toString().equals(numericCode)) {
	            return currency.getDisplayName(Locale.TRADITIONAL_CHINESE);
	        }
	    }
	    throw new IllegalArgumentException("Currency with numeric code "  + numericCode + " not found");
	}
}
