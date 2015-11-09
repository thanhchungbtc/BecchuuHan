package com.btc.supports;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helpers {
	static public SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");
	public static Date dateFromString(String string) {
		if (string == null) return null;
		try {
			return dateFormatter.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
	public static String stringFromDate(Date date) {
		if (date == null) return null;
		return dateFormatter.format(date).toString();
	}
}
