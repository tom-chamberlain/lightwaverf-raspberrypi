package com.stellaromeo.lightwaverf;

import java.util.Vector;

public class MasterSwitch {
	
	
	public static void main(String[] args) throws InterruptedException {
		
		if (args.length > 0)
		UDPSender.send(Command.init());
				
		System.out.println("Switch started");

			Vector<Action> actions = new Vector<Action>();

			actions.add(new Action(new TimeFrame("10:00","10:30",
					Days.Everyday), Device.BEDROOM_LIGHTS, Function.OFF));
			
			actions.add(new Action(new TimeFrame(SunriseSunset.SUNSET + "|17:30","22:00",
					Days.Everyday), Device.LIVING_ROOM_LIGHTS, Function.DIM_10, true));
			
			actions.add(new Action(new TimeFrame("22:00","23:58",
					Days.Everyday), Device.LIVING_ROOM_LIGHTS, Function.DIM_3));
	
			actions.add(new Action(new TimeFrame(SunriseSunset.SUNSET + "|17:30","22:30",
					Days.Everyday), Device.LIVING_ROOM_TV_LAMPS, Function.ON));
			
			actions.add(new Action(new TimeFrame(SunriseSunset.SUNSET + "|17:30","22:45",
					Days.Everyday), Device.LIVING_ROOM_UPLIGHTER, Function.ON));
			
			
			actions.add(new Action(new TimeFrame("06:40", SunriseSunset.SUNRISE + "|07:30",
					Days.Weekdays), Device.KITCHEN_LIGHTS, Function.DIM_27));
			actions.add(new Action(new TimeFrame(SunriseSunset.SUNSET + "|17:30","20:30",
					Days.Everyday), Device.KITCHEN_LIGHTS, Function.DIM_27,true));
			actions.add(new Action(new TimeFrame("20:32","23:00",
					Days.Everyday), Device.KITCHEN_LIGHTS, Function.DIM_3));
	
			actions.add(new Action(new TimeFrame("07:40",SunriseSunset.SUNRISE + "|07:30",
					Days.Weekdays), Device.DOWNSTAIRS_HALL_LIGHTS, Function.DIM_20));
			actions.add(new Action(new TimeFrame(SunriseSunset.SUNSET + "|17:30","23:00",
					Days.Everyday), Device.DOWNSTAIRS_HALL_LIGHTS, Function.DIM_20, true));
			actions.add(new Action(new TimeFrame("23:00","23:59",
					Days.Everyday), Device.DOWNSTAIRS_HALL_LIGHTS, Function.DIM_3));
			
			
			actions.add(new Action(new TimeFrame("06:00",SunriseSunset.SUNRISE + "|07:30",
					Days.Weekdays), Device.OUTSIDE_LAMP, Function.ON));
			actions.add(new Action(new TimeFrame(SunriseSunset.SUNSET,"23:59",
					Days.Everyday), Device.OUTSIDE_LAMP, Function.ON));
			
			actions.add(new Action(new TimeFrame("08:00","08:20",
					Days.Everyday), Device.DOWNSTAIRS_TOILET_LIGHTS, Function.OFF));
			actions.add(new Action(new TimeFrame("17:30","23:59",
					Days.Everyday), Device.DOWNSTAIRS_TOILET_LIGHTS, Function.OFF));
			
			actions.add(new Action(new TimeFrame("06:45",SunriseSunset.SUNRISE + "|07:30",
					Days.Weekdays), Device.UPSTAIRS_HALL_LIGHTS, Function.DIM_23));
			actions.add(new Action(new TimeFrame(SunriseSunset.SUNSET + "|17:30","22:30",
					Days.Everyday), Device.UPSTAIRS_HALL_LIGHTS, Function.DIM_3));

			
			actions.add(new Action(new TimeFrame("19:30","22:00",
					Days.Everyday), Device.OUTDOORS_FAIRY, Function.ON));
			
			actions.add(new Action(new TimeFrame("21:30","23:59",
					Days.Everyday), Device.OUTDOORS_FLOOD, Function.ON));
			
			
			// Random events
			
			actions.add(new Action(new TimeFrame("01:21", "01:23",
					Days.Weekdays), Device.OUTSIDE_LAMP, Function.ON));
			
			actions.add(new Action(new TimeFrame("02:49","02:50",
					Days.Everyday), Device.OUTSIDE_LAMP, Function.ON));
			

			actions.add(new Action(new TimeFrame("01:31", "01:32",
					Days.Weekdays), Device.DOWNSTAIRS_HALL_LIGHTS, Function.DIM_50));
			
			actions.add(new Action(new TimeFrame("02:39","02:41",
					Days.Everyday), Device.KITCHEN_LIGHTS, Function.DIM_60));
			
			actions.add(new Action(new TimeFrame("02:01","02:02",
					Days.Everyday), Device.DOWNSTAIRS_TOILET_LIGHTS, Function.DIM_83));
			
			
			
			
			
			// MAKE SURE OFF
			
			actions.add(new Action(new TimeFrame("04:00","04:50", Days.Everyday), Device.OUTSIDE_LAMP, Function.OFF));
			actions.add(new Action(new TimeFrame("04:01","04:51", Days.Everyday), Device.OUTDOORS_FAIRY, Function.OFF));
			actions.add(new Action(new TimeFrame("04:02","04:52", Days.Everyday), Device.OUTDOORS_FLOOD, Function.OFF));
			actions.add(new Action(new TimeFrame("04:03","04:53", Days.Everyday), Device.DOWNSTAIRS_HALL_LIGHTS, Function.OFF));
			actions.add(new Action(new TimeFrame("04:04","04:54", Days.Everyday), Device.DOWNSTAIRS_TOILET_LIGHTS, Function.OFF));
			actions.add(new Action(new TimeFrame("04:05","04:55", Days.Everyday), Device.KITCHEN_LIGHTS, Function.OFF));
			actions.add(new Action(new TimeFrame("04:06","04:56", Days.Everyday), Device.BEDROOM_LIGHTS, Function.OFF));
			actions.add(new Action(new TimeFrame("04:06","04:56", Days.Everyday), Device.LIVING_ROOM_LIGHTS, Function.OFF));
			actions.add(new Action(new TimeFrame("04:06","04:56", Days.Everyday), Device.LIVING_ROOM_TV_LAMPS, Function.OFF));
			actions.add(new Action(new TimeFrame("04:06","04:56", Days.Everyday), Device.LIVING_ROOM_UPLIGHTER, Function.OFF));
			actions.add(new Action(new TimeFrame("04:06","04:56", Days.Everyday), Device.UPSTAIRS_HALL_LIGHTS, Function.OFF));


			// Set all off
			for (Action action : actions)
			{
					UDPSender.send(Command.generateDeactivate(action));
			}
			
			
					
			while (true)
			{
				Thread.sleep(1000);
			
				for (Action action : actions)
				{
					if (action.requiresActivating())
					{
						UDPSender.send(Command.generate(action));
						action.setAsActive(true);
					}
					
					if (action.requiresDeactivating())
					{
						UDPSender.send(Command.generateDeactivate(action));
						action.setAsActive(false);
					}
				}
				
							
			}
					
		

	}

}
