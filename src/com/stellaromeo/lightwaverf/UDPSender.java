package com.stellaromeo.lightwaverf;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class UDPSender {
	
	private static String host = "192.168.1.98";
	private static int port = 9760;
    final static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public static void send(String data) {
		
		Calendar calBst = Calendar.getInstance();
		calBst.setTimeZone(TimeZone.getTimeZone("Europe/London"));
		String date = calBst.get(Calendar.DATE) + "/" + calBst.get(Calendar.MONTH) + " " + calBst.get(Calendar.HOUR_OF_DAY) 
			+ ":" + calBst.get(Calendar.MINUTE) + ":" + calBst.get(Calendar.SECOND);
		System.out.println(date + " sending command");;
		
		for (int i = 0; i < 2; i++)
		{
			
			System.out.println("Sending attempt " + (i + 1) + " " + data);
	
			try {
				byte[] message = data.getBytes();
	
				DatagramSocket dsocket = new DatagramSocket();
				dsocket.send(new DatagramPacket(message, message.length,
						InetAddress.getByName(host), port));
				dsocket.close();
				Thread.sleep(400);
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}

}
