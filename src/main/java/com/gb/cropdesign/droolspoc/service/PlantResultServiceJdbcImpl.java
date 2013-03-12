package com.gb.cropdesign.droolspoc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.gb.cropdesign.droolspoc.PlantResult;
import com.gb.cropdesign.droolspoc.mapper.PlantResultMapper;

@Service
public class PlantResultServiceJdbcImpl implements PlantResultServiceI {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public ObservableList<PlantResult> getPlantResults() {
		String sql = "SELECT p.name as plant_name, r.pcr_target, is_test_gene_present as result " +
				"FROM plant p join pcrresult r on r.plant_name = p.name ";
		
		return FXCollections.observableList(jdbcTemplate.query(sql, new PlantResultMapper()));
	}

}
