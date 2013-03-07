package com.gb.cropdesign.droolspoc;

import static org.junit.Assert.*;

import org.junit.Test;

public class PcrResultTest {

	@Test
	public void testNewEmpyPcrResult() {
		Plant p1 = new Plant("e002122212", "rpd51");
		
		PcrResult pcrr1 = new PcrResult(p1, PcrTarget.PROM, true);
		
		assertTrue(pcrr1.isTestGenePresent());
		assertNull(pcrr1.getCopyNr());
		assertTrue(pcrr1.isUsed());
	}

}
