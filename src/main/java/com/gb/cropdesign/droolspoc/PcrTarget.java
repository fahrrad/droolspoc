package com.gb.cropdesign.droolspoc;

public enum PcrTarget {

	TZEIN("tzein"),
	PROM("promotor"),
	TERM("terminator");
	
	private final String name;

	private PcrTarget(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
