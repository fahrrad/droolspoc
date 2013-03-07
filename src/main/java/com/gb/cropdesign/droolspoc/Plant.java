package com.gb.cropdesign.droolspoc;

public class Plant {

	private String name;

	/**
	 * indicates whether this plant is conform to the rules defined per RPD this
	 * will probably be defined by a combination of PCR results
	 */
	private boolean conform;

	/**
	 * Does this plant contains any genes introduced during transformation? If
	 * all the Pcr results are negative, the plant is null segregated, and
	 * transgene will be null
	 */
	private boolean transgene;

	/**
	 * Zygocity is determined by the copy number of a Gene.
	 */
	private Zygocity zygocity;

	/**
	 * The RPD number for this plant. 
	 */
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

	public boolean isTransgene() {
		return transgene;
	}

	public void setTransgene(boolean transgene) {
		this.transgene = transgene;
	}

	public Zygocity getZygocity() {
		return zygocity;
	}

	public void setZygocity(Zygocity zygocity) {
		this.zygocity = zygocity;
	}
	
	
}
