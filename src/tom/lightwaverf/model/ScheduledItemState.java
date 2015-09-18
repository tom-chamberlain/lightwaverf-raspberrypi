package tom.lightwaverf.model;

/**
 * Used to store variables set at runtime
 * @author tom.chamberlain
 *
 */
public abstract class ScheduledItemState {
	
	private boolean activated;

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	
	

}
