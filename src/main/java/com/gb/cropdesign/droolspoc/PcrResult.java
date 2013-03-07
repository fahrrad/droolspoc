package com.gb.cropdesign.droolspoc;

public class PcrResult {

	/**
	 * the plant this pcr was executed on
	 */
	private Plant plant;

	/**
	 * the target to test against.
	 */
	private PcrTarget pcrTarget;

	/**
	 * Was the target found during the Pcr test? If this is null, the test
	 * failed.
	 */
	private Boolean testGenePresent;

	/**
	 * When the copy number was determined, it is filled in here. Null means the
	 * copy number was not determined. The copy number is used to calculate the zygocity.
	 */
	private Float copyNr;
	
	/**
	 * should the results of this test be included in the calculation? 
	 */
	private boolean used;

	// constructors
	
	public PcrResult(Plant plant, PcrTarget pcrTarget, boolean isTestGenePresent) {
		super();
		this.plant = plant;
		this.pcrTarget = pcrTarget;
		this.testGenePresent = isTestGenePresent;
		this.copyNr = null;
		used = true;
	}
	
	// Getters and setters

	public Plant getPlant() {
		return plant;
	}

	public void setPlant(Plant plant) {
		this.plant = plant;
	}

	public PcrTarget getPcrTarget() {
		return pcrTarget;
	}

	public void setPcrTarget(PcrTarget pcrTarget) {
		this.pcrTarget = pcrTarget;
	}

	public boolean isTestGenePresent() {
		return testGenePresent;
	}

	public void setTestGenePresent(Boolean isTestGenePresent) {
		this.testGenePresent = isTestGenePresent;
	}
	
	public Float getCopyNr() {
		return copyNr;
	}

	public void setCopyNr(Float copyNr) {
		this.copyNr = copyNr;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

}
