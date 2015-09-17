package com.stellaromeo.lightwaverf;

public enum Device {
	
	LIVING_ROOM_LIGHTS("R1D1"),
	LIVING_ROOM_TV_LAMPS("R1D2"),
	LIVING_ROOM_UPLIGHTER("R1D3"),
	KITCHEN_LIGHTS("R3D1"),
	DOWNSTAIRS_HALL_LIGHTS("R2D1"),
	OUTSIDE_LAMP("R2D2"),
	DOWNSTAIRS_TOILET_LIGHTS("R5D2"),
	UPSTAIRS_HALL_LIGHTS("R5D1"),
	BEDROOM_LIGHTS("R4D1"),
	UPSTAIRS_TWIG_LIGHTS("R5D3"),
	UPSTAIRS_HALL_SOCKET("R5D4"),
	OUTDOORS_FLOOD("R6D2"),
	OUTDOORS_FAIRY("R6D1"),
	;

	String roomToSend;
	
	private Device(String roomToSend)
	{
		this.roomToSend = roomToSend;
	}
	
	public String getDeviceCode()
	{
		return roomToSend;
	}

}
