package tom.lightwaverf.timekeeping;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;

import tom.lightwaverf.model.ScheduledItem;

public class TimeUtils {
	
	private Logger logger = LogManager.getLogger();

	
	public boolean requiresActivating(ScheduledItem item)
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
	

	public boolean requiresDeactivating(ScheduledItem item)
	{
		boolean deactivate = !currentTimeWithinPeriod(item) && item.isActivated();
		
		if (item.isEndAtSunriseIfSunriseBeforeEndTime())
		{
			deactivate &= sunHasRisen();
		}
		
		return deactivate;
	}
	
	
	
	private boolean currentTimeWithinPeriod(ScheduledItem item)
	{
		Calendar dateNow = Calendar.getInstance(TimeZone.getTimeZone("Europe/London"));
		Calendar timeNow = parseTime(dateNow.get(Calendar.HOUR_OF_DAY) + ":" + dateNow.get(Calendar.MINUTE));
		return timeNow.after(parseTime(item.getStartTime())) && timeNow.before(parseTime(item.getEndTime()));
	}
	

	private Calendar parseTime(String timeAsString) {
		Calendar time = Calendar.getInstance();
		try {
			time.setTime(new SimpleDateFormat("HH:mm").parse(timeAsString));
		}
		catch (Exception e)
		{
			logger.error("Unable to parse date", e);
			e.printStackTrace();
			System.exit(1);
		}
		return time;
	}
	
	
	private boolean sunHasRisen()
	{
		Location location = new Location("52.489471", "-1.898575");
		SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(location, "Europe/London");
		return Calendar.getInstance().after(calculator.getOfficialSunriseCalendarForDate(Calendar.getInstance()));
	}
	
	private boolean sunHasSet()
	{
		Location location = new Location("52.489471", "-1.898575");
		SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(location, "Europe/London");
		return Calendar.getInstance().after(calculator.getOfficialSunsetCalendarForDate(Calendar.getInstance()));
	}
	
	private boolean todayIsAWeekday() {
		Calendar timeNow = Calendar.getInstance();
		return timeNow.get(Calendar.DAY_OF_WEEK) > 0 && timeNow.get(Calendar.DAY_OF_WEEK) < 7;
	}
	
	private boolean todayIsAWeekend() {
		Calendar timeNow = Calendar.getInstance();
		return timeNow.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || timeNow.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY; 
	}
	
}
