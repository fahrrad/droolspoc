package com.gb.cropdesign.droolspoc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
	 * The plant is non-conform from the beginning. This tests checks only if
	 * the rules will not set the plant to being conform
	 */
	@Test
	public void testPlantisNotSetToConform() {
		logger.debug(">> testNonConformPlant");
		assertTrue(ksession.getObjects().isEmpty());
		Plant p1 = new Plant("e022.0221.00.1.5", "RPD51");

		PcrResult r1 = new PcrResult(p1, PcrTarget.PROM, false);
		PcrResult r2 = new PcrResult(p1, PcrTarget.TERM, true);
		PcrResult r3 = new PcrResult(p1, PcrTarget.TZEIN, false);

		ksession.insert(r1);
		ksession.insert(r2);
		ksession.insert(r3);
		ksession.insert(p1);

		ksession.fireAllRules();

		assertFalse(p1.getConform());
	}

	@Test
	public void testConformPositivePlant() {
		logger.debug(">> testConformPositivePlant");

		assertTrue(ksession.getObjects().isEmpty());

		Plant p1 = new Plant("e022.0221.00.1.5", "RPD51");

		PcrResult r1 = new PcrResult(p1, PcrTarget.PROM, true);
		PcrResult r2 = new PcrResult(p1, PcrTarget.TERM, true);
		PcrResult r3 = new PcrResult(p1, PcrTarget.TZEIN, true);

		ksession.insert(r1);
		ksession.insert(r2);
		ksession.insert(r3);
		ksession.insert(p1);

		ksession.fireAllRules();

		assertTrue(p1.getConform());
	}

	@Test
	public void testConformNegativePlant() {
		logger.debug(">> testConformNegativePlant");

		assertTrue(ksession.getObjects().isEmpty());

		Plant p1 = new Plant("e022.0221.00.1.5", "RPD51");

		PcrResult r1 = new PcrResult(p1, PcrTarget.PROM, false);
		PcrResult r2 = new PcrResult(p1, PcrTarget.TERM, false);
		PcrResult r3 = new PcrResult(p1, PcrTarget.TZEIN, false);

		ksession.insert(r1);
		ksession.insert(r2);
		ksession.insert(r3);
		ksession.insert(p1);

		ksession.fireAllRules();

		assertTrue(p1.getConform());

	}

	@Test
	public void testTransgene() {
		logger.debug(">> testTransgene");

		Plant p1 = new Plant("some transgene plant", "RPD49");

		PcrResult r1 = new PcrResult(p1, PcrTarget.PROM, true);
		ksession.insert(p1);
		ksession.insert(r1);

		ksession.fireAllRules();
		assertTrue(p1.getTransgene());
	}

	@Test
	public void testUnusedResults() {
		logger.debug(">> testUnusedResults");

		Plant p1 = new Plant("some transgene plant", "RPD51");

		PcrResult r1 = new PcrResult(p1, PcrTarget.PROM, true);

		ksession.insert(p1);

		r1.setUsed(false);
		assertFalse(r1.isUsed());

		ksession.insert(r1);
		ksession.fireAllRules();

		assertFalse(ksession.getObjects().contains(r1));

	}
	
	@Test
	public void testPlantWithNoPositiveResultsIsNotTransgene(){
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
		
		assertFalse(p1.getTransgene());
		
	}
	
	@Test
	public void testHomozygousPlant() {
		logger.debug(">> testHomozygousPlant");
		
		Plant plant = new Plant("some test plant", "rpd324");
		
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
		result.setCopyNr(1.8f);
		
		ksession.insert(plant);
		ksession.insert(result);
		
		ksession.fireAllRules();
		
		assertEquals(Zygocity.HETEROZYGOUS, plant.getZygocity());
		
	}
	
	@Test
	public void testPlantsToField(){
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
		
		Plant plantWildRabbit = new Plant("Crazy mambo jambo", "rpd324");
		plantWildRabbit.setConform(true);
		
		ksession.insert(p2f);
		ksession.insert(plant1);
		ksession.insert(plant2);
		ksession.insert(plant3);
		ksession.insert(plantWildRabbit);
		
		ksession.fireAllRules();
		
		assertEquals(3, p2f.getPlantList().size() );
		assertTrue(p2f.getPlantList().contains(plant1));
		assertTrue(p2f.getPlantList().contains(plant2));
		assertTrue(p2f.getPlantList().contains(plantWildRabbit));
		
	}

	@After
	public void destroySession() {
		ksession.dispose();
	}
}
