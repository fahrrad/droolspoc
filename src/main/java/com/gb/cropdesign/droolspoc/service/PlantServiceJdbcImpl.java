package com.gb.cropdesign.droolspoc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.gb.cropdesign.droolspoc.Plant;
import com.gb.cropdesign.droolspoc.mapper.PlantMapper;

public class PlantServiceJdbcImpl implements PlantServiceI {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<Plant> getAllPlants() {
		String sql = "SELECT name as plant_name, rpd_nr FROM plant";
		return  jdbcTemplate.query(sql, new PlantMapper());
	}

	@Override
	public Plant getPlant(String plantName) {
		String sql = "SELECT name as plant_name, rpd_nr FROM plant where name = ?";
		return  jdbcTemplate.queryForObject(sql, new PlantMapper(), plantName);
	}

}
