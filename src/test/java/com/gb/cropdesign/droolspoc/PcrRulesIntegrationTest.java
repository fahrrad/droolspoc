package com.gb.cropdesign.droolspoc;

import static org.junit.Assert.*;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gb.cropdesign.droolspoc.mapper.PlantMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/rules-application-context.xml" })
public class PcrRulesIntegrationTest {
	
	@Autowired
	private DataSource db;
	
	@Autowired
	JdbcTemplate jdbctemplate;
	
	
	@Test
	@Ignore
	public void testLoadingPlantsInList() {
		String sql = "SELECT name as plant_name, rpd_nr FROM plant";
		List<Plant> plantsList = jdbctemplate.query(sql, new PlantMapper());
		
		assertEquals(1975, plantsList.size());
	}

}
