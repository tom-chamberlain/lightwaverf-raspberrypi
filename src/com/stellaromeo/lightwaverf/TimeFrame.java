package com.stellaromeo.lightwaverf;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeFrame {
	
	private static final String DELIMITER = "\\|";
	private String startTime;
	private String endTime;
	private Days onlyWeekdays;
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	public TimeFrame(String startTime, String endTime, Days onlyWeekdays)
	{
		this.startTime = startTime;
		this.endTime = endTime;
		this.onlyWeekdays = onlyWeekdays;
	}
	
	
	public boolean isActive()
	{
		Date earliestStartTime = null;
		Date earliestEndTime = null;

		Date tmpStartTime;
		Date tmpEndTime;
		
		String[] startTimes = startTime.split(DELIMITER);
		
		for (int i = 0; i < startTimes.length; i++)
		{
			if (startTimes[i].equals(SunriseSunset.SUNRISE))
			{
				tmpStartTime = formatTime(SunriseSunset.getSunriseTime());
			}
			else if (startTimes[i].equals(SunriseSunset.SUNSET))
			{
				tmpStartTime = formatTime(SunriseSunset.getSunsetTime());
				tmpStartTime.setTime(tmpStartTime.getTime() - (1000 * 60 * 30));
			} else
			{
				tmpStartTime = formatTime(startTimes[i]);
			}	
			
			if (earliestStartTime == null)
			{
				earliestStartTime = tmpStartTime;
			}
			else
			{
				if (tmpStartTime.after(earliestStartTime))
				{
					earliestStartTime = tmpStartTime;
				}
			}
		}
		
		
		String[] endTimes = endTime.split(DELIMITER);
		
		for (int i = 0; i < endTimes.length; i++)
		{
			if (endTimes[i].equals(SunriseSunset.SUNRISE))
			{
				tmpEndTime = formatTime(SunriseSunset.getSunriseTime());
			}
			else if (endTimes[i].equals(SunriseSunset.SUNSET))
			{
				tmpEndTime = formatTime(SunriseSunset.getSunsetTime());
				tmpEndTime.setTime(tmpEndTime.getTime() - (1000 * 60 * 30));

			} else
			{
				tmpEndTime = formatTime(endTimes[i]);
			}	
			
			if (earliestEndTime == null)
			{
				earliestEndTime = tmpEndTime;
			}
			else
			{
				if (tmpEndTime.before(earliestEndTime))
				{
					earliestEndTime = tmpEndTime;
				}
			}
		}
		
		
			Calendar dateNow = Calendar.getInstance(TimeZone.getTimeZone("Europe/London"));
			Date timeNow = formatTime(dateNow.get(Calendar.HOUR_OF_DAY) + ":" + dateNow.get(Calendar.MINUTE));
			return timeNow.after(earliestStartTime) && timeNow.before(earliestEndTime) && isWeekday(dateNow);
			
	}
	


	private boolean isWeekday(Calendar dateNow) {
		if (onlyWeekdays == Days.Weekdays)
		{
			return dateNow.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && dateNow.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY;
		}
		return true;
	}

	public Date formatTime(String time)
	{
		try {
			return dateFormat.parse("01/01/2014 " + time);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
