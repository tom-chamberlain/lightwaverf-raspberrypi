package com.stellaromeo.lightwaverf;

public enum Function {
	
	ON("F1"),
	OFF("F0"),
	DIM_100("FdP31"),
	DIM_97("FdP30"),
	DIM_93("FdP29"),
	DIM_90("FdP28"),
	DIM_87("FdP27"),
	DIM_83("FdP26"),
	DIM_80("FdP25"),
	DIM_76("FdP24"),
	DIM_73("FdP23"),
	DIM_70("FdP22"),
	DIM_67("FdP21"),
	DIM_63("FdP20"),
	DIM_60("FdP19"),
	DIM_57("FdP18"),
	DIM_53("FdP17"),
	DIM_50("FdP16"),
	DIM_47("FdP15"),
	DIM_43("FdP14"),
	DIM_40("FdP13"),
	DIM_37("FdP12"),
	DIM_33("FdP11"),
	DIM_30("FdP10"),
	DIM_27("FdP9"),
	DIM_23("FdP7"),
	DIM_20("FdP6"),
	DIM_17("FdP5"),
	DIM_13("FdP4"),
	DIM_10("FdP3"),
	DIM_7("FdP2"),
	DIM_3("FdP1");

	
	String functionToSend;
	
	private Function(String functionToSend)
	{
		this.functionToSend = functionToSend;
	}
	
	public String getFunctionCode()
	{
		return functionToSend;
	}

}
