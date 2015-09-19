package tom.lightwaverf.model;

/**
 * Used to store variables set at runtime
 * @author tom.chamberlain
 *
 */
public abstract class ItemState {
	
	private boolean activated;
	private Device device;
	private Function function;
	

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}
	
	

}
