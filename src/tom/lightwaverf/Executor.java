package tom.lightwaverf;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tom.lightwaverf.model.RandomItem;
import tom.lightwaverf.model.ScheduledItem;
import tom.lightwaverf.timekeeping.LightwaveRfUtils;
import tom.lightwaverf.timekeeping.TimeUtils;

public class Executor {
	
	private List<ScheduledItem> itemsToSchedule;
	
	private List<RandomItem> itemsToPerformAtRandomTimes;
	
	private List<String> timesToTurnEverythingOff;
	private HashMap<String, Calendar> everythingOffRemeber = new HashMap<>();
	
	private LightwaveRfUtils lightwaveUtils;
	
	private Random random = new Random();

	private Logger logger = LogManager.getLogger();
	
	/**
	 * Scans all items loaded and turns off or on those devices if required
	 */
	public void processItems()
	{
		for (ScheduledItem item : itemsToSchedule)
		{
			if (TimeUtils.requiresActivating(item))
			{
				lightwaveUtils.activate(item);
			}
			
			if (TimeUtils.requiresDeactivating(item))
			{
				lightwaveUtils.deactivate(item);
			}
		}
	}
	
	
	public void processAllOffEvents()
	{
		TIMES_TO_TURN_EVERYTHING_OFF_LOOP:
		for (String offTimeAsString : timesToTurnEverythingOff)
		{		
			if (everythingOffRemeber.containsKey(offTimeAsString))
			{
				if (everythingOffRemeber.get(offTimeAsString).get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
				{
					continue TIMES_TO_TURN_EVERYTHING_OFF_LOOP;
				}
				else
				{
					turnEverythingOffIfCorrectTime(offTimeAsString);
				}
			}
			else
			{
				turnEverythingOffIfCorrectTime(offTimeAsString);
			}
			
		}
	}
	
	
	public void processRandomItems()
	{
		for (RandomItem randomItem : itemsToPerformAtRandomTimes)
		{
			if (randomItem.getCalculatedTimeToEnd() == null)
			{
				Calendar fromTime = TimeUtils.parseTime(randomItem.getFromTime());
				Calendar endTime = TimeUtils.parseTime(randomItem.getEndTime());
				
				int numberOfMinutesBetweenTimes = (int)(endTime.getTimeInMillis() - fromTime.getTimeInMillis()) / 1000 / 60;
				
				int numberOfMinutesToAdd = random.nextInt(numberOfMinutesBetweenTimes);
				
				fromTime.add(Calendar.MINUTE, numberOfMinutesToAdd);
				endTime.setTimeInMillis(fromTime.getTimeInMillis());
				endTime.add(Calendar.MINUTE, random.nextInt(5));
				
				logger.info("Calculated random times for {} of {} at {} and off at {}", randomItem.getFunction().getName(),
						randomItem.getDevice().getName(), fromTime.getTime().toGMTString(), endTime.getTime().toGMTString());
				
				randomItem.setCalculatedTimeToStart(fromTime);
				randomItem.setCalculatedTimeToEnd(endTime);
			}
			
			
			if (TimeUtils.requiresActivating(randomItem))
			{
				lightwaveUtils.activate(randomItem);
			}
			
			if (TimeUtils.requiresDeactivating(randomItem))
			{
				lightwaveUtils.deactivate(randomItem);
				randomItem.setCalculatedTimeToEnd(null);
			}
			
		}
	
	}


	private void turnEverythingOffIfCorrectTime(String offTimeAsString) {
		
		Calendar offTime = TimeUtils.parseTime(offTimeAsString);
		Calendar timeNow = Calendar.getInstance();
		
		if (offTime.get(Calendar.MINUTE) == timeNow.get(Calendar.MINUTE) && offTime.get(Calendar.HOUR_OF_DAY) == timeNow.get(Calendar.HOUR_OF_DAY))
		{
			for (ScheduledItem item : itemsToSchedule)
			{
				lightwaveUtils.deactivate(item);
			}
			everythingOffRemeber.put(offTimeAsString, Calendar.getInstance());
		}
	}
	
	/**
	 * Sends the pairing code to the LightwaveRF box  
	 */
	public void sendInitCode()
	{
		lightwaveUtils.sendInitCode();
	}

	public void setItemsToSchedule(List<ScheduledItem> itemsToSchedule) {
		this.itemsToSchedule = itemsToSchedule;
	}

	public void setLightwaveUtils(LightwaveRfUtils lightwaveUtils) {
		this.lightwaveUtils = lightwaveUtils;
	}

	public void setTimesToTurnEverythingOff(List<String> timesToTurnAllOff) {
		this.timesToTurnEverythingOff = timesToTurnAllOff;
	}

	public void setItemsToPerformAtRandomTimes(List<RandomItem> itemsToPerformAtRandomTimes) {
		this.itemsToPerformAtRandomTimes = itemsToPerformAtRandomTimes;
	}
	
	
}
