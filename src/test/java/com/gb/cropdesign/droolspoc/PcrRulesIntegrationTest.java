package com.gb.cropdesign.droolspoc;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.runtime.StatefulKnowledgeSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gb.cropdesign.droolspoc.service.PcrResultServiceI;
import com.gb.cropdesign.droolspoc.service.PlantServiceI;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/rules-application-context.xml" })
public class PcrRulesIntegrationTest {
	
	@Autowired
	private PlantServiceI plantService;
	
	@Autowired
	private PcrResultServiceI pcrResultService;
	
	@Autowired
	KnowledgeBase kbase;
	
	@Autowired
	StatefulKnowledgeSession ksession;
	
	private final Logger logger = LoggerFactory.getLogger(PcrRulesIntegrationTest.class);
	
	@Before
	public void setup(){
		ksession = kbase.newStatefulKnowledgeSession();
	}
	
	
	@Test
	public void testLoadingPlantsInList() {
		List<Plant> plantsList = plantService.getAllPlants();
		
		assertEquals(1975, plantsList.size());
	}
	
	@Test
	public void testAddingPlantsToContext(){
		List<Plant> plantsList = plantService.getAllPlants();
		
		for( Plant plant : plantsList ){
			ksession.insert(plant);
		}
		
		for( PcrResult pcrResult : pcrResultService.getAllPcrResults() ){
			ksession.insert(pcrResult);
		}
		
		ksession.fireAllRules();
		
		logger.debug("done!");
	}
	
	@Test 
	public void testGettingAllPcrResults(){
		List<PcrResult> allPcrResults = pcrResultService.getAllPcrResults();
		
		assertEquals(6049, allPcrResults.size());
	}
	
	@After
	public void afterTest(){
		ksession.dispose();
	}
	
	
}
