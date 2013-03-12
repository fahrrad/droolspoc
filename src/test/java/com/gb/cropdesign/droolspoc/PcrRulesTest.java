package com.gb.cropdesign.droolspoc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.drools.KnowledgeBase;
import org.drools.runtime.StatefulKnowledgeSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/rules-application-context.xml" })
public class PcrRulesTest {

	private static final Logger logger = LoggerFactory
			.getLogger(PcrRulesTest.class);

	@Autowired
	private KnowledgeBase kbase;

	private StatefulKnowledgeSession ksession;

	@Before
	public void setupSession() {
		ksession = kbase.newStatefulKnowledgeSession();
	}

	/**
	 * When a plant has a pcr result with target 'TZEIN', the copy number can be
	 * used to calculate the zygocity.
	 * 
	 * - When the copyNr is between 0 and 1.8, the plant is homozygous. - When
	 * the copyNr is between 1.8 and 3, the plant is heterozygous.
	 */
	@Test
	public void testHomozygousPlant() {
		logger.debug(">> testHomozygousPlant");

		Plant plant = new Plant("some test plant", "rpd57");

		PcrResult result = new PcrResult(plant, PcrTarget.TZEIN, true);
		result.setCopyNr(0.8f);

		ksession.insert(plant);
		ksession.insert(result);

		ksession.fireAllRules();

		assertEquals(Zygocity.HOMOZYGOUS, plant.getZygocity());

	}

	@Test
	public void testHeterozygousPlant() {
		logger.debug(">> testHomozygousPlant");

		Plant plant = new Plant("some test plant", "rpd324");

		PcrResult result = new PcrResult(plant, PcrTarget.TZEIN, true);
		result.setCopyNr(2.4f);

		ksession.insert(plant);
		ksession.insert(result);

		ksession.fireAllRules();

		assertEquals(Zygocity.HETEROZYGOUS, plant.getZygocity());

	}

	/**
	 * When one of the PCR tests that are executed on the plant are positive,
	 * the plant is considered transgene
	 */
	@Test
	public void testTransgene() {
		logger.debug(">> testTransgene");

		Plant p1 = new Plant("some transgene plant", "RPD49");
		
		assertNull(p1.getTransgene());
		
		PcrResult r1 = new PcrResult(p1, PcrTarget.PROM, true);
		ksession.insert(p1);
		ksession.insert(r1);

		ksession.fireAllRules();
		
		assertNotNull(p1.getTransgene());
		assertTrue(p1.getTransgene());
	}

	/**
	 * Likewise, when no positive test exist for a plant, it  is considered
	 * not transgene
	 */
	@Test
	public void testPlantWithNoPositiveResultsIsNotTransgene() {
		logger.debug(">> testPlantWithNoPositiveResultsIsNotTransgene");

		Plant p1 = new Plant("some transgene plant", "RPD51");

		PcrResult r1 = new PcrResult(p1, PcrTarget.PROM, false);
		PcrResult r2 = new PcrResult(p1, PcrTarget.PROM, false);
		PcrResult r3 = new PcrResult(p1, PcrTarget.PROM, false);

		ksession.insert(p1);
		ksession.insert(r1);
		ksession.insert(r2);
		ksession.insert(r3);

		ksession.fireAllRules();
		
		assertNotNull(p1.getTransgene());
		assertFalse(p1.getTransgene());

	}

	/**
	 * When the is_used flag is set to false for a Pcr Result, that result
	 * should in no way be used for calculating zygocity, transgenity and/ or
	 * conformity
	 * 
	 */
	@Test
	public void testUnusedResults() {
		logger.debug(">> testUnusedResults");

		Plant p1 = new Plant("some transgene plant", "RPD51");

		PcrResult r1 = new PcrResult(p1, PcrTarget.PROM, true);

		ksession.insert(p1);

		r1.setUsed(false);

		ksession.insert(r1);
		
		assertTrue(ksession.getObjects().contains(r1));
		ksession.fireAllRules();

		assertFalse(ksession.getObjects().contains(r1));

	}

