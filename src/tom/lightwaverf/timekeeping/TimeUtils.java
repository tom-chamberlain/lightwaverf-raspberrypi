package tom.lightwaverf.timekeeping;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;

import tom.lightwaverf.model.RandomItem;
import tom.lightwaverf.model.ScheduledItem;

public class TimeUtils {
	
	private static final String EUROPE_LONDON = "Europe/London";
	private static Logger logger = LogManager.getLogger();

	
	public static boolean requiresActivating(ScheduledItem item)
	{
		if (item.isActivated()) return false;
		
		boolean activate = currentTimeWithinPeriod(item);
		
		if (item.isIgnoreIfSunAlreadyRisen())
		{
			activate &= !sunHasRisen();
		}
		
		if (item.isOnlyWeekdays())
		{
			activate &= !todayIsAWeekend();
		}
		
		if (item.isOnlyWeekends())
		{
			activate &= todayIsAWeekend();
		}
		
		if (item.isDelayStartTimeToSunsetIfSunNotSet())
		{
			activate &= sunHasSet();
		}
		
		if (item.isEndAtSunriseIfSunriseBeforeEndTime())
		{
			activate &= !sunHasRisen();
		}
		
				
		return activate;
	}
	

	public static boolean requiresDeactivating(ScheduledItem item)
	{
		if (!item.isActivated()) return false;
		
		boolean deactivate = !currentTimeWithinPeriod(item);
		
		if (item.isEndAtSunriseIfSunriseBeforeEndTime())
		{
			deactivate |= sunHasRisen();
		}
		
		return deactivate;
	}
	
	public static boolean requiresActivating(RandomItem item)
	{
		return currentTimeWithinPeriod(item) &&	!item.isActivated();
	}
	
	public static boolean requiresDeactivating(RandomItem item)
	{
		return !currentTimeWithinPeriod(item) && item.isActivated();
	}
	
	
	public static boolean currentTimeWithinPeriod(ScheduledItem item)
	{
		return currentTimeWithinPeriod(Calendar.getInstance(TimeZone.getTimeZone(EUROPE_LONDON)), 
				parseTimeWithTodaysDate(item.getStartTime()),
				parseTimeWithTodaysDate(item.getEndTime()));
	}
	
	public static boolean currentTimeWithinPeriod(RandomItem item)
	{
		return currentTimeWithinPeriod(Calendar.getInstance(TimeZone.getTimeZone(EUROPE_LONDON)), 
				item.getCalculatedTimeToStart(),
				item.getCalculatedTimeToEnd());
	}
	
	public static boolean currentTimeWithinPeriod(Calendar timeNow, Calendar startTime, Calendar endTime)
	{
		return timeNow.after(startTime) && timeNow.before(endTime != null ? endTime : parseTimeWithTodaysDate("23:59"));
	}
	

	public static Calendar parseTimeWithTodaysDate(String timeAsString) {
		
		if (timeAsString == null) return null;
		
		Calendar timeNow = Calendar.getInstance(TimeZone.getTimeZone(EUROPE_LONDON));
		
		try {
			Calendar time = Calendar.getInstance();
			time.setTime(new SimpleDateFormat("HH:mm").parse(timeAsString));
			timeNow.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY));
			timeNow.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
			timeNow.set(Calendar.SECOND, 0);
		}
		catch (Exception e)
		{
			logger.error("Unable to parse date {}", timeAsString, e);
			e.printStackTrace();
			System.exit(1);
		}
		return timeNow;
	}
	

	
	public static boolean sunHasRisen()
	{
		Location location = new Location("52.489471", "-1.898575");
		SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(location, EUROPE_LONDON);
		return Calendar.getInstance().after(calculator.getOfficialSunriseCalendarForDate(Calendar.getInstance()));
	}
	
	public static boolean sunHasSet()
	{
		Location location = new Location("52.489471", "-1.898575");
		SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(location, EUROPE_LONDON);
		return Calendar.getInstance().after(calculator.getOfficialSunsetCalendarForDate(Calendar.getInstance()));
	}

	public static boolean todayIsAWeekend() {
		Calendar timeNow = Calendar.getInstance();
		return timeNow.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || timeNow.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY; 
	}
	
}
