package com.gb.cropdesign.droolspoc.service;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.gb.cropdesign.droolspoc.Plant;
import com.gb.cropdesign.droolspoc.PlantTableModel;
import com.gb.cropdesign.droolspoc.mapper.PlantMapper;
import com.gb.cropdesign.droolspoc.mapper.PlantTableModelMapper;

@Service
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

	@Override
	public ObservableList<PlantTableModel> getAllPlantTableModels() {
		return FXCollections.observableList(jdbcTemplate.query("SELECT name, rpd_nr, conform, transgene from Plant", new PlantTableModelMapper()));
	}
}
