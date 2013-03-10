package com.gb.cropdesign.droolspoc;


public enum PcrTarget {

	TZEIN("tzein"), PROM("promotor"), TERM("terminator"),
	
	PROM_CDS___PRO0170_CDS0045("PROM_CDS___PRO0170_CDS0045"),
	SCREENABLE_MARKER("SCREENABLE_MARKER"),
	LEFT_BORDER("LEFT_BORDER"),
	TERMINATOR("TERMINATOR"),
	SELECTABLE_MARKER_EUKARYOTIC("SELECTABLE_MARKER_EUKARYOTIC"),
	SELECTABLE_MARKER_PROKARYOTIC("SELECTABLE_MARKER_PROKARYOTIC"),
	RIGHT_BORDER("RIGHT_BORDER"),
	PROM_CDS___PRO0129_CDS4107("PROM_CDS___PRO0129_CDS4107");

	private final String name;

	
	private PcrTarget(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	

	
	
}
