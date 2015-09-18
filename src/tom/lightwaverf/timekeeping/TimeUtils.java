package tom.lightwaverf.timekeeping;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import tom.lightwaverf.model.ScheduledItem;

public class TimeUtils {

	
	public boolean requiresActivating(ScheduledItem item)
	{
		System.out.println("Returning false");
		return currentTimeWithinPeriod(item) && !item.isActivated();
	}
	
	public boolean requiresDeactivating(ScheduledItem item)
	{
		System.out.println("Returning false");
		return !currentTimeWithinPeriod(item) && item.isActivated();
	}
	
	
	
	private boolean currentTimeWithinPeriod(ScheduledItem item)
	{
		Calendar dateNow = Calendar.getInstance(TimeZone.getTimeZone("Europe/London"));
		Calendar timeNow = parseTime(dateNow.get(Calendar.HOUR_OF_DAY) + ":" + dateNow.get(Calendar.MINUTE));
		
		return timeNow.after(item.getStartTime()) && timeNow.before(item.getEndTime());
	}
	

	private Calendar parseTime(String timeAsString) {
		Calendar time = Calendar.getInstance();
		try {
			time.setTime(new SimpleDateFormat("HH:mm").parse(timeAsString));
		}
		catch (Exception e)
		{
			System.err.println("Unable to parse date");
			e.printStackTrace();
			System.exit(1);
		}
		return time;
	}
	
}
