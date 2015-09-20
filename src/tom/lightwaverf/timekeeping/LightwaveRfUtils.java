package tom.lightwaverf.timekeeping;

import tom.lightwaverf.lightwave.LightwaveRfUnit;
import tom.lightwaverf.model.ItemState;
import tom.lightwaverf.model.ScheduledItem;

public class LightwaveRfUtils {
	
	private LightwaveRfUnit unit;
	
	/**
	 * Turns on or set the dim level for a device
	 * @param item
	 */
	public void activate(ItemState item)
	{
		item.setActivated(true);
		
		if (item instanceof ScheduledItem && ((ScheduledItem) item).getStartTime() == null)
		{
			return;
		}
		
		unit.sendCommand(generateActivationCommand(item));
	}
	

	/**
	 * Turns off the device
	 * @param item
	 */
	public void deactivate(ItemState item)
	{
		item.setActivated(false);
		
		if (item instanceof ScheduledItem && ((ScheduledItem) item).getEndTime() == null)
		{
			return;
		}
		
		unit.sendCommand(generateDeactivateCommand(item));
	}
	
	

	private String generateDeactivateCommand(ItemState item) {
		
		return "001,!" + item.getDevice().getCode() + "F0" 
				+ "|" + item.getDevice().getName() + "|Off|\n";
	}
	
	private String generateActivationCommand(ItemState item) {
		return "001,!" + item.getDevice().getCode() + item.getFunction().getCode() 
				+ "|" + item.getDevice().getName() + "|" + item.getFunction().getCode() + "\n";

	}
	
	
	public void sendInitCode()
	{
		unit.sendCommand("001,!F*p\n");
	}



	public void setUnit(LightwaveRfUnit unit) {
		this.unit = unit;
	}
	
	
	

}