	/**
	 * When a plant, belonging to rpd57 has 3 POSITIVE tests for targets
	 * PROM_CDS___PRO0129_CDS4107, PROM_CDS___PRO0170_CDS0045 and TERMINATOR the
	 * plant is considered conform.
	 * 
	 */
	@Test
	public void testConformPositivePlant() {
		logger.debug(">> testConformPositivePlant");

		assertTrue(ksession.getObjects().isEmpty());

		Plant p1 = new Plant("e022.0221.00.1.5", "rpd57");

		PcrResult r1 = new PcrResult(p1, PcrTarget.PROM_CDS___PRO0129_CDS4107,
				true);
		PcrResult r2 = new PcrResult(p1, PcrTarget.TERMINATOR, true);
		PcrResult r3 = new PcrResult(p1, PcrTarget.PROM_CDS___PRO0170_CDS0045,
				true);

		ksession.insert(r1);
		ksession.insert(r2);
		ksession.insert(r3);
		ksession.insert(p1);

		ksession.fireAllRules();
		
		assertNotNull(p1.getConform());
		assertTrue(p1.getConform());
	}

	/**
	 * When a plant, belonging to rpd57 has 3 NEGATIVE tests for targets
	 * PROM_CDS___PRO0129_CDS4107, PROM_CDS___PRO0170_CDS0045 and TERMINATOR the
	 * plant is considered conform.
	 */
	@Test
	public void testConformNegativePlant() {
		logger.debug(">> testConformNegativePlant");

		assertTrue(ksession.getObjects().isEmpty());

		Plant p1 = new Plant("e022.0221.00.1.5", "rpd57");

		PcrResult r1 = new PcrResult(p1, PcrTarget.PROM_CDS___PRO0129_CDS4107,
				false);
		PcrResult r2 = new PcrResult(p1, PcrTarget.TERMINATOR, false);
		PcrResult r3 = new PcrResult(p1, PcrTarget.PROM_CDS___PRO0170_CDS0045,
				false);

		ksession.insert(r1);
		ksession.insert(r2);
		ksession.insert(r3);
		ksession.insert(p1);

		ksession.fireAllRules();

		assertNotNull(p1.getConform());
		assertTrue(p1.getConform());

	}

	/**
	 * When these conditions are not fulfilled, a plant should not be set to
	 * conform!!!
	 */
	@Test
	public void testPlantisNotSetToConform() {
		logger.debug(">> testNonConformPlant");
		assertTrue(ksession.getObjects().isEmpty());
		Plant p1 = new Plant("e022.0221.00.1.5", "rpd57");

		PcrResult r1 = new PcrResult(p1, PcrTarget.PROM_CDS___PRO0129_CDS4107,
				false);
		PcrResult r2 = new PcrResult(p1, PcrTarget.TERMINATOR, true);
		PcrResult r3 = new PcrResult(p1, PcrTarget.PROM_CDS___PRO0170_CDS0045,
				false);

		ksession.insert(r1);
		ksession.insert(r2);
		ksession.insert(r3);
		ksession.insert(p1);

		ksession.fireAllRules();

		assertNull(p1.getConform());
	}

	/**
	 * when sending plants to a field in France, the have to be conform and Not transgenic!
	 */
	@Test
	public void testPlantsToField() {
		PlantsToField p2f = new PlantsToField("france");

		Plant plant1 = new Plant("some test plant1", "rpd324");
		plant1.setTransgene(false);
		plant1.setConform(true);

		Plant plant2 = new Plant("some test plant2", "rpd324");
		plant2.setTransgene(false);
		plant2.setConform(true);

		Plant plant3 = new Plant("some test plant3", "rpd324");
		plant3.setTransgene(true);
		plant3.setConform(true);

		Plant plant4 = new Plant("some test plant 4", "rpd324");
		plant4.setConform(true);

		ksession.insert(p2f);
		ksession.insert(plant1);
		ksession.insert(plant2);
		ksession.insert(plant3);
		ksession.insert(plant4);

		ksession.fireAllRules();

		assertEquals(3, p2f.getPlantList().size());
		assertTrue(p2f.getPlantList().contains(plant1));
		assertTrue(p2f.getPlantList().contains(plant2));
		assertTrue(p2f.getPlantList().contains(plant4));

	}

	@After
	public void destroySession() {
		ksession.dispose();
	}
}
