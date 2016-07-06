package tom.lightwaverf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tom.lightwaverf.model.RandomItem;
import tom.lightwaverf.model.ScheduledItem;
import tom.lightwaverf.timekeeping.LightwaveRfUtils;
import tom.lightwaverf.timekeeping.TimeUtils;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class Executor {
	
	private List<ScheduledItem> itemsToSchedule;
	
	private List<RandomItem> itemsToPerformAtRandomTimes;
	
	private List<String> timesToTurnEverythingOff;

	private LightwaveRfUtils lightwaveUtils;
	
	private Random random = new Random();

	private Logger logger = LogManager.getLogger();
	
	/**
	 * Scans all items loaded and turns off or on those devices if required
	 */
	public void processItems() {
		itemsToSchedule.stream()
					   .filter(TimeUtils::requiresActivating)
					   .forEach(lightwaveUtils::activate);

		itemsToSchedule.stream()
					   .filter(TimeUtils::requiresDeactivating)
					   .forEach(lightwaveUtils::deactivate);
	}

	public void processAllOffEvents() {
		for (String offTimeAsString : timesToTurnEverythingOff) {
			Calendar timeNow = Calendar.getInstance();
			Calendar offTime = TimeUtils.parseTimeWithTodaysDate(offTimeAsString);

			if (timeNow.get(Calendar.MINUTE) == offTime.get(Calendar.MINUTE) && timeNow.get(Calendar.HOUR_OF_DAY) == offTime.get(Calendar.HOUR_OF_DAY)
				&& timeNow.get(Calendar.SECOND) < 02) {
				turnEverythingOff();
			}
		}
	}

	public void processRandomItems() {
		for (RandomItem randomItem : itemsToPerformAtRandomTimes) {
			if (randomItem.getCalculatedTimeToEnd() == null) {
				Calendar fromTime = TimeUtils.parseTimeWithTodaysDate(randomItem.getFromTime());
				Calendar endTime = TimeUtils.parseTimeWithTodaysDate(randomItem.getEndTime());
				
				int numberOfMinutesBetweenTimes = (int)(endTime.getTimeInMillis() - fromTime.getTimeInMillis()) / 1000 / 60;
				
				int numberOfMinutesToAdd = random.nextInt(numberOfMinutesBetweenTimes);
				
				fromTime.add(Calendar.MINUTE, numberOfMinutesToAdd);
				endTime.setTimeInMillis(fromTime.getTimeInMillis());
				endTime.add(Calendar.MINUTE, random.nextInt(5));
				
				if (fromTime.before(Calendar.getInstance())) {
					fromTime.add(Calendar.DATE, 1);
					endTime.add(Calendar.DATE, 1);
				}
				
				logger.info("Calculated random times for {} of {} at {} and off at {}", randomItem.getFunction().getName(),
						randomItem.getDevice().getName(), fromTime.getTime().toGMTString(), endTime.getTime().toGMTString());
				
				randomItem.setCalculatedTimeToStart(fromTime);
				randomItem.setCalculatedTimeToEnd(endTime);
			}

			if (TimeUtils.requiresActivating(randomItem)) {
				lightwaveUtils.activate(randomItem);
			}
			
			if (TimeUtils.requiresDeactivating(randomItem)) {
				lightwaveUtils.deactivate(randomItem);
				randomItem.setCalculatedTimeToEnd(null);
			}
		}
	}

	private void turnEverythingOff() {
		itemsToSchedule.stream().forEach(item -> lightwaveUtils.deactivate(item));
	}

	public void sendPairingCode()
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
