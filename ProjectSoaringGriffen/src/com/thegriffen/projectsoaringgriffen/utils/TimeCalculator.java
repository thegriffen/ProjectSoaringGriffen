package com.thegriffen.projectsoaringgriffen.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeCalculator {
	
	private String timeStart, timeEnd;
	private long diffHours, diffMinutes;
	
	public TimeCalculator(String timeStart, String timeEnd) {
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
	}
	
	public String calculate() {
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.US);
		Date dateStart = null;
		Date dateEnd = null;
		diffMinutes = 0;
		diffHours = 0;
		String hourLabel, minuteLabel;
		try {
			dateStart = timeFormat.parse(timeStart);
			dateEnd = timeFormat.parse(timeEnd);
			
			long diff = dateEnd.getTime() - dateStart.getTime();
			diffMinutes = diff/(60*1000)%60;
			diffHours = diff/(60*60*1000)%24;			
					
		} catch (Exception e) {
			
		}
		if (diffHours > 1 || diffHours == 0) {
			hourLabel = "hrs ";
		}
		else {
			hourLabel = "hr ";
		}
		if (diffMinutes > 1 || diffMinutes == 0) {
			minuteLabel = "mins";
		}
		else {
			minuteLabel = "min";
		}
		if (diffHours > 0 && diffMinutes > 0) {
			return diffHours + hourLabel + diffMinutes + minuteLabel;
		}
		else if (diffHours > 0 && diffMinutes == 0) {
			return diffHours + hourLabel;
		}
		else {
			return diffMinutes + minuteLabel;
		}
	}
	
	public int getHours() {
		calculate();
		return (int)diffHours;
	}
	
	public int getMinutes() {
		calculate();
		return (int)diffMinutes;
	}
}
