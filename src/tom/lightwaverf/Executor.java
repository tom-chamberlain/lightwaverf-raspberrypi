package tom.lightwaverf;

import java.util.List;

import tom.lightwaverf.model.ScheduledItem;
import tom.lightwaverf.timekeeping.LightwaveRfUtils;
import tom.lightwaverf.timekeeping.TimeUtils;

public class Executor {
	
	private List<ScheduledItem> itemsToSchedule;
	
	private TimeUtils timeUtils;
	
	private LightwaveRfUtils lightwaveUtils;
	
	/**
	 * Scans all items loaded and turns off or on those devices if required
	 */
	public void processItems()
	{
		for (ScheduledItem item : itemsToSchedule)
		{
			if (timeUtils.requiresActivating(item))
			{
				lightwaveUtils.activate(item);
			}
			
			if (timeUtils.requiresDeactivating(item))
			{
				lightwaveUtils.deactivate(item);
			}
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

	public void setTimeUtils(TimeUtils timeUtils) {
		this.timeUtils = timeUtils;
	}

	public void setLightwaveUtils(LightwaveRfUtils lightwaveUtils) {
		this.lightwaveUtils = lightwaveUtils;
	}

	
	
	
}
