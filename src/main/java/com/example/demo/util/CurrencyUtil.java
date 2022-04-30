package com.example.demo.util;

import java.util.Currency;
import java.util.Set;

public class CurrencyUtil {
	public static String getCurrencyInstance(String numericCode) {
	    Set<Currency> currencies = Currency.getAvailableCurrencies();
	    for (Currency currency : currencies) {
	        if (currency.toString().equals(numericCode)) {
	            return currency.toString();
	        }
	    }
	    throw new IllegalArgumentException("Currency with numeric code "  + numericCode + " not found");
	}
}
