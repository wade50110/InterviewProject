package com.example.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {
	/**
	 * utc 時間格式轉換正常格式 2018-08-07T03:41:59Z
	 * 
	 * @param utcTime 時間
	 * @return
	 */
	public static String formatStrUTCToDateStr(String utcTime) {
	    SimpleDateFormat sf = new SimpleDateFormat("MMM dd',' yyyy HH:mm:ss", Locale.US);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    TimeZone utcZone = TimeZone.getTimeZone("UTC");
	    sf.setTimeZone(utcZone);
	    Date date = null;
	    String dateTime = "";
	    try {
	        date = sf.parse(utcTime);
	        dateTime = sdf.format(date);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    return dateTime;
	}


}
