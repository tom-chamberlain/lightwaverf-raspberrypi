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
	
	private static Logger logger = LogManager.getLogger();

	
	public static boolean requiresActivating(ScheduledItem item)
	{
		boolean activate = currentTimeWithinPeriod(item) &&	!item.isActivated();
		
		if (item.isIgnoreIfSunAlreadyRisen())
		{
			activate &= !sunHasRisen();
		}
		
		if (item.isOnlyWeekdays())
		{
			activate &= todayIsAWeekday();
		}
		
		if (item.isOnlyWeekends())
		{
			activate &= todayIsAWeekend();
		}
		
		if (item.isDelayStartTimeToSunsetIfSunNotSet())
		{
			activate &= sunHasSet();
		}
				
		return activate;
	}
	

	public static boolean requiresDeactivating(ScheduledItem item)
	{
		boolean deactivate = !currentTimeWithinPeriod(item) && item.isActivated();
		
		if (item.isEndAtSunriseIfSunriseBeforeEndTime())
		{
			deactivate &= sunHasRisen();
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
		Calendar dateNow = Calendar.getInstance(TimeZone.getTimeZone("Europe/London"));
		return currentTimeWithinPeriod(parseTime(dateNow.get(Calendar.HOUR_OF_DAY) + ":" + dateNow.get(Calendar.MINUTE)), 
				parseTime(item.getStartTime()),
				parseTime(item.getEndTime()));
	}
	
	public static boolean currentTimeWithinPeriod(RandomItem item)
	{
		return currentTimeWithinPeriod(Calendar.getInstance(TimeZone.getTimeZone("Europe/London")), 
				item.getCalculatedTimeToStart(),
				item.getCalculatedTimeToEnd());
	}
	
	public static boolean currentTimeWithinPeriod(Calendar timeNow, Calendar startTime, Calendar endTime)
	{
		if (startTime == null)
		{
			return timeNow.before(endTime);
		}
		if (endTime == null)
		{
			return timeNow.after(startTime);
		}
		return timeNow.after(startTime) && timeNow.before(endTime);
	}
	

	public static Calendar parseTime(String timeAsString) {
		
		if (timeAsString == null) return null;
		
		Calendar time = Calendar.getInstance();
		try {
			time.setTime(new SimpleDateFormat("HH:mm").parse(timeAsString));
		}
		catch (Exception e)
		{
			logger.error("Unable to parse date {}", timeAsString, e);
			e.printStackTrace();
			System.exit(1);
		}
		return time;
	}
	
	
	public static boolean sunHasRisen()
	{
		Location location = new Location("52.489471", "-1.898575");
		SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(location, "Europe/London");
		return Calendar.getInstance().after(calculator.getOfficialSunriseCalendarForDate(Calendar.getInstance()));
	}
	
	public static boolean sunHasSet()
	{
		Location location = new Location("52.489471", "-1.898575");
		SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(location, "Europe/London");
		return Calendar.getInstance().after(calculator.getOfficialSunsetCalendarForDate(Calendar.getInstance()));
	}
	
	public static boolean todayIsAWeekday() {
		Calendar timeNow = Calendar.getInstance();
		return timeNow.get(Calendar.DAY_OF_WEEK) > 0 && timeNow.get(Calendar.DAY_OF_WEEK) < 7;
	}
	
	public static boolean todayIsAWeekend() {
		Calendar timeNow = Calendar.getInstance();
		return timeNow.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || timeNow.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY; 
	}
	
}
