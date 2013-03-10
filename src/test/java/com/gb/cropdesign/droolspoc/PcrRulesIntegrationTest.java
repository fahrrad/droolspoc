package com.gb.cropdesign.droolspoc;

import static org.junit.Assert.*;


import java.util.List;

import javax.sql.DataSource;

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
	
	@Autowired
	DataSource datasource;
	
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
	public void testAdding1PlantFromDb(){
		Plant plant = plantService.getPlant("12OS0.001.593.905-003");
		assertNotNull(plant);

		List<PcrResult> pcrResults = pcrResultService.getPcrResultForPlantName("12OS0.001.593.905-003");
		assertEquals(8, pcrResults.size());
		
		ksession.insert(plant);
		for(Object fact: pcrResults){
			ksession.insert(fact);
		}
		
		ksession.fireAllRules();
		
		assertTrue(plant.getConform());
		assertTrue(plant.getTransgene());
		
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
		
		long start = System.currentTimeMillis();
		ksession.fireAllRules();		
		long end = System.currentTimeMillis();
		
		logger.info("All pcr results executed in " + String.valueOf(end-start) + "ms! ");
		assertTrue((end - start) < 500);
		
		
		int indexOfConfromPlant = plantsList.indexOf(new Plant("12OS0.001.593.905-003", ""));
		assertTrue(plantsList.get(indexOfConfromPlant).getConform());
		assertTrue(plantsList.get(indexOfConfromPlant).getTransgene());
		
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
