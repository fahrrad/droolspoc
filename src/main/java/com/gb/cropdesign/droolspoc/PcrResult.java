package com.gb.cropdesign.droolspoc;

public class PcrResult {

	private Plant plant;

	private PcrTarget pcrTarget;

	private boolean testGenePresent;

	public PcrResult(Plant plant, PcrTarget pcrTarget, boolean isTestGenePresent) {

		super();
		this.plant = plant;
		this.pcrTarget = pcrTarget;
		this.testGenePresent = isTestGenePresent;
	}

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

	public void setTestGenePresent(boolean isTestGenePresent) {
		this.testGenePresent = isTestGenePresent;
	}

}
