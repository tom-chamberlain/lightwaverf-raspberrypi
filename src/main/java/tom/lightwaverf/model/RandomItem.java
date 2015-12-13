package tom.lightwaverf.model;

import java.util.Calendar;

public class RandomItem extends ItemState {
	
	private String fromTime;
	private String endTime;
		
	private Calendar calculatedTimeToStart;
	private Calendar calculatedTimeToEnd;
	
	
	public String getFromTime() {
		return fromTime;
	}
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Calendar getCalculatedTimeToStart() {
		return calculatedTimeToStart;
	}
	public void setCalculatedTimeToStart(Calendar calculatedTimeToStart) {
		this.calculatedTimeToStart = calculatedTimeToStart;
	}
	public Calendar getCalculatedTimeToEnd() {
		return calculatedTimeToEnd;
	}
	public void setCalculatedTimeToEnd(Calendar calculatedTimeToEnd) {
		this.calculatedTimeToEnd = calculatedTimeToEnd;
	}
	
	
	

}
