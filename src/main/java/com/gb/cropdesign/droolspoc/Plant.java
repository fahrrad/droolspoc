package com.gb.cropdesign.droolspoc;

public class Plant {

	private String name;

	private boolean conform;

	private String rpd;

	public Plant(String name, String rpd) {
		super();
		this.name = name;
		this.rpd = rpd;
		this.conform = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isConform() {
		return conform;
	}

	public void setConform(boolean conform) {
		this.conform = conform;
	}

	public String getRpd() {
		return rpd;
	}

	public void setRpd(String rpd) {
		this.rpd = rpd;
	}
}
