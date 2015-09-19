package tom.lightwaverf.model;

/**
 * There will be one of these objects per item scheduled
 * @author tom.chamberlain
 *
 */
public class ScheduledItem extends ItemState {
	
	private boolean onlyWeekdays = false;
	private boolean onlyWeekends = false;

	private String startTime;
	private String endTime;
	
	private boolean ignoreIfSunAlreadyRisen = false; 
	private boolean endAtSunriseIfSunriseBeforeEndTime = false;	
	private boolean delayStartTimeToSunsetIfSunNotSet = false;
	
	
	public boolean isOnlyWeekdays() {
		return onlyWeekdays;
	}
	public void setOnlyWeekdays(boolean onlyWeekdays) {
		this.onlyWeekdays = onlyWeekdays;
	}
	public boolean isOnlyWeekends() {
		return onlyWeekends;
	}
	public void setOnlyWeekends(boolean onlyWeekends) {
		this.onlyWeekends = onlyWeekends;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public boolean isIgnoreIfSunAlreadyRisen() {
		return ignoreIfSunAlreadyRisen;
	}
	public void setIgnoreIfSunAlreadyRisen(boolean ignoreIfSunAlreadyRisen) {
		this.ignoreIfSunAlreadyRisen = ignoreIfSunAlreadyRisen;
	}
	public boolean isEndAtSunriseIfSunriseBeforeEndTime() {
		return endAtSunriseIfSunriseBeforeEndTime;
	}
	public void setEndAtSunriseIfSunriseBeforeEndTime(
			boolean endAtSunriseIfSunriseBeforeEndTime) {
		this.endAtSunriseIfSunriseBeforeEndTime = endAtSunriseIfSunriseBeforeEndTime;
	}
	public boolean isDelayStartTimeToSunsetIfSunNotSet() {
		return delayStartTimeToSunsetIfSunNotSet;
	}
	public void setDelayStartTimeToSunsetIfSunNotSet(
			boolean delayStartTimeToSunsetIfSunNotSet) {
		this.delayStartTimeToSunsetIfSunNotSet = delayStartTimeToSunsetIfSunNotSet;
	}

	
	

}
