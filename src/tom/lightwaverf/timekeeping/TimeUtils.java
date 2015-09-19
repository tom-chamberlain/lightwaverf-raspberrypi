package tom.lightwaverf.timekeeping;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tom.lightwaverf.model.ScheduledItem;

public class TimeUtils {
	
	private Logger logger = LogManager.getLogger();

	
	public boolean requiresActivating(ScheduledItem item)
	{
		return currentTimeWithinPeriod(item) && !item.isActivated();
	}
	
	public boolean requiresDeactivating(ScheduledItem item)
	{
		return !currentTimeWithinPeriod(item) && item.isActivated();
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
	
}
