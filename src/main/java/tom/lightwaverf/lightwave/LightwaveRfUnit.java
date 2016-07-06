package tom.lightwaverf.lightwave;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Represents the LightwaveRF Wifi unit that commands are sent to
 */
public class LightwaveRfUnit {

	private String hostname;
	private int port;
	private int numberOfTimesToSendCommand;
	private int millisecondsToWaitBetweenSendingCommands;
	
	private Logger logger = LogManager.getLogger();

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getNumberOfTimesToSendCommand() {
		return numberOfTimesToSendCommand;
	}

	public void setNumberOfTimesToSendCommand(int numberOfTimesToSendCommand) {
		this.numberOfTimesToSendCommand = numberOfTimesToSendCommand;
	}

	public int getMillisecondsToWaitBetweenSendingCommands() {
		return millisecondsToWaitBetweenSendingCommands;
	}

	public void setMillisecondsToWaitBetweenSendingCommands(
			int millisecondsToWaitBetweenSendingCommands) {
		this.millisecondsToWaitBetweenSendingCommands = millisecondsToWaitBetweenSendingCommands;
	}

	/**
	 * Sends the UDP command to the LightwaveRF device
	 * @param command the command to send
	 */
	public void sendCommand(String command)
	{
		logger.debug("Issuing command {} ", command);
		
		for (int i = 0; i < numberOfTimesToSendCommand; i++) {
			try (DatagramSocket datagram = new DatagramSocket()) {
				byte[] message = command.getBytes();
				datagram.send(new DatagramPacket(message, message.length, InetAddress.getByName(hostname), port));
				Thread.sleep(millisecondsToWaitBetweenSendingCommands);
			} catch (Exception e) {
				logger.error("Unable to send UDP command", e);
			}
		}
	}	
	
}
