package com.gb.cropdesign.droolspoc;

import static org.junit.Assert.*;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PcrResultsTest {

	private static KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();

	private static final Logger logger = LoggerFactory
			.getLogger(PcrResultsTest.class);
	
	private static KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
			.newKnowledgeBuilder();
	
	private StatefulKnowledgeSession ksession;
	
	@BeforeClass
	public static void setupTestClass(){
		logger.info("<< SetupClass");
		
		kbuilder.add(ResourceFactory.newClassPathResource("PcrRules.drl"), ResourceType.DRL);
		
		if (kbuilder.hasErrors()) {
			System.err.println(kbuilder.getErrors().toString());
		}
		
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
	}
	
	@Before
	public void setupTest(){
		logger.info("<< setup");
		ksession = kbase.newStatefulKnowledgeSession();
	}
	
	@Test
	public void testNonConformPlant() {
		Plant p1 = new Plant("e022.0221.00.1.5", "RPD51");

		PcrResult r1 = new PcrResult(p1, PcrTarget.PROM, false);
		PcrResult r2 = new PcrResult(p1, PcrTarget.TERM, true);
		PcrResult r3 = new PcrResult(p1, PcrTarget.TZEIN, false);
		
		ksession.insert(r1);
		ksession.insert(r2);
		ksession.insert(r3);
		ksession.insert(p1);
		
		ksession.fireAllRules();
		
		assertFalse(p1.isConform());
	}
	
	@Test
	public void testConformPlant() {
		Plant p1 = new Plant("e022.0221.00.1.5", "RPD51");

		PcrResult r1 = new PcrResult(p1, PcrTarget.PROM, true);
		PcrResult r2 = new PcrResult(p1, PcrTarget.TERM, true);
		PcrResult r3 = new PcrResult(p1, PcrTarget.TZEIN, true);
		
		ksession.insert(r1);
		ksession.insert(r2);
		ksession.insert(r3);
		ksession.insert(p1);
		
		ksession.fireAllRules();
		
		assertTrue(p1.isConform());
	}
	
	@After
	public void disposeAfterTest(){
		ksession.dispose();
	}

}
