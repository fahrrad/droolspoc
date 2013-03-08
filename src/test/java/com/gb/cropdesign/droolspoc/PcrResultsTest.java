package com.gb.cropdesign.droolspoc;

import static org.junit.Assert.*;

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
public class PcrResultsTest {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(PcrResultsTest.class);

	@Autowired
	private KnowledgeBase kbase;

	private StatefulKnowledgeSession ksession;

	@Before
	public void setupSession() {
		ksession = kbase.newStatefulKnowledgeSession();
	}

	@Test
	public void testNonConformPlant() {
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
	public void testConformPlant() {
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

	@After
	public void destroySession() {
		ksession.dispose();
	}
}
