package com.gb.cropdesign.droolspoc;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlantTest {

	@Test
	public void testNewEmtpyPlant() {
		Plant p1 = new Plant("e00212", "RPD51");
		
		assertEquals("e00212", p1.getName());
		assertEquals("RPD51", p1.getRpd());
		assertNull( p1.getZygocity());
		assertNull(p1.getConform());
		assertNull(p1.getTransgene());
	}

}
