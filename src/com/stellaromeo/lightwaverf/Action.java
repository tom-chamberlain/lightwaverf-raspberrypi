package com.stellaromeo.lightwaverf;

public class Action {
	
	private TimeFrame timeframeactivein;
	private Device device;
	private Function function;
	
	private boolean skipDeactivating = false;
	private boolean hasBeenActivated = false;
	
	public Action(TimeFrame timeframe, Device device, Function function)
	{
		this.timeframeactivein = timeframe;
		this.device = device;
		this.function = function;
	}
	
	public Action(TimeFrame timeframe, Device device, Function function, boolean skipDeactivating)
	{
		this(timeframe, device, function);
		this.skipDeactivating = skipDeactivating;
	}
	
	public void setAsActive(boolean active)
	{
		hasBeenActivated = active;
	}
	
	public boolean requiresActivating()
	{
		return !hasBeenActivated && timeframeactivein.isActive();
	}
	
	public boolean requiresDeactivating()
	{
		if ( (hasBeenActivated && !timeframeactivein.isActive()))
		{
			if (skipDeactivating)
			{
				hasBeenActivated = false;
				return false;
			}
			else
			{
				return true;
			}
		}
		
		return false;
	}

	public Device getDevice() {
		return device;
	}

	public Function getFunction() {
		return function;
	}
	
	
	

}
