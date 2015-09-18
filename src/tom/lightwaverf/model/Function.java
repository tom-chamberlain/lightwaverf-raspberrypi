package tom.lightwaverf.model;

/**
 * Represents what the device should do
 * @author tom.chamberlain
 *
 */
public class Function {
	
	private String name;
	private String code;
	
	/**
	 * Returns the friendly name for the function
	 * @return
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Returns the code that represents this function
	 * that LightwaveRF interprets
	 * @return
	 */
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	

}
