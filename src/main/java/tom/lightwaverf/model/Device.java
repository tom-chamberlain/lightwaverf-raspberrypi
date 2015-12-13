package tom.lightwaverf.model;

/**
 * Represents the LightwaveRF device
 * @author tom.chamberlain
 *
 */
public class Device {

	private String name;
	private String code;
	
	
	/**
	 * Returns the friendly name for this device
	 * @return
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the code that the LightwaveRF uses for this device
	 * that will have been set when the device was paired with the
	 * phone app
	 * @return
	 */
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

}
