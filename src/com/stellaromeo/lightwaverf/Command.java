package com.stellaromeo.lightwaverf;

public class Command {
	
	public static String generate(Action action)
	{
		return "001,!" + action.getDevice().roomToSend + action.getFunction().functionToSend + "|" + action.getDevice().name() + "|" + action.getFunction().functionToSend + "\n";
	}
	
	public static String generateDeactivate(Action action)
	{
		return "001,!" + action.getDevice().roomToSend + Function.OFF.functionToSend + "|" + action.getDevice().name() + "|Off|\n";
	}


	
	public static String init()
	{
		return "001,!F*p\n";
	}
}
