package com.gb.cropdesign.droolspoc;

public enum Zygocity {

	/**
	 * a cell homozygous is when identical alleles of the gene are present on
	 * both homologous chromosomes. [wikipedia]
	 */
	HOMOZYGOUS("homozygous"),

	/**
	 * A diploid organism is heterozygous at a gene locus when its cells contain
	 * two different alleles of a gene. [wikipedia]
	 */
	HETEROZYGOUS("heterozygous"),
	
	/**
	 * When not detected, or when unsure, the zygocity is undertermined
	 */
	UNDETERMINED("undertermined");

	private final String name;

	private Zygocity(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
